import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Card } from '../card';

@Injectable()
export class AppHttpService {

  cardDownloaderUrl = "http://localhost:8081/cards"
  cardPrinterUrl = "http://localhost:8080"

  constructor(private http: HttpClient) { }

  getCards(names: string[]): Promise<Card[]> {
    let params = new HttpParams();
    params = params.append('cardNames', names.join(','));
    return this.http.get<Card[]>(this.cardDownloaderUrl, { params: params }).toPromise() as Promise<Card[]>;
  }

  printCardsToDocx(links: string[]): Promise<never> {
    return this.http.post<never>(this.cardPrinterUrl + "/to-docx", links).toPromise() as Promise<never>;
  }

  printCardsToFolder(links: string[], folderPath: string): Promise<never> {
    return this.http.post<never>(this.cardPrinterUrl + "/to-folder", new SaveToFolderRequest(links, folderPath)).toPromise() as Promise<never>;
  }
}

class SaveToFolderRequest {
  cardLinks: string[];
  folderPath: string;

  constructor(cardLinks: string[], folderPath: string) {
    this.cardLinks = cardLinks;
    this.folderPath = folderPath;
  }
}
