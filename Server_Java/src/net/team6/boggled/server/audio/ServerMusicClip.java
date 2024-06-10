package net.team6.boggled.server.audio;

import net.team6.boggled.server.dev.settings.ServerAudioSettings;

import javax.sound.sampled.Clip;

public class ServerMusicClip extends ServerAudioClip {
    public ServerMusicClip(Clip clip) {
        super(clip);
    }

    @Override
    protected float getVolume(ServerAudioSettings audioSettings) {
        return audioSettings.getMusicVolume() / 100f;
    }

}
