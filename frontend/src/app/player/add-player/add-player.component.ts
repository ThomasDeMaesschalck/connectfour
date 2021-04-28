import { Component, OnInit } from '@angular/core';
import {Player} from "../../models/player";
import {Router} from "@angular/router";
import {PlayerService} from "../player.service";

@Component({
  selector: 'app-add-player',
  templateUrl: './add-player.component.html'
})
export class AddPlayerComponent implements OnInit {

  player: Player;
  feedback: any = {};

  constructor(
    private router: Router,
    private playerService: PlayerService
  ) { }

  ngOnInit(): void {
   this.player = new Player();
  }

  save() {
    this.playerService.save(this.player).subscribe(
      player => {
        this.player = player;
        this.feedback = {type: 'success', message: 'Added a player!'};
        setTimeout(() => {
          this.router.navigate(['/players/highscores']);
        }, 1000);
      },
      err => {
        this.feedback = {type: 'warning', message: 'Error saving - check if name is unique'};
      }
    );
  }

}
