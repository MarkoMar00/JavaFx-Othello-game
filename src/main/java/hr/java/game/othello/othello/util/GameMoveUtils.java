package hr.java.game.othello.othello.util;

import hr.java.game.othello.othello.model.GameMove;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameMoveUtils {
    private static final String GAME_MOVE_FILE_NAME = "moves/game_moves.dat";
    private static List<GameMove> gameMoves = new ArrayList<>();

    public static void saveGameMove(GameMove gameMove) {
        gameMoves.add(gameMove);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_MOVE_FILE_NAME))){
            oos.writeObject(gameMoves);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameMove getLastGameMove() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAME_MOVE_FILE_NAME))){
            gameMoves = (List<GameMove>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return gameMoves.getLast();
    }
}
