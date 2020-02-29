package com.agawrysiuk.docx4jprintimagestodocx.controller;

import com.agawrysiuk.docx4jprintimagestodocx.data.Card;
import com.agawrysiuk.docx4jprintimagestodocx.printer.Docx4JPrinter;
import com.agawrysiuk.docx4jprintimagestodocx.service.ImageToBytesConverter;
import javafx.scene.layout.BorderPane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.model.structure.PageSizePaper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class Controller {

    private BorderPane view;

    public void printDeck(List<Card> cardList) {
        try {
            List<byte[]> byteList = new ArrayList<>();
            ImageToBytesConverter converter = new ImageToBytesConverter();

            for (Card card : cardList) {
                byteList.add(converter.convertJFXImageToBytes(card.getCardImage()));
                if(card.getCardImageTransform()!=null) {
                    byteList.add(converter.convertJFXImageToBytes(card.getCardImageTransform()));
                }
            }

            Docx4JPrinter printer = Docx4JPrinter.builder()
                    .pictureLinks(byteList)
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
