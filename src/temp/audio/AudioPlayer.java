package temp.audio;

public class AudioPlayer {
    protected Sound sound = new Sound();

    public void playMusic(int i) {
        sound.setFile(i);
        sound.playSound();
        sound.loopSound();
    }

    public void stopMusic() {
        sound.stopSound();
    }

    public void playSoundEffect(int i) {
        sound.setFile(i);
        sound.playSound();
    }

}
