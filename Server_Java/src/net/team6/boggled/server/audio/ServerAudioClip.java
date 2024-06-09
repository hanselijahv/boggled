package net.team6.boggled.server.audio;

import Server_Java.net.team6.boggled.server.dev.settings.ServerAudioSettings;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public abstract class ServerAudioClip {

    private final Clip clip;

    public ServerAudioClip(Clip clip) {
        this.clip = clip;
        clip.start();
    }

    public void update(ServerAudioSettings audioSettings) {
        setVolume(audioSettings);
    }

    void setVolume(ServerAudioSettings audioSettings) {
        final FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMaximum() - control.getMinimum();
        float gain = (range * getVolume(audioSettings)) + control.getMinimum();

        control.setValue(gain);
    }

    protected abstract float getVolume(ServerAudioSettings audioSettings);

    public boolean hasFinishedPlaying() {
        return !clip.isRunning();
    }

    public void cleanUp() {
        clip.stop();
    }
}

