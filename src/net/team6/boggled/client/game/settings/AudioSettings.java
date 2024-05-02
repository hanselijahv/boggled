package net.team6.boggled.client.game.settings;

public class AudioSettings {
    private int musicVolume;
    private int soundVolume;

    public AudioSettings() {
        musicVolume = 70;
        soundVolume = 85;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume;
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = soundVolume;
    }
}
