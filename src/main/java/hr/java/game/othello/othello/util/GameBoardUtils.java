package hr.java.game.othello.othello.util;

import hr.java.game.othello.othello.Othello;
import hr.java.game.othello.othello.enums.ButtonStyleEnum;
import hr.java.game.othello.othello.enums.Player;
import hr.java.game.othello.othello.model.BoardState;
import hr.java.game.othello.othello.model.GameMove;
import hr.java.game.othello.othello.thread.SaveGameMoveThread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.*;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static hr.java.game.othello.othello.OthelloController.*;
import static hr.java.game.othello.othello.model.BoardState.NUMBER_OF_COLUMNS;
import static hr.java.game.othello.othello.model.BoardState.NUMBER_OF_ROWS;


public class GameBoardUtils {
    private static List<Button[][]> listOfMoves = new ArrayList<>();

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

    public static void loadGame(Button[][] board) {
        ButtonStyleEnum currentPlayer = ButtonStyleEnum.BLACK;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BoardState.SAVED_GAME_FILE_NAME))){
            BoardState boardToLoad = (BoardState) ois.readObject();
            BoardState.convertBoardFromStyleToButton(boardToLoad.getBoardState(), board);
            currentPlayer = boardToLoad.getCurrentPlayer();
            DialogUtils.showActionSuccess("Successfully loaded the game!");
        } catch (IOException | ClassNotFoundException e){
            DialogUtils.showActionFailure("Failed to load the game!");
        }

        currentPlayerColor = currentPlayer;
    }

    public static void movePlayed(Event event) {
        if (event.getSource() instanceof Button b) {
            BoardState boardState = new BoardState();
            boardState.setBoardState(new String[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS]);

            updateGameBoard(boardState);

            for (int row = 0; row < NUMBER_OF_ROWS; row++){
                for (int col = 0; col < NUMBER_OF_COLUMNS; col++){
                    if (board[row][col].equals(event.getSource())){
                        if (GameRules.checkIfLegalMove(currentPlayerColor, board, row, col)){
                            GameMove newGameMove = new GameMove(currentPlayerColor, row, col, LocalDateTime.now());
                            SaveGameMoveThread saveGameMoveThread = new SaveGameMoveThread(newGameMove);
                            Thread starter = new Thread(saveGameMoveThread);
                            starter.start();

                            GameRules.flipPieces(currentPlayerColor, row, col, board);
                            board[row][col].setStyle(currentPlayerColor.getStyle());

                            listOfMoves.add(copyBoard(board));
                            XmlUtils.saveGameMovesToXml(listOfMoves);
                            GameRules.isGameOver(board);

                            if (Othello.player.name().equals(Player.SINGLE_PLAYER.name())) {
                                currentPlayerColor = (currentPlayerColor.getStyle().contains(ButtonStyleEnum.WHITE.getColor()))
                                        ? ButtonStyleEnum.BLACK : ButtonStyleEnum.WHITE;
                            }
                        }
                    }
                }
            }
            updateGameBoard(boardState);

            if (Othello.player.name().equals(Player.WHITE_PLAYER.name())) {
                MultiplayerGameUtil.whitePlayerSendRequest(boardState);
            } else if (Othello.player.name().equals(Player.BLACK_PLAYER.name())) {
                MultiplayerGameUtil.blackPlayerSendRequest(boardState);
            }
            MultiplayerGameUtil.deactivateButtons(true);
        }
    }

    private static void updateGameBoard(BoardState boardState) {
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
                boardState.getBoardState()[row][col] = board[row][col].getStyle();
            }
        }

        if (currentPlayerColor.equals(ButtonStyleEnum.BLACK)) {
            boardState.setCurrentPlayer(ButtonStyleEnum.WHITE);
        } else if (currentPlayerColor.equals(ButtonStyleEnum.WHITE)) {
            boardState.setCurrentPlayer(ButtonStyleEnum.BLACK);
        }
    }

    private static Button[][] copyBoard(Button[][] board) {
        Button[][] newBoard = new Button[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
                Button originalButton = board[row][col];
                Button newButton = new Button(originalButton.getText());
                newButton.setStyle(originalButton.getStyle());
                newBoard[row][col] = newButton;
            }
        }
        return newBoard;
    }

    public static void refreshChatArea(TextArea chatTextArea) {
        List<String> chatHistory;
        try {
            chatHistory = stub.returnChatHistory();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        for (String message : chatHistory) {
            sb.append(message);
            sb.append("\n");
        }
        chatTextArea.setText(sb.toString());
    }

    public static void sendChatMessage(TextField chatTextField, TextArea chatTextArea) {
        if (!Othello.player.name().equals(Player.SINGLE_PLAYER.name())) {
            String message = chatTextField.getText();
            if (!message.isEmpty()) {
                try {
                    stub.sendMessage(Othello.player.name() + ": " + message);
                    GameBoardUtils.refreshChatArea(chatTextArea);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                chatTextField.setText("");
            }
        }
    }

    public static void replayGame(Button[][] board) {
        List<Button[][]> gameMoves = XmlUtils.readGameMovesFromXml();
        AtomicInteger i = new AtomicInteger(0);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            Button[][] gameMove = gameMoves.get(i.get());
            for (int row = 0; row < NUMBER_OF_ROWS; row++) {
                for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
                    board[row][col].setStyle(gameMove[row][col].getStyle());
                }
            }
            i.set(i.get() + 1);
        }));
        timeline.setCycleCount(gameMoves.size());
        timeline.playFromStart();
    }
}
