package temp.audio;

import temp.game.settings.AudioSettings;

import javax.sound.sampled.Clip;

public class MusicClip extends AudioClip {
    public MusicClip(Clip clip) {
        super(clip);
    }

    @Override
    protected float getVolume(AudioSettings audioSettings) {
        return audioSettings.getMusicVolume();
    }
}
