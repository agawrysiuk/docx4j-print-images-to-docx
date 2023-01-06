import { Component } from '@angular/core';
import { AppHttpService } from './appHttpService';
import { Card } from './card';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'new-frontend';
  cards: Card[] = [];
  cardNames: string = "";

  constructor(private appHttpService: AppHttpService) {}

  downloadPaths() {
    const splitArray = this.cardNames.split(/\r?\n/).filter(element => element);
    this.appHttpService.getCards(splitArray).then((res: Card[]) => {
      this.cards = res
    })
  }

  clean() {
    this.cards = []
  }
}
