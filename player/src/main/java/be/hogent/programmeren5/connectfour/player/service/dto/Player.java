package be.hogent.programmeren5.connectfour.player.service.dto;

import lombok.Data;

/**
 * The Player DTO
 */
@Data
public class Player {
    /**
     * Id of the player
     */
    private Long id;

    /**
     * Name of the player
     */
    private String name;

    /**
     * Highscore of the player
     */
    private Long highscore;
}
