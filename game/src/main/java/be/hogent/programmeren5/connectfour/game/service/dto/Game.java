package be.hogent.programmeren5.connectfour.game.service.dto;

import lombok.Data;

@Data
public class Game {

    public Game(int columns, int rows, Long player1, Long player2) {
        this.board =  new Long[columns][rows];
        this.player1 = player1;
        this.player2 = player2;
    }

    private Long[][] board;
    private Long player1;
    private Long player2;
    private Long currentPlayer;
    private boolean gameWon;

}
