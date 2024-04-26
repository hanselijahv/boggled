package net.team6.boggled.server.display;

import net.team6.boggled.client.core.Size;
import net.team6.boggled.client.game.ResizeCallback;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

public class DevDisplay extends JFrame {

    private final Canvas canvas;
    private final DevRenderer renderer;


    public DevDisplay(int width, int height, Input input, ResizeCallback resizeCallback) {
        setTitle("Boggled Server Settings");
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

        this.renderer = new DevRenderer(this);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        canvas.setBackground(Color.decode("#1f4164"));
        add(canvas);
        addKeyListener(input);
        pack();

        canvas.createBufferStrategy(2);

        //setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void render(State state) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics;
        do {
            try {
                graphics2D.setColor(Color.decode("#1f4164"));
                graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                renderer.render(state, graphics);
            } finally {
                graphics.dispose();
            }
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }
}
