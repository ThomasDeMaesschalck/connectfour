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
        boolean validationStatus = false;
        if(validateMove(columnNumber))

        {
        //dropping a token in the connect four board
        Long[][] board = getGame().getBoard();
        int currentRowToFill = boardRowsFilledPerColumn[columnNumber];
        board[columnNumber][currentRowToFill] = playerId;
        getGame().setBoard(board);
        boardRowsFilledPerColumn[columnNumber]++;

        //check for win
            if(checkIfPlayerWon())
            {
                getGame().setGameWon(true);
            }

        //set next player ID if game not won
        if (!getGame().isGameWon()) {
            setNextPlayer();
        }
        validationStatus = true;
        }

        return validationStatus;
        }


    public boolean validateMove(int columnNumber) {
        return boardRowsFilledPerColumn[columnNumber] < rows;
    }

    public void setNextPlayer() {
        Long player1 = getGame().getPlayer1();
        Long player2 = getGame().getPlayer2();
        if (getGame().getCurrentPlayer().equals(player1)) {
            getGame().setCurrentPlayer(player2);
        } else {
            getGame().setCurrentPlayer(player1);
        }
    }

    public boolean checkIfPlayerWon(){
     boolean winCheck = false;
     Long currentPlayer = getGame().getCurrentPlayer();

     //check horizontal
        for(int row = 0; row < rows; row++){
            for (int col = 0; col < columns - 3; col++){
                if (getGame().getBoard()[col][row] == currentPlayer  &&
                        getGame().getBoard()[col+1][row] == currentPlayer  &&
                        getGame().getBoard()[col+2][row] == currentPlayer &&
                        getGame().getBoard()[col+3][row] == currentPlayer ){
                    winCheck = true;
                }
            }
        }

        //check vertical
        for(int row = 0; row < rows - 3; row++){
            for (int col = 0; col < columns; col++){
                if (getGame().getBoard()[col][row] == currentPlayer  &&
                        getGame().getBoard()[col][row+1] == currentPlayer  &&
                        getGame().getBoard()[col][row+2] == currentPlayer &&
                        getGame().getBoard()[col][row+3] == currentPlayer ){
                    winCheck = true;
                }
            }
        }


     return winCheck;
    }
}