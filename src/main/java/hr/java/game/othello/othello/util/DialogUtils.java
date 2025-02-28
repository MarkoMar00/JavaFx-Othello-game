package hr.java.game.othello.othello.util;

import hr.java.game.othello.othello.enums.ButtonStyleEnum;
import javafx.scene.control.Alert;

public class DialogUtils {
    public static void showWinner(ButtonStyleEnum winner) {
        Alert alert =  new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner!");
        alert.setHeaderText(null);
        alert.setContentText("The winner is " + winner.getColor().toUpperCase());
        alert.showAndWait();
    }

    public static void showDraw() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Draw!");
        alert.setHeaderText(null);
        alert.setContentText("It's a draw! No winner this time!");
        alert.showAndWait();
    }

    public static void showActionSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showActionFailure(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
