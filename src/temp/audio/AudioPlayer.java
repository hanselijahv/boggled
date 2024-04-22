package temp.audio;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import temp.game.settings.AudioSettings;

import javax.sound.sampled.*;

public class AudioPlayer {

    private final AudioSettings audioSettings;
    private final List<AudioClip> audioClips;
    private Clip clip;

    public AudioPlayer(AudioSettings audioSettings) {
        this.audioSettings = audioSettings;
        audioClips = new ArrayList<>();
    }

    public void update() {
        audioClips.forEach(audioClip -> audioClip.update(audioSettings));

        for (AudioClip audioClip : audioClips) {
            if(audioClip.hasFinishedPlaying()){
                audioClip.cleanUp();
                audioClips.remove(audioClip);
            }
        }
    }

    public void playMusic(String fileName) {
        clip = getClip(fileName);
        final MusicClip musicClip = new MusicClip(clip);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        musicClip.setVolume(audioSettings);
        audioClips.add(musicClip);
    }

    public void playSound(String fileName) {
        final Clip clip = getClip(fileName);
        final SoundClip soundClip = new SoundClip(clip);
        soundClip.setVolume(audioSettings);
        audioClips.add(soundClip);
    }

    private Clip getClip(String fileName) {
        final URL soundFile = AudioPlayer.class.getResource("/sound/" + fileName);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(0);
            return clip;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: No Audio Devices Found!");
        } catch (LineUnavailableException ex) {
            System.out.println("Error: Requested line is already in use by another application!");
        } catch (IOException ioe) {
            System.out.println("Error: File not found!");
        } catch (UnsupportedAudioFileException uae) {
            System.out.println("Error: Unsupported audio format!");
        }

        return null;
    }

    public void clear() {
        audioClips.forEach(AudioClip::cleanUp);
        audioClips.clear();
    }

}
