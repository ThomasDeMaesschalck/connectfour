import { Component, OnInit } from '@angular/core';
import {Player} from "../../models/player";
import {PlayerService} from "../../player/player.service";
import {Game} from "../../models/game";
import {GameService} from "../game.service";
import {Router} from "@angular/router";
import {PlayerFilter} from "../../player/player-filter";

@Component({
  selector: 'app-startgame',
  templateUrl: './startgame.component.html'
})
export class StartgameComponent implements OnInit {

  feedback: any = {};
  game: Game;
  filter = new PlayerFilter();

  constructor(
    private router: Router,
    private playerService: PlayerService,
    private gameService: GameService) { }


  ngOnInit(): void {
    this.loadPlayers();
    this.game = new Game();
  }

  get playerList(): Player[] {
    return this.playerService.playerList;
  }

  loadPlayers(): void {
    this.playerService.load(this.filter);
  }

  save() {
    this.gameService.save(this.game).subscribe(
      game => {
        this.game = game;
        this.feedback = {type: 'success', message: 'Game started'};
        setTimeout(() => {
          this.router.navigate(['/game/play']);
        }, 500);
      },
      err => {
        this.feedback = {type: 'warning', message: 'Error creating new game - select two unique players!'};
      }
    );
  }
}
