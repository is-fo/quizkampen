package pojos;

import java.io.Serializable;

public class EndGame implements Serializable {

    private GameState gameState;

    public EndGame(GameState gameState) {
        this.gameState = gameState;
    }
    public GameState getGameState() {
        return gameState;
    }
}
