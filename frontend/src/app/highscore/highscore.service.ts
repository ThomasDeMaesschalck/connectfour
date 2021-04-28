import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {Player} from "../models/player";
import {HighscoreFilter} from "./highscore-filter";
import {map} from "rxjs/operators";

const headers = new HttpHeaders().set('Accept', 'application/json');

@Injectable({
  providedIn: 'root'
})
export class HighscoreService {

  size$ = new BehaviorSubject<number>(0);
  playerList: Player[] = [];
  api = 'http://localhost:5551/api/players/';

  constructor(private http: HttpClient) { }


  load(filter: HighscoreFilter): void {
    this.find(filter).subscribe(result => {
        this.playerList = result;
      },
      err => {
        console.error('error loading', err);
      }
    );
  }

  find(filter: HighscoreFilter): Observable<Player[]> {

    const params: any = {
      id: filter.id,
      pageSize: filter.size,
      pageNo: filter.page
    };

    return this.http.get<Player[]>(this.api, {headers, params}).pipe(
      map((response: any) => {
          this.size$.next(response.totalElements);
          return response.content;
        }
      ));
  }

}
