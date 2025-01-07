package hr.java.game.othello.othello.model;

import lombok.Getter;

@Getter
public enum ConfigurationKey {
    RMI_HOST("rmi.host"),
    RMI_PORT("rmi.port"),
    WHITE_PLAYER_SERVER_PORT("white.port"),
    BLACK_PLAYER_SERVER_PORT("black.port"),
    HOST("othello.host");

    private String key;

    ConfigurationKey(String key) {
        this.key = key;
    }
}
