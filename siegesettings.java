import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;





public class siegesettings{

    public static void createUI(){
        JFrame frame = new JFrame();
        frame.setSize(400, 800);
        frame.setTitle("R6 Siege Settings");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));


        JSlider slider = new JSlider(0, 100, 0);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(1);
        slider.setSnapToTicks(true);


        panel.add(slider);
        frame.add(panel);
        frame.setVisible(true);
        
        


    }

    public static void main(String[] args){
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

        //TESTING
        System.out.println();
        for (String file : filepaths){
            System.out.println(file);
        }
        //createUI();
        
        

    }

}