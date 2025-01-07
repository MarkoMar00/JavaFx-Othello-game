package hr.java.game.othello.othello.thread;

import hr.java.game.othello.othello.OthelloController;
import hr.java.game.othello.othello.jndi.ConfigurationReader;
import hr.java.game.othello.othello.model.BoardState;
import hr.java.game.othello.othello.model.ConfigurationKey;
import hr.java.game.othello.othello.util.DialogUtils;
import hr.java.game.othello.othello.util.MultiplayerGameUtil;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WhitePlayerThread implements Runnable{
    @Override
    public void run() {
        whitePlayerAcceptRequests();
    }

    private static void whitePlayerAcceptRequests() {
        Integer whitePlayerServerPort = Integer.parseInt(ConfigurationReader.getValue(ConfigurationKey.WHITE_PLAYER_SERVER_PORT));
        try (ServerSocket serverSocket = new ServerSocket(whitePlayerServerPort)){
            System.err.println("Client connected from port: "  + serverSocket.getLocalPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client connected from port: " + clientSocket.getPort());
                Platform.runLater(() ->  processSerializableClient(clientSocket));
            }
        } catch (IOException e) {
            DialogUtils.showActionFailure(e.toString());
        }
    }

    private static void processSerializableClient(Socket clientSocket) {
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())){

            BoardState boardState = (BoardState) ois.readObject();
            BoardState.convertBoardFromStyleToButton(boardState.getBoardState(), OthelloController.board);
            OthelloController.currentPlayerColor = boardState.getCurrentPlayer();
            MultiplayerGameUtil.deactivateButtons(false);

            System.out.println("White player received the game state!");
            oos.writeObject("White player received the game state - confirmation!");

        } catch (IOException | ClassNotFoundException e) {
            DialogUtils.showActionFailure(e.toString());
        }
    }
}
