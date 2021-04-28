import { Routes } from '@angular/router';
import {HighscoreListComponent} from "./highscore-list/highscore-list.component";
import {AddPlayerComponent} from "./add-player/add-player.component";

export const PLAYER_ROUTES: Routes = [
  {
    path: 'players/highscores',
    component: HighscoreListComponent
  },
  {
    path: 'players/new',
    component: AddPlayerComponent
  }
];
