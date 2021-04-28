package be.hogent.programmeren5.connectfour.game.service.dto;

import lombok.Data;

@Data
public class Player {
    private Long id;
    private String name;
    private Long highscore;
}
