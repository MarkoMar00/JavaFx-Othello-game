package hr.java.game.othello.othello.util;

import hr.java.game.othello.othello.Othello;
import hr.java.game.othello.othello.OthelloController;
import hr.java.game.othello.othello.enums.Player;
import hr.java.game.othello.othello.model.BoardState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MultiplayerGameUtil {

    public static void whitePlayerSendRequest(BoardState boardState) {
        try (Socket clientSocket = new Socket(Othello.HOST, Othello.BLACK_PLAYER_SERVER_PORT)) {
            System.err.println("Client is connecting to " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

            sendSerializableRequestToBlackPlayer(clientSocket, boardState);

        } catch (IOException | ClassNotFoundException e) {
            DialogUtils.showActionFailure(e.toString());
        }
    }

    public static void blackPlayerSendRequest(BoardState boardState) {
        try (Socket clientSocket = new Socket(Othello.HOST, Othello.WHITE_PLAYER_SERVER_PORT)) {
            System.err.println("Client is connecting to " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

            sendSerializableRequestToWhitePlayer(clientSocket, boardState);

        } catch (IOException | ClassNotFoundException e) {
            DialogUtils.showActionFailure(e.toString());
        }
    }

    public static void deactivateButtons(Boolean isDeactivated) {
        if (!Othello.player.equals(Player.SINGLE_PLAYER)) {
            for (int row = 0; row < BoardState.NUMBER_OF_ROWS; row++) {
                for (int col = 0; col < BoardState.NUMBER_OF_COLUMNS; col++) {
                    OthelloController.board[row][col].setDisable(isDeactivated);
                }
            }
        }
    }

    private static void sendSerializableRequestToBlackPlayer(Socket client, BoardState boardState) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        oos.writeObject(boardState);
        System.out.println("Game state sent to black player");
    }

    private static void sendSerializableRequestToWhitePlayer(Socket client, BoardState boardState) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        oos.writeObject(boardState);
        System.out.println("Game state sent to white player");
    }
}
