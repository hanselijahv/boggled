package net.team6.boggled.user.client.audio;

import net.team6.boggled.user.client.game.settings.AudioSettings;

import javax.sound.sampled.Clip;

public class MusicClip extends AudioClip {
    public MusicClip(Clip clip) {
        super(clip);
    }

    @Override
    protected float getVolume(AudioSettings audioSettings) {
        return audioSettings.getMusicVolume() / 100f;
    }
}
