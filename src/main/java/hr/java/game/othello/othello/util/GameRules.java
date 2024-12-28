package hr.java.game.othello.othello.util;

import hr.java.game.othello.othello.enums.ButtonStyleEnum;
import javafx.scene.control.Button;


public class GameRules {
    private static boolean checkAdjacent(ButtonStyleEnum player, int deltaRow, int deltaCol, int row, int col, Button[][] board) {

        ButtonStyleEnum opponent = (player.getColor().contains(ButtonStyleEnum.WHITE.getColor())) ? ButtonStyleEnum.BLACK : ButtonStyleEnum.WHITE;

        if ((row + deltaRow < 0) || (row + deltaRow > 7)) {
            return false;
        }

        if ((col + deltaCol < 0) || (col + deltaCol > 7)) {
            return false;
        }

        if (!board[row + deltaRow][col + deltaCol].getStyle().contains(opponent.getColor())) {
            return false;
        }

        if ((row + deltaRow + deltaRow < 0) || (row + deltaRow + deltaRow > 7)) {
            return false;
        }

        if ((col + deltaCol + deltaCol < 0) || (col + deltaCol + deltaCol > 7)) {
            return false;
        }

        return checkIfLineMatches(player, deltaRow, deltaCol, row + deltaRow + deltaRow, col + deltaCol + deltaCol, board);
    }

    private static boolean checkIfLineMatches (ButtonStyleEnum player, int deltaRow, int deltaCol, int row, int col, Button[][] board) {
        if (board[row][col].getStyle().contains(player.getColor())) {
            return true;
        }

        if ((row + deltaRow < 0) || (row + deltaRow > 7)) {
            return false;
        }

        if ((col + deltaCol < 0) || (col + deltaCol > 7)) {
            return false;
        }

        return checkIfLineMatches(player, deltaRow, deltaCol, row + deltaRow, col + deltaCol, board);
    }

    public static boolean checkIfLegalMove (ButtonStyleEnum player, Button[][] board, int row, int col) {
        if (checkAdjacent(player, -1, -1, row, col, board)) return true;

        if (checkAdjacent(player, -1, 0, row, col, board)) return true;

        if (checkAdjacent(player, -1, 1, row, col, board)) return true;

        if (checkAdjacent(player, 0, -1, row, col, board)) return true;

        if (checkAdjacent(player, 0, 1, row, col, board)) return true;

        if (checkAdjacent(player, 1, -1, row, col, board)) return true;

        if (checkAdjacent(player, 1, 0, row, col, board)) return true;

        if (checkAdjacent(player, 1, 1, row, col, board)) return true;

        return false;
    }

    public static void flipPieces(ButtonStyleEnum player, int row, int col, Button[][] board) {
        flipLineOfPieces(player, -1, -1, row, col, board);
        flipLineOfPieces(player, -1, 0, row, col, board);
        flipLineOfPieces(player, -1, 1, row, col, board);
        flipLineOfPieces(player, 0, -1, row, col, board);
        flipLineOfPieces(player, 0, 1, row, col, board);
        flipLineOfPieces(player, 1, -1, row, col, board);
        flipLineOfPieces(player, 1, 0, row, col, board);
        flipLineOfPieces(player, 1, 1, row, col, board);
    }

    private static boolean flipLineOfPieces(ButtonStyleEnum player, int deltaRow, int deltaCol, int row, int col, Button[][] board) {
        if ((row + deltaRow < 0) || (row + deltaRow > 7)) {
            return false;
        }

        if ((col + deltaCol < 0) || (col + deltaCol > 7)) {
            return false;
        }

        if (board[row + deltaRow][col + deltaCol].getStyle().contains(ButtonStyleEnum.EMPTY.getColor())) {
            return false;
        }

        if (board[row + deltaRow][col + deltaCol].getStyle().contains(player.getColor())) {
            return true;
        }
        else {
            if (flipLineOfPieces(player, deltaRow, deltaCol, row + deltaRow, col + deltaCol, board)) {
                board[row + deltaRow][col + deltaCol].setStyle(player.getStyle());
                return true;
            } else {
                return false;
            }
        }
    }

    public static void isGameOver(Button[][] board, ButtonStyleEnum player) {
        int whiteCounter = 0, blackCounter = 0, legalMoves = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (checkIfLegalMove(player, board, i, j)) {
                    legalMoves++;
                }
            }
        }

        if (legalMoves == 0) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j].getStyle().contains(ButtonStyleEnum.WHITE.getColor())) whiteCounter++;
                    if (board[i][j].getStyle().contains(ButtonStyleEnum.BLACK.getColor())) blackCounter++;
                }
            }
            if (whiteCounter > blackCounter) {
                DialogUtils.showWinner(ButtonStyleEnum.WHITE);
            } else if (blackCounter > whiteCounter) {
                DialogUtils.showWinner(ButtonStyleEnum.BLACK);
            } else {
                DialogUtils.showDraw();
            }
        }
    }

    public static void isGameOver(Button[][] board) {
        int emptySpaces = 0, whiteCounter = 0, blackCounter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getStyle().contains(ButtonStyleEnum.EMPTY.getColor()))  {
                    emptySpaces++;
                }
            }
        }

        if (emptySpaces == 0) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j].getStyle().contains(ButtonStyleEnum.WHITE.getColor())) whiteCounter++;
                    if (board[i][j].getStyle().contains(ButtonStyleEnum.BLACK.getColor())) blackCounter++;
                }
            }
            if (whiteCounter > blackCounter) {
                DialogUtils.showWinner(ButtonStyleEnum.WHITE);
            } else if (blackCounter > whiteCounter) {
                DialogUtils.showWinner(ButtonStyleEnum.BLACK);
            } else {
                DialogUtils.showDraw();
            }
        }
    }

}
