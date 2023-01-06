import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Card } from './card';

@Injectable()
export class AppHttpService {

  cardDownloaderUrl = "http://localhost:8081/cards"

  constructor(private http: HttpClient) { }

  getCards(names: string[]): Promise<Card[]> {
    let params = new HttpParams();
    params = params.append('cardNames', names.join(','));
    return this.http.get<Card[]>(this.cardDownloaderUrl, { params: params }).toPromise() as Promise<Card[]>;
  }
}
