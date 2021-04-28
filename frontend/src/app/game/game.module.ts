import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StartgameComponent } from './startgame/startgame.component';
import { GameplayComponent } from './gameplay/gameplay.component';
import {FormsModule} from "@angular/forms";
import {GAME_ROUTES} from "./game.routes";
import {RouterModule} from "@angular/router";



@NgModule({
  declarations: [StartgameComponent, GameplayComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(GAME_ROUTES)
  ]
})
export class GameModule { }
