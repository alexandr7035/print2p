package com.alexandr7035.print2p;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  



public class  WindowFrame extends JFrame {

    // Window size
    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 300;

    // Buttons
    private JButton printFisrtPageButton;
    private JButton printSecondPageButton;

    public WindowFrame() {
        // Set size
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        this.printFisrtPageButton = new JButton("First page");

        this.printSecondPageButton = new JButton("Second page");

        // Add widgets
        //this.add(this.printFisrtPageButton);
        //this.add(this.printSecondPageButton);

        this.setLocationRelativeTo(null);

    }

    public void run() {
        this.setVisible(true);
    }

}