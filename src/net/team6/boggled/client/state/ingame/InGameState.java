package net.team6.boggled.client.state.ingame;

import net.team6.boggled.client.audio.AudioPlayer;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.utilities.BoggledColors;

import javax.swing.JFrame;

import java.awt.*;

public class InGameState extends JFrame {


    public InGameState(GameSettings gameSettings){
        AudioPlayer audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        setTitle("Boggled");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BoggledColors.SYSTEM_COLOR);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setVisible(true);

        audioPlayer.playMusic("main.wav");
    }
}

