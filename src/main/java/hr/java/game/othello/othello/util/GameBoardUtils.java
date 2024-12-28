package hr.java.game.othello.othello.util;

import hr.java.game.othello.othello.enums.ButtonStyleEnum;
import javafx.event.Event;
import javafx.scene.control.Button;

import static hr.java.game.othello.othello.OthelloController.NUMBER_OF_COLUMNS;
import static hr.java.game.othello.othello.OthelloController.NUMBER_OF_ROWS;

public class GameBoardUtils {

    public static void startNewGame(Button[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].setStyle(ButtonStyleEnum.EMPTY.getStyle());
            }
        }

        board[3][3].setStyle(ButtonStyleEnum.BLACK.getStyle());
        board[3][4].setStyle(ButtonStyleEnum.WHITE.getStyle());
        board[4][3].setStyle(ButtonStyleEnum.WHITE.getStyle());
        board[4][4].setStyle(ButtonStyleEnum.BLACK.getStyle());
    }
}
