package com.agawrysiuk.newcardsaver.cardsaver.printer;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.docx4j.model.structure.PageSizePaper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Service
public class CardSaverService {

    @SneakyThrows
    public void print(List<String> cardLinks) {
        List<byte[]> byteList = cardLinks.stream().map(this::downloadToBytes).toList();

        Docx4JPrinter printer = Docx4JPrinter.builder()
                .pictureLinks(byteList)
                .fileName("test-print.docx")
                .landscape(true)
                .maxWidth(3600) //3580
                .pageSize(PageSizePaper.LETTER)
                .build();

        printer.print();
    }

    @SneakyThrows
    public byte[] downloadToBytes(String cardLink) {
        var url = new URL(cardLink);
        try (InputStream is = url.openStream()) {
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
            throw e;
        }
    }
}
