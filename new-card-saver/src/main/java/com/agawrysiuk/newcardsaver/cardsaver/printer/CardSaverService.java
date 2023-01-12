package com.agawrysiuk.newcardsaver.cardsaver.printer;

import com.agawrysiuk.newcardsaver.cardsaver.common.ApplicationEnvVariables;
import com.agawrysiuk.newcardsaver.cardsaver.common.StringExtractor;
import com.agawrysiuk.newcardsaver.cardsaver.dto.SaveToFolderRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.docx4j.model.structure.PageSizePaper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardSaverService {

    private final ApplicationEnvVariables envVariables;

    @SneakyThrows
    public void toDocx(List<String> cardLinks) {
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

    public void toFolder(SaveToFolderRequest request) {
        request.getCardLinks().forEach(link -> downloadToFolder(link, request.getFolderPath()));
        log.info("All images saved.");
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

    @SneakyThrows
    public void downloadToFolder(String link, String folderName) {
        try(InputStream in = new URL(StringExtractor.removeParams(link)).openStream()){
            String parentFolder = envVariables.getParentSaveFolder();
            Path folderPath = Files.createDirectories(Paths.get(parentFolder).resolve(folderName));
            Path fullPath = folderPath.resolve(StringExtractor.parseFileName(link));
            Files.copy(in, fullPath);
            log.info("Saved {} to {}", link, fullPath);
        }
    }
}
