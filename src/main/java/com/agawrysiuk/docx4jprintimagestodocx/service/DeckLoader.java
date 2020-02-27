package com.agawrysiuk.docx4jprintimagestodocx.service;

import com.agawrysiuk.docx4jprintimagestodocx.data.Card;
import com.agawrysiuk.docx4jprintimagestodocx.downloader.Downloader;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DeckLoader {

    private final Downloader downloader;

    private String deckSource = "src/main/resources/decks/deck.txt"; //hardcoded for now

    public DeckLoader(Downloader downloader) {
        this.downloader = downloader;
    }

    public List<String> loadDeck() throws IOException {
        File file = new File(deckSource);

        BufferedReader br = new BufferedReader(new FileReader(file));

        List<String> stringList = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            if(line.equals(" ")) {
                continue;
            }
            String cardName = line.split(" ",2)[1];
            log.info("Downloading card = {}", cardName);
            Card card = downloader.downloadCard(cardName);
            stringList.add(card.getCardImageLarge());
            if (card.getCardImageTransformLarge() != null) {
                stringList.add(card.getCardImageTransformLarge());
            }
        }

        return stringList;
    }
}
