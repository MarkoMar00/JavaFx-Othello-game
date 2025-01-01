package hr.java.game.othello.othello;

import hr.java.game.othello.othello.enums.ButtonStyleEnum;
import hr.java.game.othello.othello.model.BoardState;
import hr.java.game.othello.othello.util.DocumentationUtil;
import hr.java.game.othello.othello.util.GameBoardUtils;
import hr.java.game.othello.othello.util.GameRules;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import static hr.java.game.othello.othello.model.BoardState.NUMBER_OF_COLUMNS;
import static hr.java.game.othello.othello.model.BoardState.NUMBER_OF_ROWS;

public class OthelloController {
    @FXML
    private Button button00;
    @FXML
    private Button button01;
    @FXML
    private Button button02;
    @FXML
    private Button button03;
    @FXML
    private Button button04;
    @FXML
    private Button button05;
    @FXML
    private Button button06;
    @FXML
    private Button button07;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button13;
    @FXML
    private Button button14;
    @FXML
    private Button button15;
    @FXML
    private Button button16;
    @FXML
    private Button button17;
    @FXML
    private Button button20;
    @FXML
    private Button button21;
    @FXML
    private Button button22;
    @FXML
    private Button button23;
    @FXML
    private Button button24;
    @FXML
    private Button button25;
    @FXML
    private Button button26;
    @FXML
    private Button button27;
    @FXML
    private Button button30;
    @FXML
    private Button button31;
    @FXML
    private Button button32;
    @FXML
    private Button button33;
    @FXML
    private Button button34;
    @FXML
    private Button button35;
    @FXML
    private Button button36;
    @FXML
    private Button button37;
    @FXML
    private Button button40;
    @FXML
    private Button button41;
    @FXML
    private Button button42;
    @FXML
    private Button button43;
    @FXML
    private Button button44;
    @FXML
    private Button button45;
    @FXML
    private Button button46;
    @FXML
    private Button button47;
    @FXML
    private Button button50;
    @FXML
    private Button button51;
    @FXML
    private Button button52;
    @FXML
    private Button button53;
    @FXML
    private Button button54;
    @FXML
    private Button button55;
    @FXML
    private Button button56;
    @FXML
    private Button button57;
    @FXML
    private Button button60;
    @FXML
    private Button button61;
    @FXML
    private Button button62;
    @FXML
    private Button button63;
    @FXML
    private Button button64;
    @FXML
    private Button button65;
    @FXML
    private Button button66;
    @FXML
    private Button button67;
    @FXML
    private Button button70;
    @FXML
    private Button button71;
    @FXML
    private Button button72;
    @FXML
    private Button button73;
    @FXML
    private Button button74;
    @FXML
    private Button button75;
    @FXML
    private Button button76;
    @FXML
    private Button button77;

    public static ButtonStyleEnum currentPlayerColor;
    public static Button[][] board;

    public void initialize() {
        currentPlayerColor = ButtonStyleEnum.BLACK;
        board = new Button[8][8];

        board[0][0] = button00;
        board[0][1] = button01;
        board[0][2] = button02;
        board[0][3] = button03;
        board[0][4] = button04;
        board[0][5] = button05;
        board[0][6] = button06;
        board[0][7] = button07;
        board[1][0] = button10;
        board[1][1] = button11;
        board[1][2] = button12;
        board[1][3] = button13;
        board[1][4] = button14;
        board[1][5] = button15;
        board[1][6] = button16;
        board[1][7] = button17;
        board[2][0] = button20;
        board[2][1] = button21;
        board[2][2] = button22;
        board[2][3] = button23;
        board[2][4] = button24;
        board[2][5] = button25;
        board[2][6] = button26;
        board[2][7] = button27;
        board[3][0] = button30;
        board[3][1] = button31;
        board[3][2] = button32;
        board[3][3] = button33;
        board[3][4] = button34;
        board[3][5] = button35;
        board[3][6] = button36;
        board[3][7] = button37;
        board[4][0] = button40;
        board[4][1] = button41;
        board[4][2] = button42;
        board[4][3] = button43;
        board[4][4] = button44;
        board[4][5] = button45;
        board[4][6] = button46;
        board[4][7] = button47;
        board[5][0] = button50;
        board[5][1] = button51;
        board[5][2] = button52;
        board[5][3] = button53;
        board[5][4] = button54;
        board[5][5] = button55;
        board[5][6] = button56;
        board[5][7] = button57;
        board[6][0] = button60;
        board[6][1] = button61;
        board[6][2] = button62;
        board[6][3] = button63;
        board[6][4] = button64;
        board[6][5] = button65;
        board[6][6] = button66;
        board[6][7] = button67;
        board[7][0] = button70;
        board[7][1] = button71;
        board[7][2] = button72;
        board[7][3] = button73;
        board[7][4] = button74;
        board[7][5] = button75;
        board[7][6] = button76;
        board[7][7] = button77;

    }

    public void movePlayed(Event event) {
        if (event.getSource() instanceof Button b) {
            for (int row = 0; row < NUMBER_OF_ROWS; row++){
                for (int col = 0; col < NUMBER_OF_COLUMNS; col++){
                    if (board[row][col].equals(event.getSource())){
                        if (GameRules.checkIfLegalMove(currentPlayerColor, board, row, col)){
                            GameRules.flipPieces(currentPlayerColor, row, col, board);
                            board[row][col].setStyle(currentPlayerColor.getStyle());
                            GameRules.isGameOver(board);
                            currentPlayerColor = (currentPlayerColor.getStyle().contains(ButtonStyleEnum.WHITE.getColor()))
                                    ? ButtonStyleEnum.BLACK : ButtonStyleEnum.WHITE;
                        }
                    }
                }
            }
        }
    }

    public void newGame() {
        GameBoardUtils.startNewGame(board);
        currentPlayerColor = ButtonStyleEnum.BLACK;
    }

    public void saveGame() {
        GameBoardUtils.saveGame(board, currentPlayerColor);
    }

    public void loadGame() {
        currentPlayerColor = GameBoardUtils.loadGame(board);
    }

    public void generateDocumentation() {
        DocumentationUtil.generateDocumentation();
    }
}