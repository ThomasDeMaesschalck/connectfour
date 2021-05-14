package be.hogent.programmeren5.connectfour.player.web.rest;

import be.hogent.programmeren5.connectfour.player.service.PlayerService;
import be.hogent.programmeren5.connectfour.player.service.dto.Player;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Player microservice REST communication with the frontend.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Log4j2
public class PlayerResource {

    /**
     * The PlayerService
     */
    @Autowired
    private PlayerService playerService;

    /**
     * Get a paginated list of all players persisted in the database
     * @param pageNo The page number requested. Default is 0.
     * @param pageSize The page size. Default is 10.
     * @param sortBy The specified sortBy. Default is id.
     * @return A page of the players
     */
    @GetMapping("/players")
    public ResponseEntity<Page<Player>> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "id") String sortBy)
    {
        Page<Player> players = playerService.getAll(pageNo, pageSize, sortBy);
        log.info("Retrieved all players");
        return ResponseEntity.ok(players);
    }

    /**
     * Get player information from database
     * @param id Id of the player
     * @return The Player
     * @throws Exception Thrown when player is not found.
     */
    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getById(@PathVariable Long id) throws Exception {
        Player player = playerService.getById(id);
        if(player == null){
            log.error("Failed to find player number " + id);
            throw new Exception("Player not found");
        }
        log.info("Retrieved player number " + id);
        return ResponseEntity.ok(player);
    }

    /**
     * Create a new player.
     * @param player The player that needs to be persisted.
     * @return Player
     * @throws Exception Thrown when Player name is a duplicate
     */
    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player) throws Exception {
        if (playerService.save(player)) {
            log.info("Saved player " + player);
            return ResponseEntity.ok(player);
        }
        else
        {
            log.error("Failed to add player - duplicate name " + player.getName());
            throw new Exception("Player already exists - duplicate name");
        }
    }

    /**
     * Increase the highscore of player who won a game
     * @param id Player id
     * @return Boolean true if action a success
     * @throws Exception Is thrown when action fails.
     */
    @PostMapping("/increasescore/{id}")
    public ResponseEntity<Boolean> increaseScoreById(@PathVariable Long id) throws Exception {
        boolean found = playerService.increaseScore(id);
        if(!found){
            log.error("Failed to increase highscore of player number " + id);
            throw new Exception("Problem with increasing highscore for player " + id);
        }
        else {
            log.info("Increased highscore of player number " + id);
            return ResponseEntity.ok(true);
        }
    }

}


