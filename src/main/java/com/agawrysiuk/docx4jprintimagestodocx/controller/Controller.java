package com.agawrysiuk.docx4jprintimagestodocx.controller;

import com.agawrysiuk.docx4jprintimagestodocx.downloader.Downloader;
import com.agawrysiuk.docx4jprintimagestodocx.printer.Docx4JPrinter;
import com.agawrysiuk.docx4jprintimagestodocx.service.DeckLoader;
import javafx.scene.layout.GridPane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.model.structure.PageSizePaper;

import java.util.List;

@Slf4j
@Data
public class Controller {

    private GridPane view;

    public void printDeck() {
        try {
            DeckLoader deckLoader = new DeckLoader(new Downloader());
            List<String> arrayLink = deckLoader.loadDeck();
            Docx4JPrinter printer = Docx4JPrinter.builder()
                    .pictureLinks(arrayLink)
                    .fileName("test-print.docx")
                    .landscape(true)
                    .maxWidth(3600) //3580
                    .pageSize(PageSizePaper.LETTER)
                    .build();
            printer.print();
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Can't print the picture!");
        }
    }
}
