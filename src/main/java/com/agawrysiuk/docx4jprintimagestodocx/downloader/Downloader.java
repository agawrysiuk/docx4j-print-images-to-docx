package com.agawrysiuk.docx4jprintimagestodocx.downloader;

import com.agawrysiuk.docx4jprintimagestodocx.data.Card;
import javafx.scene.image.Image;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

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

        JSONObject downloadedCard = new JSONObject(scryfallDocument.body().text());

        // TODO: 2020-02-25 add some serializer
        Card card = new Card();
        if(downloadedCard.has("card_faces") && !downloadedCard.has("image_uris")) {
            card.setCardImageLargeLink(downloadedCard.getJSONArray("card_faces").getJSONObject(0).getJSONObject("image_uris").getString("large"));
            card.setCardImage(new Image(downloadedCard.getJSONArray("card_faces").getJSONObject(0).getJSONObject("image_uris").getString("large")));
            card.setCardImageTransformLargeLink(downloadedCard.getJSONArray("card_faces").getJSONObject(1).getJSONObject("image_uris").getString("large"));
            card.setCardImageTransform(new Image(downloadedCard.getJSONArray("card_faces").getJSONObject(1).getJSONObject("image_uris").getString("large")));
        } else {
            card.setCardImageLargeLink(downloadedCard.getJSONObject("image_uris").getString("large"));
            card.setCardImage(new Image(downloadedCard.getJSONObject("image_uris").getString("large")));
            card.setCardImageTransformLargeLink(null);
        }

        card.setName(downloadedCard.getString("name"));

        return card;
    }
}
