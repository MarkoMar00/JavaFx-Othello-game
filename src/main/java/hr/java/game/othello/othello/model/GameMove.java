package hr.java.game.othello.othello.model;

import hr.java.game.othello.othello.enums.ButtonStyleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameMove implements Serializable {
    private ButtonStyleEnum pieceColor;
    private Integer positionX;
    private Integer positionY;
    private LocalDateTime localDateTime;
}
