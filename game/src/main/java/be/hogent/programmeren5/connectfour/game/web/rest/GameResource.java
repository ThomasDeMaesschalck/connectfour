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
import java.util.Arrays;
import java.util.stream.StreamSupport;

/**
 * Game REST resource layer. Communicates with the frontend.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Log4j2
public class GameResource {

    @Autowired
    private GameService gameService;

    /**
     * Retrieve the current game
     * Perform check to see if board is full and adjust boardIsFull boolean accordingly
     * @return Returns the Game object
     */
    @GetMapping("/games")
    public ResponseEntity<Game> getGame() {
        Game game = gameService.getGame();
        log.info("Retrieved game details");

        if (this.gameService.getGame() != null) { //check if board is full
            if (this.gameService.getGame().isGameWon() == false && gameService.isBoardFull()) {
                log.info("Board is full");
                game.setBoardIsFull(true);
            }
        }
        return ResponseEntity.ok(game);
    }

    /**
     * Start a new game.
     * @param game Contains the two players
     * @return Game object
     * @throws Exception Throws exception when game creation fails
     */
    @PostMapping("/games")
    public ResponseEntity<Game> startGame(@Valid @RequestBody Game game) throws Exception {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        if(player1.equals(player2)){
            log.error("Failed to make game - players not unique");
            throw new Exception("Player 1 and Player 2 need to be unique");
        }

       try {
           Game gameStarted = gameService.startGame(player1, player2);
           log.info("Started a new game with player " + player1.getId() + " and player " + player2.getId());
           return ResponseEntity.ok(gameStarted);
       }
       catch (Exception e) {
           log.error("Failed to make new game");
           throw new Exception("Could not start game");
        }
    }

    /**
     * Gameplay actions. Lets a user drop a token in the specified Connect Four column.
     * @param columnNumber The selected Connect Four column
     * @return Boolean is returned to specify whether the token drop was valid
     * @throws Exception Thrown when an invalid move was made
     */
    @PostMapping("/droptoken/{columnNumber}")
    public ResponseEntity<Boolean> dropToken(@PathVariable int columnNumber) throws Exception {

        boolean validated = gameService.dropToken(columnNumber, gameService.getGame().getCurrentPlayer().getId());

        if (validated == false)
        {
            log.info("Token drop in column " + columnNumber + " failed as column is full" );
            throw new Exception("Not a valid move");
        }
        log.info("Dropped a token in column " + columnNumber);

        return ResponseEntity.ok(validated);
    }

}
