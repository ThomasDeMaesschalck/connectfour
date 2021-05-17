package be.hogent.programmeren5.connectfour.game.service;

import be.hogent.programmeren5.connectfour.game.service.dto.Game;
import be.hogent.programmeren5.connectfour.game.service.dto.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private Player player1 = new Player();
    private Player player2 = new Player();
    private GameService gameService = new GameService();

    @BeforeEach
    public void setUp() {
       player1.setId(Long.parseLong("1"));
       player1.setName("Thomas");
       player1.setHighscore(Long.parseLong("2"));

       player2.setId(Long.parseLong("2"));
       player2.setName("Carmen");
       player2.setHighscore(Long.parseLong("2"));
    }


    @Test
    void startGame() {
        gameService.startGame(player1, player2);
        assertEquals("Thomas", gameService.getGame().getPlayer1().getName(), "Failed test 01 - first player name not corrrect");
        assertEquals("Thomas", gameService.getGame().getCurrentPlayer().getName(), "Failed test 02 - current player should be Thomas");
        assertEquals(7, gameService.getGame().getBoard().length, "Failed test 03 - board setup not correct");
    }


    @Test
    void validateMove() {
        gameService.startGame(player1, player2);
        assertEquals(true, gameService.validateMove(0), "Failed test 04 - move should be possible");
        gameService.dropToken(0, player1.getId());
        gameService.dropToken(0, player2.getId());
        gameService.dropToken(0, player1.getId());
        gameService.dropToken(0, player2.getId());
        assertEquals(true, gameService.validateMove(0), "Failed test 05 - move should be possible");
        gameService.dropToken(0, player1.getId());
        gameService.dropToken(0, player2.getId());
        assertEquals(false, gameService.validateMove(0), "Failed test 06 - move should not be possible");

    }

    @Test
    void setNextPlayer() {
        gameService.startGame(player1, player2);
        assertEquals(player1.getName(), gameService.getGame().getCurrentPlayer().getName(), "Failed test 07 - player should be Thomas");
        gameService.setNextPlayer();
        assertEquals(player2.getName(), gameService.getGame().getCurrentPlayer().getName(), "Failed test 08 - player should be Carmen");
    }

    @Test
    void isBoardFull() {
        gameService.startGame(player1, player2);
        assertEquals(false, gameService.isBoardFull(), "Failed test 09 - board is not full");
        gameService.dropToken(0, player1.getId());
        gameService.dropToken(0, player2.getId());
        gameService.dropToken(0, player1.getId());
        gameService.dropToken(0, player2.getId());
        assertEquals(false, gameService.isBoardFull(), "Failed test 10 - board is not full");
        gameService.dropToken(0, player1.getId());
        gameService.dropToken(0, player2.getId());

        int[] boardRowsFilledPerColumn = new int[7];
        boardRowsFilledPerColumn[0] = 6;
        boardRowsFilledPerColumn[1] = 6;
        boardRowsFilledPerColumn[2] = 6;
        boardRowsFilledPerColumn[3] = 6;
        boardRowsFilledPerColumn[4] = 6;
        boardRowsFilledPerColumn[5] = 6;
        boardRowsFilledPerColumn[6] = 6;
        gameService.setBoardRowsFilledPerColumn(boardRowsFilledPerColumn);
        assertEquals(true, gameService.isBoardFull(), "Failed test 11 - board is  full");
    }

    @Test
    void checkIfPlayerWon() {
        gameService.startGame(player1, player2);
        gameService.dropToken(1, player1.getId());
        gameService.getGame().setCurrentPlayer(player1);
        assertEquals(false, gameService.checkIfPlayerWon(), "Failed test 12 - game should not be won");

        Long[][] testHorizontalBoardWin = new Long[7][6];
        testHorizontalBoardWin[0][0] = player1.getId();
        testHorizontalBoardWin[2][0] = player2.getId();
        testHorizontalBoardWin[0][1] = player1.getId();
        testHorizontalBoardWin[3][0] = player2.getId();
        testHorizontalBoardWin[0][2] = player1.getId();
        testHorizontalBoardWin[1][0] = player2.getId();
        testHorizontalBoardWin[0][3] = player1.getId();
        gameService.getGame().setBoard(testHorizontalBoardWin);
        assertEquals(true, gameService.checkIfPlayerWon(), "Failed test 13 - game should be won");


        Long[][] testVerticalBoardWin = new Long[7][6];
        testVerticalBoardWin[0][0] = player1.getId();
        testVerticalBoardWin[0][1] = player2.getId();
        testVerticalBoardWin[1][0] = player1.getId();
        testVerticalBoardWin[1][1] = player2.getId();
        testVerticalBoardWin[2][0] = player1.getId();
        testVerticalBoardWin[2][1] = player2.getId();
        testVerticalBoardWin[3][0] = player1.getId();
        gameService.getGame().setBoard(testVerticalBoardWin);
        assertEquals(true, gameService.checkIfPlayerWon(), "Failed test 14 - game should be won");


        Long[][] testDiagonalBoardWin = new Long[7][6];
        testDiagonalBoardWin[0][0] = player1.getId();
        testDiagonalBoardWin[0][1] = player2.getId();

        testDiagonalBoardWin[1][1] = player1.getId();
        testDiagonalBoardWin[5][0] = player2.getId();

        testDiagonalBoardWin[2][2] = player1.getId();
        testDiagonalBoardWin[2][1] = player2.getId();

        gameService.getGame().setBoard(testDiagonalBoardWin);
        assertEquals(false, gameService.checkIfPlayerWon(), "Failed test 15 - game should not be won");

        testDiagonalBoardWin[3][3] = player1.getId();
        gameService.getGame().setBoard(testDiagonalBoardWin);
        assertEquals(true, gameService.checkIfPlayerWon(), "Failed test 16 - game should be won");

        Long[][] testReverseDiagonalBoardWin = new Long[7][6];
        testReverseDiagonalBoardWin[0][3] = player1.getId();
        testReverseDiagonalBoardWin[5][0] = player2.getId();

        testReverseDiagonalBoardWin[1][2] = player1.getId();
        testReverseDiagonalBoardWin[0][1] = player2.getId();

        testReverseDiagonalBoardWin[2][1] = player1.getId();
        testReverseDiagonalBoardWin[0][2] = player2.getId();
        gameService.getGame().setBoard(testReverseDiagonalBoardWin);
        assertEquals(false, gameService.checkIfPlayerWon(), "Failed test 17 - game should not be won");

        testReverseDiagonalBoardWin[3][0] = player1.getId();
        gameService.getGame().setBoard(testReverseDiagonalBoardWin);
        assertEquals(true, gameService.checkIfPlayerWon(), "Failed test 18 - game should be won");
    }

}
