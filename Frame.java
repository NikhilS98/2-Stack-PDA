package toa_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Frame extends JPanel implements ActionListener {

    private MyStack stack1, stack2;
    private Timer loopTimer;
    private int width, height;
    private Languages L;
    private String input = "", symbols = "", lang = "";
    private int index, machine;
    private int[] originalp = new int[3];
    private boolean halt, start;
    private JFrame pda;
    private JTextField ifield;
    private JButton run, reset, showpda;
    /*
    ArrayList of powers because we don't know the number of symbols
    could be a, b, c, d... 
     */
    private ArrayList powers;
    /*
    first 2 elements for push in stack 1 and 2 respectively;
    last 2 elements for pop from 1 and 2;
    false means do nothing, true means perform respective action.
     */
    private boolean[] choices;

    public Frame(int w, int h) {
        width = w;
        height = h;
        ifield = new JTextField("");
        run = new JButton("RUN");
        run.addActionListener(this);
        reset = new JButton("RESET");
        reset.addActionListener(this);
        showpda = new JButton("Show PDA");
        showpda.addActionListener(this);
        stack1 = new MyStack(900, 550);
        stack2 = new MyStack(1100, 550);
        choices = new boolean[4];
        L = new Languages();

        loopTimer = new Timer(1000, this);

    }

    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, width, height);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //setting background color to white
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        ifield.setBounds(20, 20, 350, 35);
        ifield.setFont(new Font("LucidaSans", Font.PLAIN, 20));
        add(ifield);

        run.setBounds(50, 65, 60, 25);
        add(run);

        reset.setBounds(125, 65, 80, 25);
        add(reset);

        g2d.setColor(Color.gray);
        g2d.setFont(new Font("LucidaSans", Font.PLAIN, 20));
        g2d.drawString("Example: a^2.b^2.c^2", 400, 45);
        
        g2d.setColor(Color.black);
        stack1.drawStack(g2d);
        stack2.drawStack(g2d);

        if (start) {
            if (index < symbols.length()) { //index points to current letter
                String current = symbols.substring(index, index + 1); //extracting cuurent letter to sym
                showCurrentSymbol(g2d, current, originalp[index] - (int) powers.get(index) + 1);
                choices = L.rule(symbols, current, index, powers);
                if ((lang.equals("lang2"))) {
                    machine = 2;
                    executelang2(current);
                } else if ((lang.equals("lang3"))) {
                    machine = 3;
                    executelang3(current);
                } else {
                    if(input.equals("") || (originalp[0] == 0 && originalp[1] == 0 && originalp[2] == 0)){
                        machine = 0;
                    }
                    else{
                        machine = 1;
                    }
                    executelang1(current);
                }
            }
            stack1.drawElements(g2d);
            stack2.drawElements(g2d);

            if (index < powers.size()) {
                if (powers.get(index).equals(0)) {
                    index++;
                }
            }

            if (halt) {
                drawReject(g2d);
            }

            if (index == symbols.length()) {
                checkAccept(g2d);
            }
        }

    }

    private ArrayList calPowers() {
        if (input.length() == 0) {
            return null;
        }
        ArrayList p = new ArrayList();
        for (int i = 2; i < input.length(); i++) {
            String str = "";
            if (input.charAt(i - 1) == '^') {
                while (i < input.length() && input.charAt(i) != '.') {
                    str += input.substring(i, i + 1);
                    i++;
                }
                p.add(Integer.parseInt(str));
            }
        }
        return p;
    }

    private String extractSymbols() {
        String sym = input.substring(0, 1);
        for (int i = 3; i < input.length() - 2; i++) {
            if (input.charAt(i - 1) == '.') {
                sym += input.substring(i, i + 1);
            }
        }
        return sym;
    }

    private void showCurrentSymbol(Graphics2D g2d, String current, int num) {
        g2d.setColor(Color.red);
        Font font = new Font("LucidaSans", Font.PLAIN, 40);
        g2d.setFont(font);
        g2d.drawString("READ " + num + ": " + current, 980, 630);
    }

    private void checkAccept(Graphics2D g2d) {
        if (stack1.peek().equals("$") && stack2.peek().equals("$")) {
            drawAccept(g2d);
        } else {
            drawReject(g2d);
        }
    }

    private void drawAccept(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.fillOval(173, 333, 120, 120);
        g2d.setColor(Color.black);
        g2d.drawString("ACCEPT", 200, 400);
        showpda.setBounds(180, 480, 100, 25);
        add(showpda);
    }

    private void drawReject(Graphics2D g2d) {
        g2d.setColor(Color.red);
        g2d.fillOval(173, 333, 120, 120);
        g2d.setColor(Color.black);
        g2d.drawString("REJECT", 200, 400);
    }

    private void executelang1(String current) {
        if (!halt && choices[0]) {
            stack1.push(current);
            int value = (int) powers.get(index);
            powers.set(index, value - 1);
        }
        if (!halt && choices[2]) {
            if (stack1.peek().equals(symbols.substring(index - 1, index))) {
                stack1.pop();
            } else {
                halt = true;
            }
        }
        if (!halt && choices[1]) {
            stack2.push(current);
            int value = (int) powers.get(index);
            powers.set(index, value - 1);
        }
        if (!halt && choices[3]) {
            if (stack2.peek().equals(symbols.substring(index - 1, index))) {
                stack2.pop();
                int value = (int) powers.get(index);
                powers.set(index, value - 1);
            } else {
                halt = true;
            }
        }
    }

    private void executelang2(String current) {
        if (!halt && choices[0]) {
            stack1.push(current);
            int value = (int) powers.get(index);
            powers.set(index, value - 1);
        }
        if (!halt && choices[1]) {
            stack2.push(current);
            int value = (int) powers.get(index);
            if (stack1.peek().equals(symbols.substring(index - 1, index))) {
                if ((value % 2) != 0) {
                    stack1.pop();
                }
            } else {
                halt = true;
            }
            powers.set(index, value - 1);
        }
        if (!halt && choices[3]) {
            if (stack2.peek().equals(symbols.substring(index - 1, index))) {
                stack2.pop();
                stack2.pop();
                int value = (int) powers.get(index);
                powers.set(index, value - 1);
            } else {
                halt = true;
            }
        }
    }

    private void executelang3(String current) {
        if (!halt && choices[0]) {
            stack1.push(current);
            int value = (int) powers.get(index);
            powers.set(index, value - 1);
        }
        if (!halt && choices[1]) {
            stack2.push(current);
            int value = (int) powers.get(index);
            if (stack1.peek().equals(symbols.substring(index - 1, index))) {
                stack1.pop();
                stack1.pop();
            } else {
                halt = true;
            }
            powers.set(index, value - 1);
        }
        if (!halt && choices[3]) {
            if (stack2.peek().equals(symbols.substring(index - 1, index))) {
                int value = (int) powers.get(index);
                if (value % 2 != 0) {
                    stack2.pop();
                }
                powers.set(index, value - 1);
            } else {
                halt = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == run) {
            input = ifield.getText();
            if (input.equals("")) {
                powers = new ArrayList();
                powers.add(0);
                powers.add(0);
                powers.add(0);
                symbols = "@@@";

            } else {
                powers = calPowers();
                originalp[0] = (int) powers.get(0);
                originalp[1] = (int) powers.get(1);
                originalp[2] = (int) powers.get(2);
                //Separating out the letters from the input
                symbols = extractSymbols();

            }
            lang = L.checkLanguage(symbols, powers);
            start = true;
            loopTimer.start();
            run.removeActionListener(this);
        }

        if (e.getSource() == reset) { //reset all the fields
            powers = new ArrayList();
            start = false;
            halt = false;
            ifield.setText("");
            input = "";
            symbols = "";
            index = 0;
            stack1 = new MyStack(900, 550);
            stack2 = new MyStack(1100, 550);
            choices = new boolean[4];
            remove(showpda);
            if(pda != null)
                pda.setVisible(false);
            run.addActionListener(this);
            loopTimer.stop();
        }

        if (e.getSource() == showpda) {
            pda = new JFrame();
            pda.setFocusable(true);
            pda.setSize(width, 450);
            pda.setLocationRelativeTo(null);
            //pda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            System.out.println(machine);
            PDAPanel p = new PDAPanel(width, 450, machine, symbols);
            pda.add(p);
            pda.setVisible(true);
        }

        repaint();
    }

}
