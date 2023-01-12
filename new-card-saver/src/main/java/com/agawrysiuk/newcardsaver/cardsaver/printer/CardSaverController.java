package com.agawrysiuk.newcardsaver.cardsaver.printer;

import com.agawrysiuk.newcardsaver.cardsaver.dto.SaveToFolderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class CardSaverController {

    private final CardSaverService service;

    @PostMapping("/to-docx")
    public void toDocx(@RequestBody List<String> cardLinks) {
        service.toDocx(cardLinks);
    }

    @PostMapping("/to-folder")
    public void toFolder(@RequestBody SaveToFolderRequest request) {
        service.toFolder(request);
    }
}