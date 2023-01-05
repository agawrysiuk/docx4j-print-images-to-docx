package com.agawrysiuk.newmtgdownloader.downloader;

import com.agawrysiuk.newmtgdownloader.data.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DownloaderController {

    private final DownloaderService service;

    @GetMapping("/cards")
    public List<Card> getCards(@RequestBody List<String> cardNames) {
        return service.getCards(cardNames);
    }
}
