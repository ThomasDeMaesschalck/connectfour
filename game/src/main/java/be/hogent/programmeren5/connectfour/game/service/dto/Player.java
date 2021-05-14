package be.hogent.programmeren5.connectfour.game.service.dto;

import lombok.Data;

/**
 * Player DTO class
 */
@Data
public class Player {
    /**
     * Player id
     */
    private Long id;

    /**
     * Player name
     */
    private String name;

    /**
     * Player highscore
     */
    private Long highscore;
}
