package be.hogent.programmeren5.connectfour.game.service.dto;

import lombok.Data;

/**
 * Game DTO class
 */
@Data
public class Game {

    /**
     * Game constructor
     * @param columns Number of colums of game board
     * @param rows Number of rows of game board
     * @param player1 The first player
     * @param player2 The second player
     */
    public Game(int columns, int rows, Player player1, Player player2) {
        this.board =  new Long[columns][rows];
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * 2D Array of the game board
     */
    private Long[][] board;

    /**
     * First player
     */
    private Player player1;

    /**
     * Second player
     */
    private Player player2;

    /**
     * Player who needs to make a move in the current game round
     */
    private Player currentPlayer;

    /**
     * Set to true when game win condition is met
     */
    private boolean gameWon;

    /**
     * Set to true when the array is full and game was not won. This means both players lost
     */
    private boolean boardIsFull;
}
