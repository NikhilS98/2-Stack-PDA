package toa_project;

import java.awt.Color;
import java.awt.Font;
import java.util.Stack;
import java.awt.Graphics2D;

public class MyStack extends Stack {

    private int x, width = 100;
    private int y, height = 500, elementh = 30; //elementh = element box height
    private int stringoff = 20;

    public MyStack(int x, int y) {
        this.x = x;
        this.y = y;
        push("$"); //calling push from inherited Stack class
    }

    //draws stack boundary
    public void drawStack(Graphics2D g2d) {
        int x1 = this.x, x2 = this.x + width;
        int y1 = this.y, y2 = this.y - height; //y1 is the bottom point in vertical or left in horizontal
        
        //left vertical line
        g2d.drawLine(x1, y1, x1, y2);
        //right vertical line
        g2d.drawLine(x2, y1, x2, y2);
        //horizontal line
        g2d.drawLine(x1, y1, x2, y1);
        
    }

    //draws all elements in within stack boundary
    public void drawElements(Graphics2D g2d) {
        int num = this.elementCount;
        int x = this.x + 1;
        int width = this.width - 1;
        int y;
        int x_off = (this.x + this.x + width) / 2 - 5; //x offset for string position
        Font font = new Font("LucidaSans", Font.PLAIN, 18);
        for (int i = 0; i < num; i++) {
            y = this.y - ((i + 1) * elementh); //finds top y point for each element
            g2d.setColor(Color.yellow);
            g2d.fillRect(x, y, width, elementh);
            g2d.setColor(Color.black);
            g2d.setFont(font);
            //new y offset needs to be calculated for each element inserted
            g2d.drawString((String) this.get(i), x_off, y + stringoff);
        }
    }

}
