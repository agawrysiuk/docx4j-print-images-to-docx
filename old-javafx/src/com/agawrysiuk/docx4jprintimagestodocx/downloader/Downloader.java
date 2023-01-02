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

    public Card downloadCard(String cardTitle) {

        String searchLink = prepareCardSearchLink(cardTitle);
        JSONObject downloadedCard = getJsonFromScryfall(searchLink);
        if (downloadedCard != null) {
            return createCardObject(downloadedCard);
        }
        return null;
    }

    private String prepareCardSearchLink(String cardTitle) {
        StringBuilder link = new StringBuilder();
        link.append("https://api.scryfall.com/cards/named?exact=");
        cardTitle = cardTitle.replace(" ", "+");
        link.append(cardTitle);
        return link.toString();
    }

    private JSONObject getJsonFromScryfall(String scryfallLink) {
        Document scryfallDocument;
        try {
            scryfallDocument = Jsoup.connect(scryfallLink).ignoreContentType(true).get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return new JSONObject(scryfallDocument.body().text());
    }


    private Card createCardObject(JSONObject downloadedCard){
        Card card = new Card();
        if (isCardTwoFaced(downloadedCard)) {
            setTwoCardImages(card,downloadedCard);
        } else {
            setOneCardImage(card,downloadedCard);
        }
        card.setName(downloadedCard.getString("name"));
        return card;
    }

    private boolean isCardTwoFaced(JSONObject downloadedCard) {
        return !downloadedCard.has("image_uris") && downloadedCard.has("card_faces");
    }

    private void setTwoCardImages(Card card, JSONObject downloadedCard) {
        card.setCardImageLargeLink(downloadedCard.getJSONArray("card_faces").getJSONObject(0).getJSONObject("image_uris").getString("large"));
        card.setCardImage(new Image(downloadedCard.getJSONArray("card_faces").getJSONObject(0).getJSONObject("image_uris").getString("large")));
        card.setCardImageTransformLargeLink(downloadedCard.getJSONArray("card_faces").getJSONObject(1).getJSONObject("image_uris").getString("large"));
        card.setCardImageTransform(new Image(downloadedCard.getJSONArray("card_faces").getJSONObject(1).getJSONObject("image_uris").getString("large")));
    }

    private void setOneCardImage(Card card, JSONObject downloadedCard) {
        card.setCardImageLargeLink(downloadedCard.getJSONObject("image_uris").getString("large"));
        card.setCardImage(new Image(downloadedCard.getJSONObject("image_uris").getString("large")));
        card.setCardImageTransformLargeLink(null);
    }
}
