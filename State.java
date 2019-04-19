package toa_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class State {

    int x, y, w = 60, h = 60, prop;
    String name;

    //prop = 0 means normal, 1 means initial, 2 means final 
    //and 3 means initial and final both
    public State(Graphics2D g2d, String name, int x, int y, int prop) {
        this.x = x;
        this.y = y;
        this.prop = prop;
        this.name = name;

        g2d.setColor(Color.pink);
        if (prop >= 2) {
            g2d.setColor(Color.GREEN);
        }
        g2d.fillOval(x, y, w, h);
        g2d.setFont(new Font("LucidaSans", Font.PLAIN, 22));
        g2d.setColor(Color.black);
        if(prop == 1 || prop == 3){
            g2d.drawString("-" + name, x + 15, y + 38);
        }
        else{
            g2d.drawString(name, x + 20, y + 38);
        }
    }

    public void transition(Graphics2D g2d, String tran, State to) {
        g2d.setColor(Color.black);
        g2d.setFont(new Font("LucidaSans", Font.PLAIN, 17));

        int stroff = 10;
        if(tran.length() > 15){
            g2d.setFont(new Font("LucidaSans", Font.PLAIN, 15));
            stroff = 55;
        }
        
        if (to == this) {
            int xoff1 = x + (w / 2) + 12;
            int xoff2 = x + (w / 2) - 12;
            g2d.drawLine(xoff1, y, xoff1 - 12, y - 25);
            g2d.drawLine(xoff2, y, xoff1 - 12, y - 25);
            g2d.drawLine(xoff2, y, xoff2 - 5, y - 5);
            g2d.drawLine(xoff2, y, xoff2 + 10, y - 5);
            g2d.drawString(tran, xoff2 - stroff, y - 30);
        } else {
            int x2 = to.x;
            int y2 = to.y;
            if (x2 > x) {
                int yoff = y2 + (h / 2) - 10;
                g2d.drawLine(x + w, yoff, x2, yoff);
                g2d.drawLine(x2, yoff, x2 - 5, yoff - 5);
                g2d.drawLine(x2, yoff, x2 - 5, yoff + 5);
                g2d.drawString(tran, (x + (x2 - x) / 2) - stroff, yoff - 5);
            } else {
                int yoff = y2 + (h / 2) + 10;
                g2d.drawLine(x, yoff, x2 + w, yoff);
                g2d.drawLine(x2 + w, yoff, x2 + w + 5, yoff - 5);
                g2d.drawLine(x2 + w, yoff, x2 + w + 5, yoff + 5);
                g2d.drawString(tran, (x + (x2 - x) / 2) - stroff, yoff + 18);
            }
        }
    }
}
