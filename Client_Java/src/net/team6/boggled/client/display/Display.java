package net.team6.boggled.client.display;


import net.team6.boggled.client.display.Renderer;
import net.team6.boggled.client.game.ResizeCallback;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.State;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.utilities.BoggledColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;


public class Display extends JFrame {

    private final Canvas canvas;
    private final Renderer renderer;

    public Display(int width, int height, Input input, ResizeCallback resizeCallback) {
        ImageIcon img = new ImageIcon("res/icons/clientIcon.png");
        this.setIconImage(img.getImage());
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

        this.renderer = new Renderer(this);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        add(canvas);
        addKeyListener(input);
        pack();

        canvas.createBufferStrategy(2);
        canvas.setFocusTraversalKeysEnabled(false);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void render(State state) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        do {
            try {
                graphics.setColor(BoggledColors.SYSTEM_COLOR);
                graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                renderer.render(state, graphics);
            } finally {
                graphics.dispose();
            }
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }
}