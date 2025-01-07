package hr.java.game.othello.othello.util;

import hr.java.game.othello.othello.Othello;
import hr.java.game.othello.othello.OthelloController;
import hr.java.game.othello.othello.chat.ChatService;
import hr.java.game.othello.othello.enums.ButtonStyleEnum;
import hr.java.game.othello.othello.enums.Player;
import hr.java.game.othello.othello.jndi.ConfigurationReader;
import hr.java.game.othello.othello.model.ConfigurationKey;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static hr.java.game.othello.othello.OthelloController.*;

public class GameSetup {
    private OthelloController controller;

    public GameSetup(OthelloController controller) {
        this.controller = controller;
    }

    public void initializeBoard() {
        OthelloController.currentPlayerColor = ButtonStyleEnum.BLACK;
        board = new Button[8][8];

        board[0][0] = controller.getButton00();
        board[0][1] = controller.getButton01();
        board[0][2] = controller.getButton02();
        board[0][3] = controller.getButton03();
        board[0][4] = controller.getButton04();
        board[0][5] = controller.getButton05();
        board[0][6] = controller.getButton06();
        board[0][7] = controller.getButton07();
        board[1][0] = controller.getButton10();
        board[1][1] = controller.getButton11();
        board[1][2] = controller.getButton12();
        board[1][3] = controller.getButton13();
        board[1][4] = controller.getButton14();
        board[1][5] = controller.getButton15();
        board[1][6] = controller.getButton16();
        board[1][7] = controller.getButton17();
        board[2][0] = controller.getButton20();
        board[2][1] = controller.getButton21();
        board[2][2] = controller.getButton22();
        board[2][3] = controller.getButton23();
        board[2][4] = controller.getButton24();
        board[2][5] = controller.getButton25();
        board[2][6] = controller.getButton26();
        board[2][7] = controller.getButton27();
        board[3][0] = controller.getButton30();
        board[3][1] = controller.getButton31();
        board[3][2] = controller.getButton32();
        board[3][3] = controller.getButton33();
        board[3][4] = controller.getButton34();
        board[3][5] = controller.getButton35();
        board[3][6] = controller.getButton36();
        board[3][7] = controller.getButton37();
        board[4][0] = controller.getButton40();
        board[4][1] = controller.getButton41();
        board[4][2] = controller.getButton42();
        board[4][3] = controller.getButton43();
        board[4][4] = controller.getButton44();
        board[4][5] = controller.getButton45();
        board[4][6] = controller.getButton46();
        board[4][7] = controller.getButton47();
        board[5][0] = controller.getButton50();
        board[5][1] = controller.getButton51();
        board[5][2] = controller.getButton52();
        board[5][3] = controller.getButton53();
        board[5][4] = controller.getButton54();
        board[5][5] = controller.getButton55();
        board[5][6] = controller.getButton56();
        board[5][7] = controller.getButton57();
        board[6][0] = controller.getButton60();
        board[6][1] = controller.getButton61();
        board[6][2] = controller.getButton62();
        board[6][3] = controller.getButton63();
        board[6][4] = controller.getButton64();
        board[6][5] = controller.getButton65();
        board[6][6] = controller.getButton66();
        board[6][7] = controller.getButton67();
        board[7][0] = controller.getButton70();
        board[7][1] = controller.getButton71();
        board[7][2] = controller.getButton72();
        board[7][3] = controller.getButton73();
        board[7][4] = controller.getButton74();
        board[7][5] = controller.getButton75();
        board[7][6] = controller.getButton76();
        board[7][7] = controller.getButton77();
    }

    public void initializeChat(TextArea chatTextArea) {
        if(!Othello.player.name().equals(Player.SINGLE_PLAYER.name())) {
            try {
                String rmiPort = ConfigurationReader.getValue(ConfigurationKey.RMI_PORT);
                String serverName = ConfigurationReader.getValue(ConfigurationKey.RMI_HOST);
                Registry registry = LocateRegistry.getRegistry(serverName, Integer.parseInt(rmiPort));
                stub = (ChatService) registry.lookup(ChatService.REMOTE_OBJECT_NAME);
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> GameBoardUtils.refreshChatArea(chatTextArea)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.playFromStart();
        }
/*
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            Platform.runLater(new GetLastGameMoveThread(lastGameMoveLabel));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();*/
    }
}
