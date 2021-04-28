import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {HighscoreFilter} from "../highscore-filter";
import {Player} from "../../models/player";
import {HighscoreService} from "../highscore.service";

@Component({
  selector: 'app-highscore-list',
  templateUrl: './highscore-list.component.html'
})
export class HighscoreListComponent implements OnInit {

  total$: Observable<number>;
  filter = new HighscoreFilter();
  selectedPlayer: Player;
  feedback: any = {};

  constructor(private highscoreService: HighscoreService) { }

  ngOnInit(): void {
    this.filter.id = '';
    this.filter.page = 0;
    this.filter.size = 10;
    this.search();
  }

  get playerList(): Player[] {
    return this.highscoreService.playerList;
  }

  search(): void {
    this.highscoreService.load(this.filter);
    this.total$ = this.highscoreService.size$;
  }

  select(selected: Player): void {
    this.selectedPlayer = selected;
  }

  onChange(pageSize: number) {
    this.filter.size = pageSize;
    this.filter.page = 0;
    this.search();
  }

  onPageChange(page: number) {
    this.filter.page = page - 1;
    this.search();
    this.filter.page = page;
  }
}
