package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip;

    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/main.wav");
        soundURL[1] = getClass().getResource("/sound/cursorSE.wav");
        soundURL[2] = getClass().getResource("/sound/selectSE.wav");
    }


    public void playSound() {
        clip.start();
    }

    public void stopSound() {
        clip.stop();
    }

    public void loopSound() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setFile(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: No Audio Devices Found!");
        } catch (LineUnavailableException ex) {
            System.out.println("Error: Requested line is already in use by another application!");
        } catch (IOException ioe) {
            System.out.println("Error: File not found!");
        } catch (UnsupportedAudioFileException uae) {
            System.out.println("Error: Unsupported audio format!");
        }
    }
}
