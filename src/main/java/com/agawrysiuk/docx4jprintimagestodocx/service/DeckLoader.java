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

    public DeckLoader(Downloader downloader) {
        this.downloader = downloader;
    }

    public List<Card> loadDeck(String deckSource) throws IOException {

        BufferedReader br = new BufferedReader(new StringReader(deckSource));

        List<Card> cardList = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            if(line.equals(" ")) {
                continue;
            }
            String cardName = line.split(" ",2)[1];
            log.info("Downloading card = {}", cardName);
            Card card = downloader.downloadCard(cardName);
            cardList.add(card);
        }

        return cardList;
    }
}
