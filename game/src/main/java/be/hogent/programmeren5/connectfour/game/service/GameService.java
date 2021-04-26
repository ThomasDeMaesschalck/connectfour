package be.hogent.programmeren5.connectfour.game.service;

import be.hogent.programmeren5.connectfour.game.service.dto.Game;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class GameService {

    private static final int columns = 7;
    private static final int rows = 6;
    private Game game;
    private int[] boardRowsFilledPerColumn;


    public Game getGame(){
        return game;
    }

    public Game startGame(Long player1, Long player2) //setting up a new board
    {
        game = new Game(columns, rows, player1, player2);
        boardRowsFilledPerColumn = new int[columns];
        Arrays.fill(boardRowsFilledPerColumn, 0);
        game.setCurrentPlayer(player1);
        return game;
    }

    public boolean dropToken(int columnNumber, Long playerId){
        //validate move
        if(validateMove(columnNumber))
        {
        //dropping a token in the connect four board
        String[][] board = getGame().getBoard();
        int currentRowToFill = boardRowsFilledPerColumn[columnNumber];
        board[columnNumber][currentRowToFill] =String.valueOf(playerId);
        getGame().setBoard(board);
        boardRowsFilledPerColumn[columnNumber]++;

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
        else
        {
        return false;
        }
    }

    public boolean validateMove(int columnNumber) {
        if(boardRowsFilledPerColumn[columnNumber] < rows)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
