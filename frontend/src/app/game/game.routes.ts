import {Routes} from "@angular/router";
import {StartgameComponent} from "./startgame/startgame.component";
import {GameplayComponent} from "./gameplay/gameplay.component";

export const GAME_ROUTES: Routes = [
  {
    path: 'game',
    component: StartgameComponent
  },
  {
    path: 'game/play',
    component: GameplayComponent
  }
];
