package hr.java.game.othello.othello.model;

import hr.java.game.othello.othello.enums.ButtonStyleEnum;
import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardState implements Serializable {
    public static final Integer NUMBER_OF_ROWS = 8;
    public static final Integer NUMBER_OF_COLUMNS = 8;
    public static final String SAVED_GAME_FILE_NAME = "saves/saved_game.dat";

    private String[][] boardState;
    private ButtonStyleEnum currentPlayer;

    public static String[][] convertBoardFromButtonToButtonStyle(Button[][] board) {
        String[][] convertedBoard = new String[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                convertedBoard[i][j] = board[i][j].getStyle();
            }
        }

        return convertedBoard;
    }

    public static void convertBoardFromStyleToButton(String[][] boardOfStyles, Button[][] boardWithButtons) {
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                boardWithButtons[i][j].setStyle(boardOfStyles[i][j]);
            }
        }
    }

}
