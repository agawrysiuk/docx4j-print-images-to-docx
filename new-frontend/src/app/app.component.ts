import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'new-frontend';
  cards: Card[] = []

  downloadPaths() {
    this.cards = [
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
      {name: "asd", path: "https://cards.scryfall.io/large/front/9/1/91d9bb89-d8f8-4dff-8b94-3f7b8aa8f299.jpg?1593274717"},
    ]
  }

  clean() {
    this.cards = []
  }
}

type Card = {
  name: string;
  path: string;
}
