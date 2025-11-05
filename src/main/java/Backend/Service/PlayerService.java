package Backend.Service;

import Utils.AudioPlayer;

public class PlayerService {
    private final AudioPlayer audioPlayer;

    public PlayerService(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    public void play(String filePath) {
        audioPlayer.load(filePath);
        audioPlayer.play();
    }

    public void pause() {
        audioPlayer.pause();
    }

    public void resume() {
        audioPlayer.play();
    }

    public void stop() {
        audioPlayer.stop();
    }
}






