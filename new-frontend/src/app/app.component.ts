import { Component } from '@angular/core';
import { AppHttpService } from './common/appHttpService';
import { Card } from './card';
import { StringService } from './common/string.service';

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

  constructor(private appHttpService: AppHttpService,
    private stringService: StringService) {}

  downloadMtgPaths() {
    const splitArray = this.pastedText.split(/\r?\n/).filter(element => element);
    this.appHttpService.getCards(splitArray).then((res: Card[]) => {
      this.cards = res
      const regularLinks = res.filter(card => card.image_uris).map(card => card.image_uris!!.large)
      const faceLinks = res.filter(card => card.card_faces).flatMap(card => card.card_faces).map(face => face!!.image_uris.large)
      this.cardLinks = regularLinks.concat(faceLinks)
    })
  }

  printToDocx() {
    this.appHttpService.printCardsToDocx(this.cardLinks).then(res => {
      console.log(res);
    })
  }

  printImgLinks() {
    this.cardLinks = this.stringService.getPicsFromText(this.pastedText);
    this.printToDocx();
  }

  saveImgsToFolder(folderPath: string) {
    this.cardLinks = this.stringService.getPicsFromText(this.pastedText);
    this.appHttpService.printCardsToFolder(this.cardLinks, folderPath).then(res => {
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
