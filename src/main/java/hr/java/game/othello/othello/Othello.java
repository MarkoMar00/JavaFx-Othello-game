package hr.java.game.othello.othello;

import hr.java.game.othello.othello.enums.Player;
import hr.java.game.othello.othello.exception.WrongPlayerException;
import hr.java.game.othello.othello.thread.BlackPlayerThread;
import hr.java.game.othello.othello.thread.WhitePlayerThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Othello extends Application {

    public static Stage mainStage;
    public static Player player;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Othello.class.getResource("othello.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 900);
        stage.setTitle(player.name());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        String firstArgument = args[0];

        try {
            player = Player.valueOf(firstArgument);
        } catch (IllegalArgumentException e) {
            throw new WrongPlayerException("The game was started with an unexpected player: " + firstArgument);
        }

        if (Player.valueOf(firstArgument).equals(Player.WHITE_PLAYER)) {
            player = Player.WHITE_PLAYER;
            Thread serverStarter = new Thread(new WhitePlayerThread());
            serverStarter.start();
        } else if (Player.valueOf(firstArgument).equals(Player.BLACK_PLAYER)) {
            player = Player.BLACK_PLAYER;
            Thread serverStarter = new Thread(new BlackPlayerThread());
            serverStarter.start();
        } else if (Player.valueOf(firstArgument).equals(Player.SINGLE_PLAYER)) {
            player = Player.SINGLE_PLAYER;
            System.err.println("Single player mode active");
        } else {
            throw new WrongPlayerException("The game was started with an unexpected player: " + firstArgument);
        }

        launch();
    }
}