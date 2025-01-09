package hr.java.game.othello.othello.thread;

import hr.java.game.othello.othello.model.GameMove;
import hr.java.game.othello.othello.util.GameMoveUtils;

public abstract class GameMoveThread {

    private static Boolean isFileAccessInProgress = false;

    protected synchronized void saveGameMoveToFile(GameMove gameMove) {
        while (isFileAccessInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isFileAccessInProgress = true;
        GameMoveUtils.saveGameMove(gameMove);
        isFileAccessInProgress = false;

        notifyAll();
    }

    protected synchronized GameMove getLastMoveFromFile() {
        while (isFileAccessInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isFileAccessInProgress = true;
        GameMove gameMove = GameMoveUtils.getLastGameMove();
        isFileAccessInProgress = false;

        notifyAll();
        return gameMove;
    }
}
