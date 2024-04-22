package temp.audio;

import temp.game.settings.AudioSettings;

import javax.sound.sampled.Clip;

public class SoundClip extends AudioClip {
    public SoundClip(Clip clip) {
        super(clip);
    }

    @Override
    protected float getVolume(AudioSettings audioSettings) {
        return audioSettings.getSoundVolume();
    }
}
