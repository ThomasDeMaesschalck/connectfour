import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HighscoreListComponent } from './highscore-list/highscore-list.component';
import {FormsModule} from "@angular/forms";
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {RouterModule} from "@angular/router";
import {PlayerService} from "./player.service";
import {PLAYER_ROUTES} from "./player.routes";
import { AddPlayerComponent } from './add-player/add-player.component';



@NgModule({
  declarations: [HighscoreListComponent, AddPlayerComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(PLAYER_ROUTES),
    NgbPaginationModule
  ],
  providers: [PlayerService],
  exports: []
})
export class PlayerModule { }
