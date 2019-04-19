package toa_project;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Toa_Project {

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setFocusable(true);
        frame.setSize(screenSize);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame f = new Frame((int) Math.round(Toa_Project.screenSize.getWidth()), (int) Math.round(Toa_Project.screenSize.getHeight()));
        frame.add(f);
        frame.setVisible(true);
    }
    
}
