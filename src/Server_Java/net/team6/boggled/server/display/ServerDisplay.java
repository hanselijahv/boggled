package Server_Java.net.team6.boggled.server.display;

import Server_Java.net.team6.boggled.common.core.Size;
import Server_Java.net.team6.boggled.server.app.ResizeCallback;
import Server_Java.net.team6.boggled.server.input.ServerInput;
import Server_Java.net.team6.boggled.server.state.ServerState;
import Server_Java.net.team6.boggled.utilities.BoggledColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

public class ServerDisplay extends JFrame {

    private final Canvas canvas;
    private final ServerRenderer renderer;


    public ServerDisplay(int width, int height, ServerInput serverInput, ResizeCallback resizeCallback) {
        ImageIcon img = new ImageIcon("res/icons/serverIcon.png");
        this.setIconImage(img.getImage());

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

        this.renderer = new ServerRenderer(this);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.addMouseListener(serverInput);
        canvas.addMouseMotionListener(serverInput);
        canvas.setBackground(Color.decode("#1f4164"));
        add(canvas);
        addKeyListener(serverInput);
        pack();

        canvas.createBufferStrategy(2);

        //setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void render(ServerState state) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics;
        do {
            try {
                graphics2D.setColor(BoggledColors.SYSTEM_COLOR);
                graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                renderer.render(state, graphics);
            } finally {
                graphics.dispose();
            }
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }
}
