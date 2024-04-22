package temp.display;


import temp.game.Game;
import temp.input.Input;
import temp.state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

@SuppressWarnings("all")
public class Display extends JFrame {

    private Canvas canvas;
    private Game game;
    private Renderer renderer;

    public Display(int width, int height, Input input) {
        setTitle("Boggled");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

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

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void render(State state){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        renderer.render(state, graphics);

        graphics.dispose();
        bufferStrategy.show();
    }
}