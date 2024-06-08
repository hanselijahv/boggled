package Server_Java.net.team6.boggled.server.audio;

import Server_Java.net.team6.boggled.server.dev.settings.ServerAudioSettings;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServerAudioPlayer {

    private final ServerAudioSettings audioSettings;
    private final List<ServerAudioClip> serverAudioClips;
    private Clip clip;

    public ServerAudioPlayer(ServerAudioSettings audioSettings) {
        this.audioSettings = audioSettings;
        serverAudioClips = new ArrayList<>();
    }

    public void update() {
        serverAudioClips.forEach(serverAudioClip -> serverAudioClip.update(audioSettings));

        List<ServerAudioClip> clipsCopy = new ArrayList<>(serverAudioClips);

        clipsCopy.forEach(serverAudioClip -> {
            if (serverAudioClip.hasFinishedPlaying()) {
                serverAudioClip.cleanUp();
                serverAudioClips.remove(serverAudioClip);
            }
        });
    }

    public void playMusic(String fileName) {
        clip = getClip(fileName);
        final ServerMusicClip musicClip = new ServerMusicClip(clip);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        musicClip.setVolume(audioSettings);
        serverAudioClips.add(musicClip);
    }

    public void playSound(String fileName) {
        final Clip clip = getClip(fileName);
        final ServerSoundClip soundClip = new ServerSoundClip(clip);
        soundClip.setVolume(audioSettings);
        serverAudioClips.add(soundClip);
    }

    private Clip getClip(String fileName) {
        final URL soundFile = ServerAudioPlayer.class.getResource("/sound/" + fileName);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(0);
            return clip;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: No Audio Devices Found!");
        } catch (LineUnavailableException ex) {
            System.out.println("Error: Requested line is already in use by another application!" + ex.getMessage());
        } catch (IOException ioe) {
            System.out.println("Error: File not found!");
        } catch (UnsupportedAudioFileException uae) {
            System.out.println("Error: Unsupported audio format!");
        }

        return null;
    }

    public void clear() {
        serverAudioClips.forEach(ServerAudioClip::cleanUp);
        serverAudioClips.clear();
    }

}
