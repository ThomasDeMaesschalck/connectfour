package be.hogent.programmeren5.connectfour.player.business;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Players")
@Data
public class PlayerEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long highscore;
}
