module hr.java.game.othello.othello {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.rmi;
    requires java.naming;


    opens hr.java.game.othello.othello to javafx.fxml;
    exports hr.java.game.othello.othello;
    opens hr.java.game.othello.othello.chat to java.rmi;
}