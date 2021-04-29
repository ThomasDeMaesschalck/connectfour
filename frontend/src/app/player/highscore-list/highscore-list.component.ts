import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {PlayerFilter} from "../player-filter";
import {Player} from "../../models/player";
import {PlayerService} from "../player.service";

@Component({
  selector: 'app-highscore-list',
  templateUrl: './highscore-list.component.html'
})
export class HighscoreListComponent implements OnInit {

  total$: Observable<number>;
  filter = new PlayerFilter();
  selectedPlayer: Player;
  feedback: any = {};

  constructor(private playerService: PlayerService) { }

  ngOnInit(): void {
    this.filter.id = '';
    this.filter.page = 0;
    this.filter.size = 5;
    this.search();
  }

  get playerList(): Player[] {
    return this.playerService.playerList;
  }

  search(): void {
    this.playerService.load(this.filter);
    this.total$ = this.playerService.size$;
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
