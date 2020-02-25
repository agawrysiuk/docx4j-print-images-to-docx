package com.agawrysiuk.docx4jprintimagestodocx.service;

import com.agawrysiuk.docx4jprintimagestodocx.data.Card;
import com.agawrysiuk.docx4jprintimagestodocx.downloader.Downloader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeckLoader {

    private Downloader downloader;

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
            Card card = downloader.downloadCard(line.split(" ",2)[1]);
            stringList.add(card.getCardImageLarge());
        }

        return stringList;
    }
}
