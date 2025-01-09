package hr.java.game.othello.othello.thread;


import hr.java.game.othello.othello.model.GameMove;
import javafx.scene.control.Label;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReadLastGameMoveThread extends GameMoveThread implements Runnable {

    private Label label;

    @Override
    public void run() {
        GameMove lastGameMove = getLastMoveFromFile();

        label.setText("Last move played: " +
                lastGameMove.getPieceColor().getColor().toUpperCase() + ": " +
                "(" + lastGameMove.getPositionX() + ", " +
                lastGameMove.getPositionY() + ") " +
                lastGameMove.getLocalDateTime().getHour() + ":" + lastGameMove.getLocalDateTime().getMinute());
    }
}
