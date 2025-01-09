package hr.java.game.othello.othello.thread;

import hr.java.game.othello.othello.model.GameMove;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaveGameMoveThread extends GameMoveThread implements Runnable{

    private GameMove newGameMove;

    @Override
    public void run() {
        saveGameMoveToFile(newGameMove);
    }
}
