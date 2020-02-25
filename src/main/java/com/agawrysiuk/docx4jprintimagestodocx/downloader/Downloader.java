package com.agawrysiuk.docx4jprintimagestodocx.downloader;

import com.agawrysiuk.docx4jprintimagestodocx.data.Card;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Downloader {

    public Card[] downloadBulk(String[] cardTitles) {
        Card[] cardList = new Card[cardTitles.length];
        for (int i = 0; i < cardTitles.length; i++) {
            cardList[i] = downloadCard(cardTitles[i]);
        }
        return cardList;
    }

    public Card downloadCard(String cardTitle){

        cardTitle = cardTitle.replace(" ","+"); //change to string
        Document scryfallDocument;
        try {
            scryfallDocument = Jsoup.connect("https://api.scryfall.com/cards/named?fuzzy="+cardTitle).ignoreContentType(true).get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        //...

        return null;
    }
}
