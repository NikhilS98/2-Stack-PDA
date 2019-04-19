package toa_project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class PDAPanel extends JPanel{
    
    private int machine, width, height;
    private String a, b, c;
    
    public PDAPanel(int w, int h, int num, String symbols){
        a = symbols.substring(0, 1);
        b = symbols.substring(1, 2);
        c = symbols.substring(2);
        machine = num;
        width = w;
        height = h;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        
        if(machine == 1)
            drawMachine1(g2d);
        else if (machine == 2)
            drawMachine2(g2d);
        else if (machine == 3)
            drawMachine3(g2d);
        else
            drawMachine0(g2d);
    }
    
    private void drawMachine1(Graphics2D g2d){
        State s0 = new State(g2d, "q0", 80, 200, 3);
        State s1 = new State(g2d, "q1", 350, 200, 0);
        State s2 = new State(g2d, "q2", 550, 200, 0);
        State s3 = new State(g2d, "q3", 750, 200, 0);
        State s4 = new State(g2d, "q4", 1150, 200, 2);
        s0.transition(g2d, "@, $(1) -> $(2); @, $(2) -> $(2)", s1);
        s1.transition(g2d, a + ", " + "@ -> " + a + "(1)", s1);
        s1.transition(g2d, b + ", " + a + "(1) -> " + b + "(2)", s2);
        s2.transition(g2d, b + ", " + a + "(1) -> " + b + "(2)", s2);
        s2.transition(g2d, c + ", " + b + "(2) -> @" , s3);
        s3.transition(g2d, c + ", " + b + "(2) -> @", s3);
        s3.transition(g2d, "@, $(1) -> $(1); @, $(2) -> $(2)", s4); 
    }
    
    private void drawMachine2(Graphics2D g2d){
        State s1 = new State(g2d, "q0", 150, 200, 1);
        State s2 = new State(g2d, "q1", 350, 200, 0);
        State s3 = new State(g2d, "q2", 620, 200, 0);
        State s4 = new State(g2d, "q3", 800, 200, 0);
        State s5 = new State(g2d, "q4", 1150, 200, 2);
        s1.transition(g2d, a + ", " + "@ -> " + a + "(1)", s1);
        s1.transition(g2d, "@, @ -> @", s2);
        s2.transition(g2d, b + ", @ -> " + b + "(2); @, @ -> @", s3);
        s3.transition(g2d, b + ", @ -> " + b + "(2); " + b + ", " + a + "(1) -> @" , s2);
        s3.transition(g2d, c + ", " + b + b + "(2) -> @", s4);
        s4.transition(g2d, c + ", " + b + b + "(2) -> @", s4);
        s4.transition(g2d, "@, $(1) -> $(1); @, $(2) -> $(2)", s5);
    }
    
    private void drawMachine3(Graphics2D g2d){
        State s1 = new State(g2d, "q0", 150, 200, 1);
        State s2 = new State(g2d, "q1", 350, 200, 0);
        State s3 = new State(g2d, "q2", 550, 200, 0);
        State s4 = new State(g2d, "q3", 750, 200, 0);
        State s5 = new State(g2d, "q4", 1150, 200, 2);
        s1.transition(g2d, a + ", " + "@ -> " + a + "(1)", s1);
        s1.transition(g2d, "@, @ -> @", s2);
        s2.transition(g2d, b + ", " + a + a + "(1) -> " + b + "(2)", s3);
        s3.transition(g2d, b + ", " + a + a + "(1) -> " + b + "(2)", s3);
        s3.transition(g2d, c + ", @ -> @", s4);
        s4.transition(g2d, c + ", " + b + "(2) -> @", s3);
        s4.transition(g2d, "@, $(1) -> $(1); @, $(2) -> $(2)", s5);
    }
    
    private void drawMachine0(Graphics2D g2d){
        State s1 = new State(g2d, "q0", 400, 200, 3);
        s1.transition(g2d, "@, $(1) -> $(1); @, $(2) -> $(2)", s1);
    }
    
}
