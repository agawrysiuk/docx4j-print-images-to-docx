package com.agawrysiuk.newcardsaver.cardsaver.printer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardSaverController {

    private final CardSaverService service;

    @PostMapping("/print")
    public void print(@RequestBody List<String> cardLinks) {
        service.print(cardLinks);
    }
}