module hr.java.game.othello.othello {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.java.game.othello.othello to javafx.fxml;
    exports hr.java.game.othello.othello;
}