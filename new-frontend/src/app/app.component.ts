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
  cardLinks: string[] = [];

  constructor(private appHttpService: AppHttpService) {}

  downloadPaths() {
    const splitArray = this.cardNames.split(/\r?\n/).filter(element => element);
    this.appHttpService.getCards(splitArray).then((res: Card[]) => {
      this.cards = res
      const regularLinks = res.filter(card => card.image_uris).map(card => card.image_uris!!.large)
      const faceLinks = res.filter(card => card.card_faces).flatMap(card => card.card_faces).map(face => face!!.image_uris.large)
      this.cardLinks = regularLinks.concat(faceLinks)
    })
  }

  print() {
    this.appHttpService.printCards(this.cardLinks).then(res => {
      console.log(res);
    })
  }
}

/* 
TEST LIST:

Meren of Clan Nel Toth
Viscera Seer
Shambling Ghast
Ragavan Nimble Pilferer
Prosperous Innkeeper
Mahadi Emporium Master
Jolene the Plunder Queen
Tendershoot Dryad
Scute Swarm
Dockside Extortionist
Tymaret the Murder King
Fable of the Mirror-Breaker
*/
