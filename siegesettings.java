/*
 * TODO:
 *      -Ensure multiplier inputs are valid and apply them
 *      -ADS Sens (Standard, advanced, and each sight)
 *      -FPS Limit
 *      -maybe aspect ratio?
 * 
 */



import java.nio.file.Files;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;

public class siegesettings {
    public static int vF, hF, fpsF;
    public static double fovF,multiplierF,refreshRateF,adsMultiF;
    public static void createUI() throws FileNotFoundException {
        UIManager.put("TabbedPane.selected", Color.BLACK);
        UIManager.put("TabbedPane.contentAreaColor", Color.BLACK);
        UIManager.put("TabbedPane.focus", Color.BLACK);


        // initial jframe setup
        JTabbedPane categories = new JTabbedPane();
        JPanel sensPanel = new JPanel();
        sensPanel.setLayout(new GridLayout(10,1));
        
        
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(6,1));
        


        JFrame frame = new JFrame();
        try {
            frame.setIconImage(ImageIO.read(new File("res/r6s48.png")));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
        frame.setSize(300, 750);
        frame.setTitle("R6 Siege Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout layout = new GridBagLayout();
        layout.rowHeights = new int[] {200,50};
        frame.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();
        categories.addTab("Sensitivity", sensPanel);
        categories.addTab("Display", displayPanel);

        // get Documents path
        // tbh idk how this works ty stack overflow
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        String settingsPath = fw.getDefaultDirectory().toString();
        //

        settingsPath += "/My Games/Rainbow Six - Siege/";
        String filepath = "";

        // gets GameSettings.ini paths for all uplay accounts on this PC
        ArrayList<String> filepaths = new ArrayList<String>();
        File settingsFile = new File(settingsPath);
        for (File x : settingsFile.listFiles()) {
            if (x.listFiles() != null) {
                for (File y : x.listFiles()) {
                    if (y != null && !y.toString().contains("backup") &&y.toString().contains("GameSettings")) {
                        filepath = y.toString();
                        if (!filepaths.contains(filepath))
                            filepaths.add(filepath);
                    }
                }
            }
        }
        if (filepaths.size() > 1) {
            System.out.println("WARNING\nYou have more than 1 UPlay account on this PC");
        }

        // reads initial values from gamesettings.ini (line by line, find better solution if possible)
        ArrayList<String> GameSettingsFile = new ArrayList<String>();
        if (filepaths.size() != 0) {
            File GameSettings = new File(filepaths.get(0));
            Scanner scanner = new Scanner(GameSettings);
            while (scanner.hasNextLine()) {
                GameSettingsFile.add(scanner.nextLine());
            }
            scanner.close();
            int vSens, hSens, fpsLimit;
            double refreshRate, multiplier, fov, adsMulti;
            vSens = hSens = fpsLimit = 0;
            refreshRate = multiplier = fov = adsMulti = 0;
            for (String x : GameSettingsFile) {
                if (x.contains("MousePitchSensitivity=")) {
                    vSens = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                }
                if (x.contains("MouseYawSensitivity=")) {
                    hSens = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                }
                if (x.contains("FPSLimit=")) {
                    fpsLimit = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                }
                if (x.contains("DefaultFOV=")) {
                    fov = Double.parseDouble(x.substring(1 + x.indexOf("=")));
                }
                if (x.contains("RefreshRate=")) {
                    refreshRate = Double.parseDouble(x.substring(1 + x.indexOf("=")));
                }
                if (x.contains("MouseSensitivityMultiplierUnit=")) {
                    multiplier = Double.parseDouble(x.substring(1 + x.indexOf("=")));
                }
                if (x.contains("ADSMouseMultiplierUnit=")) {
                    adsMulti = Double.parseDouble(x.substring(1 + x.indexOf("=")));
                }
            }

            // TESTING
            // prints GameSettings.ini file paths
            System.out.println();
            for (String file : filepaths) {
                System.out.println("Found GameSettings.ini in: "+file);
            }


            //
            //   SENS PANEL SETUP
            //
            // Create sensitivity sliders       
            JSlider ysensSlider = new JSlider(0, 100, vSens);
            ysensSlider.setBorder(new EmptyBorder(10,10,10,10));
            ysensSlider.setPaintTrack(true);
            ysensSlider.setPaintLabels(true);
            ysensSlider.setMajorTickSpacing(25);
            ysensSlider.setMinorTickSpacing(1);
            ysensSlider.setSnapToTicks(true);
            JLabel ycurrentSens = new JLabel("Current Y Sensitivity: " + vSens, SwingConstants.CENTER);
            JLabel ycurrentSelSens = new JLabel("Currently selected Y Sensitivity: " + vSens, SwingConstants.CENTER);
            ysensSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    vF = ysensSlider.getValue();
                    ycurrentSelSens.setText("Currently selected Y Sensitivity: " + vF);
                }
            });

            JSlider hsensSlider = new JSlider(0, 100, hSens);
            hsensSlider.setBorder(new EmptyBorder(10,10,10,10));
            hsensSlider.setPaintTrack(true);
            hsensSlider.setPaintLabels(true);
            hsensSlider.setMajorTickSpacing(25);
            hsensSlider.setMinorTickSpacing(1);
            hsensSlider.setSnapToTicks(true);
            JLabel hcurrentSens = new JLabel("Current X Sensitivity: " + hSens, SwingConstants.CENTER);
            JLabel hcurrentSelSens = new JLabel("Currently selected X Sensitivity: " + hSens, SwingConstants.CENTER);
            hsensSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    hF = hsensSlider.getValue();
                    hcurrentSelSens.setText("Currently selected X Sensitivity: " + hF);
                }
            });

            //MULTIPLIER
            JLabel currentMultiplier = new JLabel();
            currentMultiplier.setHorizontalAlignment(SwingConstants.CENTER);
            currentMultiplier.setText("Current Multiplier = " + multiplier);

            JTextField multiplierTextField = new JTextField("Type desired multiplier",5);
            multiplierTextField.addFocusListener(new FocusListener(){

                @Override
                public void focusGained(FocusEvent e) {
                    multiplierTextField.setText("");
                    
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (multiplierTextField.getText().equals("")){
                        multiplierTextField.setText("Type desired multiplier");
                    }
                }
                
            });
            multiplierTextField.setAlignmentX(SwingConstants.CENTER);
            multiplierTextField.setToolTipText("<html>"+"Default is 0.02"+"<br>"+"Change if you know what you're doing"+"</html>");

            //ADS MULTIPLIER
            JLabel currentADSMultiplier = new JLabel();
            currentADSMultiplier.setHorizontalAlignment(SwingConstants.CENTER);
            currentADSMultiplier.setText("Current ADS Multiplier = "+adsMulti);
            JTextField ADSmultiplierTextField = new JTextField("Type desired ADS multiplier",5);
            ADSmultiplierTextField.addFocusListener(new FocusListener(){

                @Override
                public void focusGained(FocusEvent e) {
                    ADSmultiplierTextField.setText("");
                    
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (ADSmultiplierTextField.getText().equals("")){
                        ADSmultiplierTextField.setText("Type desired ADS multiplier");
                    }
                }
                
            });
            ADSmultiplierTextField.setAlignmentX(SwingConstants.CENTER);
            ADSmultiplierTextField.setToolTipText("<html>"+"Default is 0.02"+"<br>"+"Change if you know what you're doing"+"</html>");





            /*  for locking the two sens sliders together for equal x and y sens
                dont know how to move both at the same time lol

            JCheckBox sensBox = new JCheckBox("Lock X and Y Sensitivity?");
            sensBox.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (sensBox.isSelected()){
                        hSensPanel.setVisible(false);
                        
                    }
                    else {
                        hSensPanel.setVisible(true);
                    }
                }});
            */
            
            //
            //   DISPLAY PANEL SETUP
            //
            // refresh rate selection box
            Double[] rrList = new Double[] { 30.0, 60.0, 75.0, 120.0, 144.0, 165.0, 240.0 };
            JComboBox<Double> refreshRates = new JComboBox<Double>(rrList);
            refreshRates.setSelectedItem(refreshRate);
            refreshRates.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    
                }
                
            });
            JLabel currentRR = new JLabel("Current Refresh Rate is set to: " + Double.toString(refreshRate), SwingConstants.CENTER);

            // fov slider
            JLabel fovLabel = new JLabel("Current FOV: " + fov, SwingConstants.CENTER);
            JLabel fovCurrentLabel = new JLabel("Currently selected FOV: " + fov, SwingConstants.CENTER);
            JSlider fovSlider = new JSlider(60, 90, (int) fov);
            fovSlider.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    fovF = fovSlider.getValue();
                    fovCurrentLabel.setText("Currently selected FOV: " + fovF);
                }
                
            });
            


            // fps limit
            JCheckBox fpsLimitBox = new JCheckBox();

            
            //creates a backup file of settings called GameSettings.ini.backup
            for (String x : filepaths){
                String backupPath = x+".backup";
                File backupFile = new File(backupPath);
                System.out.println("Backup file created in: "+backupPath);
                try {
                    Files.writeString(backupFile.toPath(),";THIS IS A BACKUP FILE OF YOUR ORIGINAL SETTINGS\n;TO RESTORE SETTINGS, REMOVE '.backup' FROM THE END OF THIS FILE NAME");
                    FileWriter writer = new FileWriter(backupFile.toPath().toString(), true);
                    BufferedWriter bw = new BufferedWriter(writer);
                    for (String y : GameSettingsFile){
                        bw.newLine();
                        bw.write(y);
                    }
                    bw.close();
                } catch (Exception e1) {
                }
            }

            //Apply button
            JButton applyButton = new JButton("Apply changes");
            applyButton.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < GameSettingsFile.size()-1; i++){
                        vF = ysensSlider.getValue();
                        hF = hsensSlider.getValue();
                        refreshRateF = Double.parseDouble(refreshRates.getSelectedItem().toString());
                        if (GameSettingsFile.get(i).contains("MousePitchSensitivity=")){
                            GameSettingsFile.set(i,"MousePitchSensitivity="+vF);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("MouseYawSensitivity=")){
                            GameSettingsFile.set(i,"MouseYawSensitivity="+hF);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("RefreshRate=")) {
                            GameSettingsFile.set(i,"RefreshRate="+refreshRateF);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        //ADD THE UI FOR THESE
                        else if (GameSettingsFile.get(i).contains("FPSLimit=")) {
                            GameSettingsFile.set(i,"FPSLimit="+fpsF);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("DefaultFOV=")) {
                            GameSettingsFile.set(i,"DefaultFOV="+fovF);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("MouseSensitivityMultiplierUnit=")) {
                            GameSettingsFile.set(i,"MouseSensitivityMultiplierUnit="+multiplierF);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        
                    }
                    try {
                        for (String x: filepaths){
                            FileWriter fwriter = new FileWriter(x, false);
                            BufferedWriter bwriter = new BufferedWriter(fwriter);
                            for (String y : GameSettingsFile){
                                bwriter.newLine();
                                bwriter.write(y);
                            }
                            bwriter.close();
                            System.out.println("Changes applied");
                        }
                    } catch (IOException e1) {
                        
                    }

                    
                }});
            
            
            
            

            /*
:::::::::: ::::::::::: ::::::::  :::    ::: :::::::::  ::::::::::       ::::::::  :::    ::: :::::::::::      ::::::::::: :::    ::: ::::::::::      :::            :::   :::   :::  ::::::::  :::    ::: ::::::::::: 
:+:            :+:    :+:    :+: :+:    :+: :+:    :+: :+:             :+:    :+: :+:    :+:     :+:              :+:     :+:    :+: :+:             :+:          :+: :+: :+:   :+: :+:    :+: :+:    :+:     :+:     
+:+            +:+    +:+        +:+    +:+ +:+    +:+ +:+             +:+    +:+ +:+    +:+     +:+              +:+     +:+    +:+ +:+             +:+         +:+   +:+ +:+ +:+  +:+    +:+ +:+    +:+     +:+     
:#::+::#       +#+    :#:        +#+    +:+ +#++:++#:  +#++:++#        +#+    +:+ +#+    +:+     +#+              +#+     +#++:++#++ +#++:++#        +#+        +#++:++#++: +#++:   +#+    +:+ +#+    +:+     +#+     
+#+            +#+    +#+   +#+# +#+    +#+ +#+    +#+ +#+             +#+    +#+ +#+    +#+     +#+              +#+     +#+    +#+ +#+             +#+        +#+     +#+  +#+    +#+    +#+ +#+    +#+     +#+     
#+#            #+#    #+#    #+# #+#    #+# #+#    #+# #+#             #+#    #+# #+#    #+#     #+#              #+#     #+#    #+# #+#             #+#        #+#     #+#  #+#    #+#    #+# #+#    #+#     #+#     
###        ########### ########   ########  ###    ### ##########       ########   ########      ###              ###     ###    ### ##########      ########## ###     ###  ###     ########   ########      ###                                                                                                                                                                                     
             */
            // creates jframe
            JSeparator s = new JSeparator();
            //frame.add(sensBox);
            sensPanel.add(ycurrentSens);
            sensPanel.add(ycurrentSelSens);
            sensPanel.add(ysensSlider);
            sensPanel.add(hcurrentSens);
            sensPanel.add(hcurrentSelSens);
            sensPanel.add(hsensSlider);
            sensPanel.add(currentMultiplier);
            sensPanel.add(multiplierTextField);
            sensPanel.add(currentADSMultiplier);
            sensPanel.add(ADSmultiplierTextField);

            displayPanel.add(fovLabel);
            displayPanel.add(fovCurrentLabel);
            displayPanel.add(fovSlider);
            displayPanel.add(currentRR);
            displayPanel.add(refreshRates);

            frame.add(categories);
            
            /* 
            ySensPanel.add(ysensSlider);
            ySensPanel.add(ycurrentSens);
            ySensPanel.add(ycurrentSelSens);
            frame.add(ySensPanel);
            frame.add(s);
            hSensPanel.add(hsensSlider);
            hSensPanel.add(hcurrentSens);
            hSensPanel.add(hcurrentSelSens);
            frame.add(hSensPanel);
            


            frame.add(currentRR);
            frame.add(refreshRates);
            
            frame.add(fovLabel);
            frame.add(fovCurrentLabel);
            frame.add(fovSlider);
            */
            
            ysensSlider.setBackground(Color.BLACK);
            ysensSlider.setForeground(Color.white);
            hsensSlider.setBackground(Color.BLACK);
            hsensSlider.setForeground(Color.white);
            c.gridx = 0;
            c.gridy = 3;
            frame.add(applyButton,c);
            categories.setBackground(Color.GRAY);
            categories.setForeground(Color.WHITE);
            frame.setForeground(Color.BLACK);
            frame.setVisible(true);
            
            // frame.add(fovLabel);

            // temporary shit solution to constantly update values
            /*
             * fixed using ChangeListener
             * while (true){
             * 
             * hcurrentSens.setText("Current X Sensitivity: " + hsensSlider.getValue());
             * }
             */
        }
        else {
            System.out.println("No GameSettings.ini found");
            JLabel nofile = new JLabel("No GameSettings.ini found",SwingConstants.CENTER);
            frame.add(nofile,SwingConstants.CENTER);
            frame.setVisible(true);
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        createUI();

    }

}