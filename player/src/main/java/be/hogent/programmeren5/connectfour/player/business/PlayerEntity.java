package be.hogent.programmeren5.connectfour.player.business;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name="Players")
@Data
public class PlayerEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    private Long highscore;

    public void increaseHighscore() {
        highscore++;
    }
}

