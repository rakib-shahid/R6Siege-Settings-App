/*
 * TODO:
 *      DONE-Ensure multiplier inputs are valid and apply them
 *      DONE-ADS Sens (Standard, advanced, and each sight)
 *      DONE-FPS Limit
 *      -maybe aspect ratio?
 * 
 */



import java.nio.file.Files;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    public static int vF, hF, fpsF,ads1f,ads15f,ads2f,ads25f,ads3f,ads4f,ads5f,ads12f,adsGlobalF,fpsLimitF;
    public static double fovF,multiplierF,refreshRateF,adsMultiF;
    public static boolean adsSpecific;
    public static void createUI() throws FileNotFoundException {
        UIManager.put("TabbedPane.selected", Color.BLACK);
        UIManager.put("TabbedPane.contentAreaColor", Color.BLACK);
        UIManager.put("TabbedPane.focus", Color.BLACK);


        // initial jframe setup
        JTabbedPane categories = new JTabbedPane();
        JPanel sensPanel = new JPanel();
        sensPanel.setLayout(new GridLayout(10,1));

        JPanel adsPanel = new JPanel();
        adsPanel.setLayout(new GridLayout(19,1));
        
        
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(8,1));


        JFrame frame = new JFrame();
        try {
            frame.setIconImage(ImageIO.read(new File("res/r6s48.png")));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
        frame.setSize(300, 800);
        frame.setTitle("R6 Siege Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout layout = new GridBagLayout();
        layout.rowHeights = new int[] {200,50};
        frame.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();
        categories.addTab("Sensitivity", sensPanel);
        categories.addTab("ADS", adsPanel);
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

        //
        //  Read in initial values
        //
        //////////
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
            int ads1x,ads15x,ads2x,ads25x,ads3x,ads4x,ads5x,ads12x,adsGlobal;
            ads1x=ads15x=ads2x=ads25x=ads3x=ads4x=ads5x=ads12x=adsGlobal=0;

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
                if (x.contains("ADSMouseUseSpecific=")){
                    if (Integer.parseInt(x.substring(1 + x.indexOf("="))) == 1){
                        adsSpecific = true;
                    }
                    else {
                        adsSpecific = false;
                    }
                }
                if (x.contains("ADSMouseSensitivity1x=")) {
                    ads1x = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                    ads1f = ads1x;
                }
                if (x.contains("ADSMouseSensitivity1xHalf=")) {
                    ads15x = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                    ads15f = ads15x;
                }
                if (x.contains("ADSMouseSensitivity2x=")) {
                    ads2x = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                    ads2f = ads2x;
                }
                if (x.contains("ADSMouseSensitivity2xHalf=")) {
                    ads25x = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                    ads25f = ads25x;
                }
                if (x.contains("ADSMouseSensitivity3x=")) {
                    ads3x = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                    ads3f = ads3x;
                }
                if (x.contains("ADSMouseSensitivity4x=")) {
                    ads4x = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                    ads4f = ads4x;
                }
                if (x.contains("ADSMouseSensitivity5x=")) {
                    ads5x = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                    ads5f = ads5x;
                }
                if (x.contains("ADSMouseSensitivity12x=")) {
                    ads12x = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                    ads12f = ads12x;
                }
                if (x.contains("ADSMouseSensitivityGlobal=")) {
                    adsGlobal = Integer.parseInt(x.substring(1 + x.indexOf("=")));
                    adsGlobalF = adsGlobal;
                }

            }
            fovF = fov;
            multiplierF = multiplier;
            adsMultiF = adsMulti;
            refreshRateF = refreshRate;
            vF = vSens;
            hF = hSens;
            fpsF = fpsLimit;
            ads1f = ads1x;
            ads15f = ads15x;
            ads2f = ads2x;
            ads25f = ads25x;
            ads3f = ads3x;
            ads4f = ads4x;
            ads5f = ads5x;
            ads12f = ads12x; 
            adsGlobalF = adsGlobal;
            fpsLimitF = fpsLimit;
            

            // TESTING
            // prints GameSettings.ini file paths
            System.out.println();
            for (String file : filepaths) {
                System.out.println("Found GameSettings.ini in: "+file);
            }


            
            
            //////////////////////
            //                  //
            //    SENS PANEL    //
            //                  //
            //////////////////////

            //
            //    Create sensitivity sliders     
            //
            /////////  
            JSlider ysensSlider = new JSlider(0, 100, vSens);
            ysensSlider.setBorder(new EmptyBorder(10,10,10,10));
            ysensSlider.setPaintTrack(true);
            ysensSlider.setPaintLabels(true);
            ysensSlider.setMajorTickSpacing(25);
            ysensSlider.setMinorTickSpacing(1);
            ysensSlider.setSnapToTicks(true);
            JLabel ycurrentSens = new JLabel("Current Y Sensitivity: " + vSens, SwingConstants.CENTER);
            JLabel ycurrentSelSens = new JLabel("Selected Y Sensitivity: " + vSens, SwingConstants.CENTER);
            ysensSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    vF = ysensSlider.getValue();
                    ycurrentSelSens.setText("Selected Y Sensitivity: " + vF);
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
            JLabel hcurrentSelSens = new JLabel("Selected X Sensitivity: " + hSens, SwingConstants.CENTER);
            hsensSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    hF = hsensSlider.getValue();
                    hcurrentSelSens.setText("Selected X Sensitivity: " + hF);
                }
            });

            //
            //  Mouse Multiplier
            //
            //////////
            JLabel currentMultiplier = new JLabel();
            currentMultiplier.setHorizontalAlignment(SwingConstants.CENTER);
            currentMultiplier.setText("Current Multiplier = " + multiplier);
            
            
            JTextField multiplierTextField = new JTextField("Type desired mouse multiplier",5);
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

            //
            //  ADS Multiplier
            //
            //////////
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



            //////////////////////
            //                  //
            //  ADS SENS PANEL  //
            //                  //
            //////////////////////

            JLabel adsGlobalLabel = new JLabel();
            adsGlobalLabel.setText("Selected global sens: "+adsGlobal);
            JSlider adsGlobalSlider = new JSlider(1, 200, adsGlobal);
            adsGlobalSlider.setBorder(new EmptyBorder(0,10,0,10));
            adsGlobalSlider.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    adsGlobalF = adsGlobalSlider.getValue();
                    adsGlobalLabel.setText("Selected global sens: "+adsGlobalF);
                }
                
            });
            adsGlobalSlider.setPaintTrack(true);
            adsGlobalSlider.setPaintLabels(true);
            adsGlobalSlider.setMajorTickSpacing(99);
            adsGlobalSlider.setMinorTickSpacing(1);
            adsGlobalSlider.setSnapToTicks(true);

            JCheckBox adsSimple = new JCheckBox("Using standard ADS sens");
            adsSimple.setToolTipText("<html>"+"Check this box to use 1 global sensitivity for all sights<br>or uncheck it to use individual sensitivities for each sight"+"</html>");
            if (!adsSpecific){
                adsSimple.setSelected(true);
                adsSimple.setText("Using standard ADS sens");
            }
            else {
                adsSimple.setSelected(false);
                adsSimple.setText("Using advanced ADS sens");
            }
            adsSimple.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (adsSimple.isSelected()){
                        adsSpecific = false;
                        adsSimple.setText("Using standard ADS sens");
                    }
                    else {
                        adsSpecific = true;
                        adsSimple.setText("Using advanced ADS sens");
                    }
                    
                }

            });
            
            JLabel adsLabel1 = new JLabel();
            adsLabel1.setText("Selected 1x sens: "+ads1f);
            JSlider adsSlider1 = new JSlider(1, 200, ads1x);
            adsSlider1.setBorder(new EmptyBorder(0,10,0,10));
            adsSlider1.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    ads1f = adsSlider1.getValue();
                    adsLabel1.setText("Selected 1x sens: "+ads1f);
                }
                
            });
            adsSlider1.setPaintTrack(true);
            adsSlider1.setPaintLabels(true);
            adsSlider1.setMajorTickSpacing(99);
            adsSlider1.setMinorTickSpacing(1);
            adsSlider1.setSnapToTicks(true);
            JLabel adsLabel15 = new JLabel("Selected 1.5x sens: "+ads15f);
            JSlider adsSlider15 = new JSlider(1, 200, ads15x);
            adsSlider15.setBorder(new EmptyBorder(0,10,0,10));
            adsSlider15.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    ads15f = adsSlider15.getValue();
                    adsLabel15.setText("Selected 1.5x sens: "+ads15f);
                }
                
            });
            adsSlider15.setPaintTrack(true);
            adsSlider15.setPaintLabels(true);
            adsSlider15.setMajorTickSpacing(99);
            adsSlider15.setMinorTickSpacing(1);
            adsSlider15.setSnapToTicks(true);
            JSlider adsSlider2 = new JSlider(1, 200, ads2x);
            JLabel adsLabel2 = new JLabel("Selected 2x sens: "+ads2f);
            adsSlider2.setBorder(new EmptyBorder(0,10,0,10));
            adsSlider2.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    ads2f = adsSlider2.getValue();
                    adsLabel2.setText("Selected 2x sens: "+ads2f);
                }
                
            });
            adsSlider2.setPaintTrack(true);
            adsSlider2.setPaintLabels(true);
            adsSlider2.setMajorTickSpacing(99);
            adsSlider2.setMinorTickSpacing(1);
            adsSlider2.setSnapToTicks(true);
            JSlider adsSlider25 = new JSlider(1, 200, ads25x);
            adsSlider25.setBorder(new EmptyBorder(0,10,0,10));
            JLabel adsLabel25 = new JLabel("Selected 2.5x sens: "+ads25f);
            adsSlider25.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    ads25f = adsSlider25.getValue();
                    adsLabel25.setText("Selected 2.5x sens: "+ads25f);
                }
                
            });
            adsSlider25.setPaintTrack(true);
            adsSlider25.setPaintLabels(true);
            adsSlider25.setMajorTickSpacing(99);
            adsSlider25.setMinorTickSpacing(1);
            adsSlider25.setSnapToTicks(true);
            JSlider adsSlider3 = new JSlider(1, 200, ads3x);
            adsSlider3.setBorder(new EmptyBorder(0,10,0,10));
            JLabel adsLabel3 = new JLabel("Selected 3x sens: "+ads3f);
            adsSlider3.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    ads3f = adsSlider3.getValue();
                    adsLabel3.setText("Selected 3x sens: "+ads3f);
                }
                
            });
            adsSlider3.setPaintTrack(true);
            adsSlider3.setPaintLabels(true);
            adsSlider3.setMajorTickSpacing(99);
            adsSlider3.setMinorTickSpacing(1);
            adsSlider3.setSnapToTicks(true);
            JSlider adsSlider4 = new JSlider(1, 200, ads4x);
            adsSlider4.setBorder(new EmptyBorder(0,10,0,10));
            JLabel adsLabel4 = new JLabel("Selected 4x sens: "+ads4f);
            adsSlider4.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    ads4f = adsSlider4.getValue();
                    adsLabel4.setText("Selected 4x sens: "+ads4f);
                }
                
            });
            adsSlider4.setPaintTrack(true);
            adsSlider4.setPaintLabels(true);
            adsSlider4.setMajorTickSpacing(99);
            adsSlider4.setMinorTickSpacing(1);
            adsSlider4.setSnapToTicks(true);
            JSlider adsSlider5 = new JSlider(1, 200, ads5x);
            adsSlider5.setBorder(new EmptyBorder(0,10,0,10));
            JLabel adsLabel5 = new JLabel("Selected 5x sens: "+ads5f);
            adsSlider5.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    ads5f = adsSlider5.getValue();
                    adsLabel5.setText("Selected 5x sens: "+ads5f);
                }
                
            });
            adsSlider5.setPaintTrack(true);
            adsSlider5.setPaintLabels(true);
            adsSlider5.setMajorTickSpacing(99);
            adsSlider5.setMinorTickSpacing(1);
            adsSlider5.setSnapToTicks(true);
            JSlider adsSlider12 = new JSlider(1, 200, ads12x);
            adsSlider12.setBorder(new EmptyBorder(0,10,0,10));
            JLabel adsLabel12 = new JLabel("Selected 12x sens: "+ads12f);
            adsSlider12.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    ads12f = adsSlider12.getValue();
                    adsLabel12.setText("Selected 12x sens: "+ads12f);
                }
                
            });
            adsSlider12.setPaintTrack(true);
            adsSlider12.setPaintLabels(true);
            adsSlider12.setMajorTickSpacing(99);
            adsSlider12.setMinorTickSpacing(1);
            adsSlider12.setSnapToTicks(true);


            
            
            //////////////////////
            //                  //
            //  DISPLAY  PANEL  //
            //                  //
            //////////////////////


            //
            //  Refresh rate selection box
            //
            //////////
            Double[] rrList = new Double[] { 30.0, 60.0, 75.0, 120.0, 144.0, 165.0, 240.0 };
            JComboBox<Double> refreshRates = new JComboBox<Double>(rrList);
            refreshRates.setSelectedItem(refreshRate);
            JLabel selectedRR = new JLabel("Selected Refresh Rate: " + refreshRates.getSelectedItem());
            refreshRates.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    refreshRateF = Double.parseDouble(refreshRates.getSelectedItem().toString());
                    selectedRR.setText("Selected Refresh Rate: " + refreshRates.getSelectedItem());
                }
                
            });
            JLabel currentRR = new JLabel("Current Refresh Rate is set to: " + Double.toString(refreshRate), SwingConstants.CENTER);
           

            //
            //  FOV Slider
            //
            //////////
            JLabel fovLabel = new JLabel("Current FOV: " + fov, SwingConstants.CENTER);
            JLabel fovCurrentLabel = new JLabel("Selected FOV: " + fov, SwingConstants.CENTER);
            if (fov < 60){
                fov = 60;
            }
            JSlider fovSlider = new JSlider(60, 90, (int)fov);
            fovSlider.setBorder(new EmptyBorder(0,10,0,10));
            fovSlider.setPaintTrack(true);
            fovSlider.setPaintLabels(true);
            fovSlider.setMajorTickSpacing(10);
            fovSlider.setMinorTickSpacing(1);
            fovSlider.setSnapToTicks(true);
            fovSlider.setBackground(Color.BLACK);
            fovSlider.setForeground(Color.WHITE);
            fovSlider.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    fovF = fovSlider.getValue();
                    fovCurrentLabel.setText("Selected FOV: " + fovF);
                }
                
            });
            


            //
            //  FPS Limit (not yet implemented)
            //
            //////////
            Integer[] fpsList = new Integer[] {0,30, 60, 75, 120, 144, 165, 240};
            JComboBox<Integer> fpsLimitBox = new JComboBox<Integer>(fpsList);
            fpsLimitBox.setSelectedItem(fpsLimit);
            JLabel currentfpsLimit = new JLabel("Selected FPS limit: " + Integer.toString(fpsLimit), SwingConstants.CENTER);
            fpsLimitBox.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    fpsLimitF = Integer.parseInt(fpsLimitBox.getSelectedItem().toString());
                    currentfpsLimit.setText("Selected FPS limit: " + fpsLimitBox.getSelectedItem());
                }
                
            });
            

            
            
            //
            //  Create backup files
            //
            //////////
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

            //
            //  Apply button
            //
            //////////
            JButton applyButton = new JButton("Apply changes");
            applyButton.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < GameSettingsFile.size()-1; i++){
                        try {
                            if (!multiplierTextField.getText().equals("Type desired mouse multiplier")){
                                Double temp = 0.02;
                                temp = Double.parseDouble(multiplierTextField.getText());
                                multiplierF = temp;
                            }
                        } catch (NumberFormatException ex) {
                            //TODO: handle exception
                            multiplierTextField.setText("ENTER VALID NUMBER (ex: 0.02, 0.002)");
                        }
                        try {
                            if (!ADSmultiplierTextField.getText().equals("Type desired ADS multiplier")){
                                Double temp2 = 0.02;
                                temp2 = Double.parseDouble(ADSmultiplierTextField.getText());
                                adsMultiF = temp2;
                            }
                            
                        } catch (NumberFormatException ex) {
                            //TODO: handle exception
                            ADSmultiplierTextField.setText("ENTER VALID NUMBER (ex: 0.02, 0.002)");
                        }
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
                        else if (GameSettingsFile.get(i).contains("ADSMouseMultiplierUnit=")) {
                            GameSettingsFile.set(i,"ADSMouseMultiplierUnit="+adsMultiF);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("ADSMouseSensitivity1x=")) {
                            GameSettingsFile.set(i,"ADSMouseSensitivity1x="+ads1f);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("ADSMouseSensitivity1xHalf=")) {
                            GameSettingsFile.set(i,"ADSMouseSensitivity1xHalf="+ads15f);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("ADSMouseSensitivity2x=")) {
                            GameSettingsFile.set(i,"ADSMouseSensitivity2x="+ads2f);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("ADSMouseSensitivity2xHalf=")) {
                            GameSettingsFile.set(i,"ADSMouseSensitivity2xHalf="+ads25f);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("ADSMouseSensitivity3x=")) {
                            GameSettingsFile.set(i,"ADSMouseSensitivity3x="+ads3f);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("ADSMouseSensitivity4x=")) {
                            GameSettingsFile.set(i,"ADSMouseSensitivity4x="+ads4f);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("ADSMouseSensitivity5x=")) {
                            GameSettingsFile.set(i,"ADSMouseSensitivity5x="+ads5f);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("ADSMouseSensitivity12x=")) {
                            GameSettingsFile.set(i,"ADSMouseSensitivity12x="+ads12f);
                            System.out.println(GameSettingsFile.get(i));
                        }
                        else if (GameSettingsFile.get(i).contains("ADSMouseSensitivityGlobal=")) {
                            GameSettingsFile.set(i,"ADSMouseSensitivityGlobal="+adsGlobalF);
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
                            System.out.println("Changes applied to file located in " + x.toString());
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


            adsPanel.add(adsGlobalLabel);
            adsPanel.add(adsGlobalSlider);
            adsPanel.add(adsSimple);

            adsPanel.add(adsLabel1);
            adsPanel.add(adsSlider1);

            adsPanel.add(adsLabel15);
            adsPanel.add(adsSlider15);

            adsPanel.add(adsLabel2);
            adsPanel.add(adsSlider2);

            adsPanel.add(adsLabel25);
            adsPanel.add(adsSlider25);

            adsPanel.add(adsLabel3);
            adsPanel.add(adsSlider3);

            adsPanel.add(adsLabel4);
            adsPanel.add(adsSlider4);

            adsPanel.add(adsLabel5);
            adsPanel.add(adsSlider5);

            adsPanel.add(adsLabel12);
            adsPanel.add(adsSlider12);


            displayPanel.add(fovLabel);
            displayPanel.add(fovCurrentLabel);
            displayPanel.add(fovSlider);
            displayPanel.add(currentRR);
            displayPanel.add(selectedRR);
            displayPanel.add(refreshRates);
            displayPanel.add(currentfpsLimit);
            displayPanel.add(fpsLimitBox);
            //displayPanel.add(currentfpsLimit);
            //displayPanel.add(fpsLimitBox);

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