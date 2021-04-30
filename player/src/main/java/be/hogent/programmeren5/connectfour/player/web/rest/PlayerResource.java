package be.hogent.programmeren5.connectfour.player.web.rest;

import be.hogent.programmeren5.connectfour.player.service.PlayerService;
import be.hogent.programmeren5.connectfour.player.service.dto.Player;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Log4j2
public class PlayerResource {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public ResponseEntity<Page<Player>> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "id") String sortBy)
    {
        Page<Player> players = playerService.getAll(pageNo, pageSize, sortBy);
        log.info("Retrieved all players");
        return ResponseEntity.ok(players);
    }

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


