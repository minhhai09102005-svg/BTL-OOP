package Backend.Controller;

import Backend.Service.PlayerService;

public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void play(String filePath) { playerService.play(filePath); }
    public void pause() { playerService.pause(); }
    public void resume() { playerService.resume(); }
    public void stop() { playerService.stop(); }
}






