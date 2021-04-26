package be.hogent.programmeren5.connectfour.game.service;

import be.hogent.programmeren5.connectfour.game.service.dto.Game;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final int columns = 7;
    private static final int rows = 6;
    private Game game;

    public Game getGame(){
        return game;
    }

    public Game startGame(Long player1, Long player2) //setting up a new board
    {
        game = new Game(columns, rows, player1, player2);
        game.setCurrentPlayer(player1);
        return game;
    }

    public boolean dropToken(int columnNumber, Long playerId){
        //validate move

        //dropping a token in the connect four board
        String[][] board = getGame().getBoard();
        board[0][columnNumber] =String.valueOf(playerId);
        getGame().setBoard(board);

        //check for win

        //set next player ID if game not won
        if (!getGame().isGameWon()) {
            Long player1 = getGame().getPlayer1();
            Long player2 = getGame().getPlayer2();
            if (getGame().getCurrentPlayer().equals(player1)) {
                getGame().setCurrentPlayer(player2);
            } else {
                getGame().setCurrentPlayer(player1);
            }
        }

        return true;
    }
}
