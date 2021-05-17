package be.hogent.programmeren5.connectfour.game.service;

import be.hogent.programmeren5.connectfour.game.service.dto.Game;
import be.hogent.programmeren5.connectfour.game.service.dto.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Service class for the Game microservice
 */
@Service
public class GameService {

    /**
     * URL of Player API to increase the high score of the winning player
     */
    @Value("http://localhost:5551/api/increasescore/")
    private String urlPlayersHighscore;

    /**
     * Number of colums of the Connect Four board
     */
    private static final int columns = 7;

    /**
     *  Number of rows of the Connect Four board
     */
    private static final int rows = 6;

    /**
     * The game
     */
    private Game game;

    /**
     * Stores number of rows per column that have been filled by the players
     */
    private int[] boardRowsFilledPerColumn;


    /**
     * Start a Connect Four game.
     * Sets up the board.
     * Player 1 goes first.
     * @param player1 The first player
     * @param player2 The second player
     * @return Returns the created Game.
     */
    public Game startGame(Player player1, Player player2)
    {
        game = new Game(columns, rows, player1, player2);
        boardRowsFilledPerColumn = new int[columns];
        Arrays.fill(boardRowsFilledPerColumn, 0);
        game.setCurrentPlayer(player1);
        return game;
    }

    /**
     * Player drops a token in a row of the Connect Four board.
     * Method validates whether the move is valid
     * Method writes player id to the selected row
     * Method adjusts boardRowsFilledPerColumn array to reflect current situation.
     * Calls logic to check if player made a winning move.
     * If player made the winning move, REST API is used to increase player's high score.
     * If game was not won, next round goes to the other player
     * @param columnNumber The column where a token needs to be added to
     * @param playerId The player id of the player who made the move
     * @return Returns boolean true if move was valid
     */
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


    /**
     * Validates the player move.
     * @param columnNumber Column where a token was inserted into
     * @return Returns false if move is not possible.
     */
    public boolean validateMove(int columnNumber) {
        return boardRowsFilledPerColumn[columnNumber] < rows;
    }

    /**
     * Switches to player 1 if player 2 made a move, or other way around.
     */
    public void setNextPlayer() {
        if (getGame().getCurrentPlayer().equals(getGame().getPlayer1())) {
            getGame().setCurrentPlayer(getGame().getPlayer2());
        } else {
            getGame().setCurrentPlayer(game.getPlayer1());
        }
    }

    /**
     * Logic to check whether the current player made a winning move in the Connect Four game.
     * Method checks for horizontal, vertical, diagonal, and reverse diagonal win conditions.
     * If one condition is met, the current player won.
     * @return Returns true if current player made the winning move
     */
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

        //check reverse diagonal
        for(int row = 3; row < rows; row++){
            for(int col = 0; col < columns - 3; col++){
                if (board[col][row] == currentPlayer   &&
                        board[col+1][row-1] == currentPlayer &&
                        board[col+2][row-2] == currentPlayer &&
                        board[col+3][row-3] == currentPlayer){
                    return true;
                }
            }
        }

        //check diagonal
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

    /**
     * Checks whether all Connect Four rows are fully filled.
     * This is used to stop the game and means both players lost.
     * @return Returns true of all slots of the board are filled.
     */
    public boolean isBoardFull() {
        int slotsFilled = 0;
        for (int i = 0; i < columns; i++ )
        {
            slotsFilled += boardRowsFilledPerColumn[i];
        }
        if (columns * rows == slotsFilled) {
            return true;
        }
        return false;
    }

    public int[] getBoardRowsFilledPerColumn() {
        return boardRowsFilledPerColumn;
    }

    protected void setBoardRowsFilledPerColumn(int[] boardRowsFilledPerColumn) {
        this.boardRowsFilledPerColumn = boardRowsFilledPerColumn;
    }

    public Game getGame(){
        return game;
    }
}


