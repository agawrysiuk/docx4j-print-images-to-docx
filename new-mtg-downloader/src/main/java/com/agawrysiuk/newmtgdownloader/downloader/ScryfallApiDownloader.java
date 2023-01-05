package com.agawrysiuk.newmtgdownloader.downloader;

import com.agawrysiuk.newmtgdownloader.data.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ScryfallApiDownloader implements ApiDownloader {

    private final RestTemplate restTemplate;

    @Override
    public Card get(String cardTitle) {
        String searchLink = prepareCardSearchLink(cardTitle);
        return restTemplate.getForObject(searchLink, Card.class);
    }

    private String prepareCardSearchLink(final String cardTitle) {
        return "https://api.scryfall.com/cards/named?exact=" +
                cardTitle.replace(" ", "+");
    }
}
