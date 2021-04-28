import { Injectable } from '@angular/core';
import {Player} from "../models/player";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Game} from "../models/game";
import {PlayerFilter} from "../player/player-filter";
import {map} from "rxjs/operators";

const headers = new HttpHeaders().set('Accept', 'application/json');

@Injectable({
  providedIn: 'root'
})

export class GameService {

  game: Game;
  api = 'http://localhost:5552/api/games/';

  constructor(private http: HttpClient) { }

  save(gameToStart: Game): Observable<Game> {
    let params = new HttpParams();
    let url = '';
    url = `${this.api}`;
    return this.http.post<Game>(url, gameToStart, {headers, params});
  }


  load(): void {
    this.find().subscribe(result => {
        this.game = result;
      },
      err => {
        console.error('Error loading game', err);
      }
    );
  }

  find(): Observable<Game> {
    const params: any = {};
    return this.http.get<Game>(this.api, {headers, params})
  }

}
