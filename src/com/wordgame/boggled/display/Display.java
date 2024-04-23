package com.wordgame.boggled.display;


import com.wordgame.boggled.core.Size;
import com.wordgame.boggled.game.ResizeCallback;
import com.wordgame.boggled.input.Input;
import com.wordgame.boggled.state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;


public class Display extends JFrame {

    private final Canvas canvas;
    private final Renderer renderer;

    public Display(int width, int height, Input input, ResizeCallback resizeCallback) {
        setTitle("Boggled");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setPreferredSize(e.getComponent().getSize());
                pack();
                resizeCallback.resize(new Size(getContentPane().getWidth(), getContentPane().getHeight()));
                canvas.setPreferredSize(getContentPane().getPreferredSize());
            }
        });

        this.renderer = new Renderer();

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        add(canvas);
        addKeyListener(input);
        pack();

        canvas.createBufferStrategy(2);

        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void render(State state) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics;
        do {
            try {

                graphics2D.setColor(Color.decode("#0d1b2a"));
                graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                renderer.render(state, graphics);
            } finally {
                graphics.dispose();
            }
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }
}