import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;





public class siegesettings{

    public static void createUI() throws FileNotFoundException{
        //initial jframe setup
        JFrame frame = new JFrame();
        frame.setSize(600, 800);
        frame.setTitle("R6 Siege Settings");
        
        JPanel ySensPanel = new JPanel();
        JPanel hSensPanel = new JPanel();
        frame.setLayout(new GridLayout(12,1));
        



        //get Documents path
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        String settingsPath = fw.getDefaultDirectory().toString();
        //

        settingsPath +=  "/My Games/Rainbow Six - Siege/";
        String filepath = "";

        //gets GameSettings.ini paths for all uplay accounts on this PC
        ArrayList<String> filepaths = new ArrayList<String>();
        File settingsFile = new File(settingsPath);
        for (File x : settingsFile.listFiles()){
            if (x.listFiles() != null){
                for (File y : x.listFiles()){
                    if (y != null && y.toString().contains("GameSettings") ){
                        filepath = y.toString();
                        if (!filepaths.contains(filepath)) filepaths.add(filepath);
                    }
                }
            }
        }
        if (filepaths.size() > 1){
            System.out.println("WARNING\nYou have more than 1 UPlay account on this PC");
        }

        

        //reads initial values from gamesettings.ini (line by line, find better solution if possible)
        ArrayList<String> GameSettingsFile = new ArrayList<String>();
        File GameSettings = new File(filepaths.get(0));
        Scanner scanner = new Scanner(GameSettings);
        while (scanner.hasNextLine()){
            GameSettingsFile.add(scanner.nextLine());
        }
        scanner.close();
        int vSens,hSens,fpsLimit;
        double refreshRate,multiplier,fov;
        vSens=hSens=fpsLimit=0;
        refreshRate=multiplier=fov=0;
        for (String x : GameSettingsFile){
            if (x.contains("MousePitchSensitivity=")){
                vSens= Integer.parseInt(x.substring(1+x.indexOf("=")));
            }
            if (x.contains("MouseYawSensitivity=")){
                hSens= Integer.parseInt(x.substring(1+x.indexOf("=")));
            }
            if (x.contains("FPSLimit=")){
                fpsLimit= Integer.parseInt(x.substring(1+x.indexOf("=")));
            }
            if (x.contains("DefaultFOV=")){
                fov= Double.parseDouble(x.substring(1+x.indexOf("=")));
            }
            if (x.contains("RefreshRate=")){
                refreshRate = Double.parseDouble(x.substring(1+x.indexOf("=")));
            }
            if (x.contains("MouseSensitivityMultiplierUnit=")){
                multiplier= Double.parseDouble(x.substring(1+x.indexOf("=")));
            }
        }

        //TESTING
        //prints GameSettings.ini file paths
        System.out.println();
        for (String file : filepaths){
            System.out.println(file);
        }


        //Create sensitivity sliders
        JSlider ysensSlider = new JSlider(0, 100, vSens);
        ysensSlider.setPaintTrack(true);
        ysensSlider.setPaintTicks(true);
        ysensSlider.setPaintLabels(true);
        ysensSlider.setMajorTickSpacing(50);
        ysensSlider.setMinorTickSpacing(1);
        ysensSlider.setSnapToTicks(true);
        JLabel ycurrentSens = new JLabel("Current Y Sensitivity: ", SwingConstants.CENTER);

        JSlider hsensSlider = new JSlider(0, 100, hSens);
        hsensSlider.setPaintTrack(true);
        hsensSlider.setPaintTicks(true);
        hsensSlider.setPaintLabels(true);
        hsensSlider.setMajorTickSpacing(50);
        hsensSlider.setMinorTickSpacing(1);
        hsensSlider.setSnapToTicks(true);
        JLabel hcurrentSens = new JLabel("Current Y Sensitivity: ", SwingConstants.CENTER);
        //


        //refresh rate selection box
        Double[] rrList = new Double[] {30.0,60.0,75.0,120.0,144.0,165.0,240.0};
        JComboBox<Double> refreshRates = new JComboBox<Double>(rrList);
        JLabel currentRR = new JLabel("Current Refresh Rate is set to: " + Double.toString(refreshRate));
        

        //figure out the damn layout u idiot
        //creates jframe
        JSeparator s = new JSeparator();
        ySensPanel.add(ysensSlider);
        frame.add(ycurrentSens);
        frame.add(ySensPanel);
        hSensPanel.add(hsensSlider);
        frame.add(hcurrentSens);
        frame.add(hSensPanel);
        frame.add(refreshRates);
        frame.add(currentRR);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //temporary shit solution to constantly update values
        while (true){
            ycurrentSens.setText("Current Y Sensitivity: " + ysensSlider.getValue());
            hcurrentSens.setText("Current Y Sensitivity: " + hsensSlider.getValue());
        }
        
        


    }

    public static void main(String[] args) throws FileNotFoundException{
        createUI();
        
        

    }

}