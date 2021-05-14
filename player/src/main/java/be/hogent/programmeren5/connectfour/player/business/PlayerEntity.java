package be.hogent.programmeren5.connectfour.player.business;

import lombok.Data;
import javax.persistence.*;

/**
 * Player Entity for the Player microservice. Uses database table "Players"
 */
@Entity
@Table(name="Players")
@Data
public class PlayerEntity {

    /**
     * Auto generated user id
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Name of the player. Needs to be unique.
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /**
     * Number of game wins
     */
    private Long highscore;

    /**
     * Increase the highscore by one
     */
    public void increaseHighscore() {
        highscore++;
    }
}

