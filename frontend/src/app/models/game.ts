import {Player} from "./player";

export class Game {
  board: number[][];
  player1: Player;
  player2: Player;
  currentPlayer: Player;
  gameWon: boolean;
  boardIsFull: boolean;
}
