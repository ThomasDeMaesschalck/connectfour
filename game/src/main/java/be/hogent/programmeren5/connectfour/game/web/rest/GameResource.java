package be.hogent.programmeren5.connectfour.game.web.rest;

import be.hogent.programmeren5.connectfour.game.service.GameService;
import be.hogent.programmeren5.connectfour.game.service.dto.Game;
import be.hogent.programmeren5.connectfour.game.service.dto.Player;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Log4j2
public class GameResource {

    @Autowired
    private GameService gameService;

    @GetMapping("/games")
    public ResponseEntity<Game> getGame(){
        Game game = gameService.getGame();
        log.info("Retrieved game details");
        return ResponseEntity.ok(game);
    }

    @PostMapping("/games")
    public ResponseEntity<Game> startGame(@Valid @RequestBody Game game)
    {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        if(player1.equals(player2)){
            log.error("Failed to make game - players not unique");

            return ResponseEntity.badRequest().build();
        }

        Game gameStarted = gameService.startGame(player1, player2);
        log.info("Started a new game with player " + player1.getId() + " and player " + player2.getId());
        return ResponseEntity.ok(gameStarted);
    }

    @PostMapping("/droptoken/{columnNumber}")
    public ResponseEntity<Boolean> dropToken(@PathVariable int columnNumber){
        Boolean validated = gameService.dropToken(columnNumber, gameService.getGame().getCurrentPlayer().getId());

        log.info("Dropped a token in column " + columnNumber);
        return ResponseEntity.ok(validated);
    }
}
