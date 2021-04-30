package be.hogent.programmeren5.connectfour.game.service;

import be.hogent.programmeren5.connectfour.game.service.dto.Game;
import be.hogent.programmeren5.connectfour.game.service.dto.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class GameService {

    @Value("http://localhost:5551/api/increasescore/")
    private String urlPlayersHighscore;

    private static final int columns = 7;
    private static final int rows = 6;
    private Game game;
    private int[] boardRowsFilledPerColumn;

    public Game getGame(){
        return game;
    }

    public Game startGame(Player player1, Player player2) //setting up a new board
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
                if(game.isGameWon() == true)
                { //update highscore if game won
                    RestTemplate rtHighscoreUpdate = new RestTemplate();
                    rtHighscoreUpdate.postForLocation(urlPlayersHighscore+getGame().getCurrentPlayer().getId(), String.class);
                }
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
        if (getGame().getCurrentPlayer().equals(getGame().getPlayer1())) {
            getGame().setCurrentPlayer(getGame().getPlayer2());
        } else {
            getGame().setCurrentPlayer(game.getPlayer1());
        }
    }

    public boolean checkIfPlayerWon(){
     boolean winCheck = false;
     Long[][] board = getGame().getBoard();
     Long currentPlayer = getGame().getCurrentPlayer().getId();

     //check horizontal
        for(int row = 0; row < rows; row++){
            for (int col = 0; col < columns - 3; col++){
                if (board[col][row] == currentPlayer  &&
                        board[col+1][row] == currentPlayer  &&
                        board[col+2][row] == currentPlayer &&
                        board[col+3][row] == currentPlayer ){
                    winCheck = true;
                }
            }
        }

        //check vertical
        for(int row = 0; row < rows - 3; row++){
            for (int col = 0; col < columns; col++){
                if (board[col][row] == currentPlayer  &&
                        board[col][row+1] == currentPlayer  &&
                        board[col][row+2] == currentPlayer &&
                        board[col][row+3] == currentPlayer ){
                    winCheck = true;
                }
            }
        }

        //check diagonal
        for(int row = 3; row < rows - 3; row++){  //nakijken of dit klopt
            for(int col = 0; col < columns - 3; col++){
                if (board[col][row] == currentPlayer   &&
                        board[col+1][row-1] == currentPlayer &&
                        board[col+2][row-2] == currentPlayer &&
                        board[col+3][row-3] == currentPlayer){
                    return true;
                }
            }
        }

        //check reverse diagonal
        for(int row = 0; row < rows - 3; row++){
            for(int col = 0; col < columns - 3; col++){
                if (board[col][row] == currentPlayer   &&
                        board[col+1][row+1] == currentPlayer &&
                        board[col+2][row+2] == currentPlayer &&
                        board[col+3][row+3] == currentPlayer){
                    return true;
                }
            }
        }

     return winCheck;
    }
}
