package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    public final int originalTileSize = 16;
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenColumn = 25;
    public final int maxScreenRow = 17;


    public final int screenWidth = tileSize * maxScreenColumn;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int FPS = 120;
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int settingsState = 2;
    public final int leaderboardsState = 3;

    Sound sound = new Sound();
    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;
    UI ui = new UI(this);



    int x = 100;
    int y = 100;
    int speed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setUpGame(){
        gameState = titleState;
        //playMusic(0);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {


        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;

        long lastTime = System.nanoTime();
        long currentTime;


        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update(){
        if(keyHandler.up){
            y -= speed;

        }

        else if(keyHandler.down){
            y += speed;
        }

        else if(keyHandler.left){
            x -= speed;
        }
        else if(keyHandler.right){
            x += speed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if(gameState == titleState){
            ui.draw(g2d);
        }

        if(gameState == settingsState){
            ui.draw(g2d);
        }

        if(gameState == playState){
            ui.draw(g2d);
        }

        if(gameState == leaderboardsState){
            ui.draw(g2d);
        }
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.playSound();
        sound.loopSound();
    }

    public void stopMusic(){
        sound.stopSound();
    }

    public void playSoundEffect(int i){
        sound.setFile(i);
        sound.playSound();
    }


}
