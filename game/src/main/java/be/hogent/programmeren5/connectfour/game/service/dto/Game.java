package be.hogent.programmeren5.connectfour.game.service.dto;

import lombok.Data;

@Data
public class Game {

    public Game(int columns, int rows, Player player1, Player player2) {
        this.board =  new Long[columns][rows];
        this.player1 = player1;
        this.player2 = player2;
    }

    private Long[][] board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameWon;
    private boolean boardIsFull;
}
