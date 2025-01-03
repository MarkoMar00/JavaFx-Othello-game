package hr.java.game.othello.othello.exception;

public class WrongPlayerException extends RuntimeException {
    public WrongPlayerException() {
    }

    public WrongPlayerException(String message) {
        super(message);
    }

    public WrongPlayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongPlayerException(Throwable cause) {
        super(cause);
    }

    public WrongPlayerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
