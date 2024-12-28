package hr.java.game.othello.othello.enums;

public enum ButtonStyleEnum {
    WHITE("-fx-background-radius: 100; -fx-background-color: white;", "white"),
    BLACK("-fx-background-radius: 100; -fx-background-color: black;", "black"),
    EMPTY("-fx-background-radius: 100; -fx-background-color: bisque;", "bisque"),;

    String style;
    String color;

    ButtonStyleEnum(String style, String color) {
        this.style = style;
        this.color = color;
    }

    public String getStyle() {
        return style;
    }

    public String getColor() {
        return color;
    }
}
