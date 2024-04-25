package com.wordgame.boggled.server;

import com.wordgame.boggled.input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class DevDisplay extends JFrame {

    private final Canvas canvas;


    public DevDisplay(int width, int height){
        setTitle("Boggled Dev");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setUndecorated(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);


        canvas.setBackground(Color.decode("#0d1b2a"));
        add(canvas);
        pack();

        canvas.createBufferStrategy(1);

        setLocationRelativeTo(null);
        setVisible(true);


    }
}
