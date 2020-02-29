package com.agawrysiuk.docx4jprintimagestodocx.controller;

import com.agawrysiuk.docx4jprintimagestodocx.downloader.Downloader;
import com.agawrysiuk.docx4jprintimagestodocx.printer.Docx4JPrinter;
import com.agawrysiuk.docx4jprintimagestodocx.service.DeckLoader;
import com.agawrysiuk.docx4jprintimagestodocx.service.ImageToBytesConverter;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.model.structure.PageSizePaper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class Controller {

    private BorderPane view;

//    public void printDeck(TextArea area) {
//        try {
//            DeckLoader deckLoader = new DeckLoader(new Downloader());
//            List<String> arrayLink = deckLoader.loadDeck(area.getText());
//            List<byte[]> byteList = new ArrayList<>();
//
//            for (String link : arrayLink) {
//                ImageToBytesConverter converter = new ImageToBytesConverter();
//                BufferedImage bufferedImage = ImageIO.read(new URL(link));
//                byteList.add(converter.convertBufferedImageToBytes(bufferedImage));
//            }
//
//            Docx4JPrinter printer = Docx4JPrinter.builder()
//                    .pictureLinks(byteList)
//                    .fileName("test-print.docx")
//                    .landscape(true)
//                    .maxWidth(3600) //3580
//                    .pageSize(PageSizePaper.LETTER)
//                    .build();
//            printer.print();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.warn("Can't print the picture!");
//        }
//    }
}
