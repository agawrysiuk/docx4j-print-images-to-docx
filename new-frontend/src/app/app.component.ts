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
  pastedText: string = "";
  cardLinks: string[] = [];
  imgSrcRegex = new RegExp("src\s*=\s*\"(.+?)\"", "g")

  constructor(private appHttpService: AppHttpService) {}

  downloadMtgPaths() {
    const splitArray = this.pastedText.split(/\r?\n/).filter(element => element);
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

  printImgLinks () {
    this.cardLinks = Array.from(this.pastedText.matchAll(this.imgSrcRegex), x=>x[1]);
    this.print();
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
