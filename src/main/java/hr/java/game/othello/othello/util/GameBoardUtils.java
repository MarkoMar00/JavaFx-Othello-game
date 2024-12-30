package hr.java.game.othello.othello.util;

import hr.java.game.othello.othello.enums.ButtonStyleEnum;
import hr.java.game.othello.othello.model.BoardState;
import javafx.scene.control.Button;

import java.io.*;


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

    public static void saveGame(Button[][] board, ButtonStyleEnum currentPlayer) {
        String[][] savedBoardStyles = BoardState.convertBoardFromButtonToButtonStyle(board);
        BoardState boardState = new BoardState(savedBoardStyles, currentPlayer);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BoardState.SAVED_GAME_FILE_NAME))){
            oos.writeObject(boardState);
            DialogUtils.showActionSuccess("Successfully saved the game!");
        } catch (IOException e) {
            DialogUtils.showActionFailure("Failed to save the game!");
        }
    }

    public static ButtonStyleEnum loadGame(Button[][] board) {
        ButtonStyleEnum currentPlayer = ButtonStyleEnum.BLACK;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BoardState.SAVED_GAME_FILE_NAME))){
            BoardState boardToLoad = (BoardState) ois.readObject();
            BoardState.convertBoardFromStyleToButton(boardToLoad.getBoardState(), board);
            currentPlayer = boardToLoad.getCurrentPlayer();
            DialogUtils.showActionSuccess("Successfully loaded the game!");
        } catch (IOException | ClassNotFoundException e){
            DialogUtils.showActionFailure("Failed to load the game!");
        }

        return currentPlayer;
    }
}
