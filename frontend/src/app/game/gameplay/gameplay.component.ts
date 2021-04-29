import { Component, OnInit } from '@angular/core';
import {Player} from "../../models/player";
import {Game} from "../../models/game";
import {GameService} from "../game.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-gameplay',
  templateUrl: './gameplay.component.html',
 styleUrls: ['./gameplay.stylesheet.css']
})
export class GameplayComponent implements OnInit {

  feedback: any = {};

  constructor(private gameService: GameService) {
  }

  ngOnInit(): void {
    this.load();
    this.feedback = {};
  }

  get game(): Game {
    return this.gameService.game;
  }
  load(): void {
    this.gameService.load();
  }

  dropToken(columnId: number) {
    this.gameService.dropToken(columnId).subscribe(
      game => {
        setTimeout(() => {
          this.ngOnInit();
        }, 100);
      },
      err => {
        this.feedback = {type: 'warning', message: 'This move is not possible'};
      }
    );
  }
}
