import { Routes } from '@angular/router';
import {HighscoreListComponent} from "./highscore-list/highscore-list.component";

export const HIGHSCORE_ROUTES: Routes = [
  {
    path: 'highscores',
    component: HighscoreListComponent
  }
];
