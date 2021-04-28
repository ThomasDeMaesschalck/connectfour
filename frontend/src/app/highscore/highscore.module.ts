import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HighscoreListComponent } from './highscore-list/highscore-list.component';
import {FormsModule} from "@angular/forms";
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {HighscoreService} from "./highscore.service";
import {RouterModule} from "@angular/router";
import {HIGHSCORE_ROUTES} from "./highscore.routes";



@NgModule({
  declarations: [HighscoreListComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(HIGHSCORE_ROUTES),
    NgbPaginationModule
  ],
  providers: [HighscoreService],
  exports: []
})
export class HighscoreModule { }
