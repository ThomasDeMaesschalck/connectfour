import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {Player} from "../models/player";
import {PlayerFilter} from "./player-filter";
import {map} from "rxjs/operators";

const headers = new HttpHeaders().set('Accept', 'application/json');

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  size$ = new BehaviorSubject<number>(0);
  playerList: Player[] = [];
  api = 'http://localhost:5551/api/players/';

  constructor(private http: HttpClient) { }


  load(filter: PlayerFilter): void {
    this.find(filter).subscribe(result => {
        this.playerList = result;
      },
      err => {
        console.error('error loading', err);
      }
    );
  }

  find(filter: PlayerFilter): Observable<Player[]> {

    const params: any = {
      id: filter.id,
      pageSize: filter.size,
      pageNo: filter.page,
      sortBy: filter.sortBy
    };

    return this.http.get<Player[]>(this.api, {headers, params}).pipe(
      map((response: any) => {
          this.size$.next(response.totalElements);
          return response.content;
        }
      ));
  }

  save(playerToSave: Player): Observable<Player> {
    let params = new HttpParams();
    let url = '';
    url = `${this.api}`;
    return this.http.post<Player>(url, playerToSave, {headers, params});
    }

}
