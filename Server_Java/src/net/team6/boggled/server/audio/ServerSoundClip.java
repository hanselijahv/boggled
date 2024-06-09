package net.team6.boggled.server.audio;

import Server_Java.net.team6.boggled.server.dev.settings.ServerAudioSettings;

import javax.sound.sampled.Clip;

public class ServerSoundClip extends ServerAudioClip {
    public ServerSoundClip(Clip clip) {
        super(clip);
    }

    @Override
    protected float getVolume(ServerAudioSettings audioSettings) {
        return audioSettings.getSoundVolume() / 100f;
    }
}
