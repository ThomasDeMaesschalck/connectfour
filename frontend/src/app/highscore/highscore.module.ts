import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HighscoreListComponent } from './highscore-list/highscore-list.component';
import {FormsModule} from "@angular/forms";
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {HighscoreService} from "./highscore.service";



@NgModule({
  declarations: [HighscoreListComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgbPaginationModule
  ],
  providers: [HighscoreService]
})
export class HighscoreModule { }
