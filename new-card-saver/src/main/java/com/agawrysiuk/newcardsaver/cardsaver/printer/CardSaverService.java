package com.agawrysiuk.newcardsaver.cardsaver.printer;

import com.agawrysiuk.newcardsaver.cardsaver.common.ApplicationEnvVariables;
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
import java.util.Map;
import java.util.stream.Collectors;

import static com.agawrysiuk.newcardsaver.cardsaver.common.StringExtractor.parseFileName;
import static com.agawrysiuk.newcardsaver.cardsaver.common.StringExtractor.removeParams;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.counting;

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

    @SneakyThrows
    public void toFolder(SaveToFolderRequest request) {
        Map<String, Long> counts = request.getCardLinks().stream()
                .collect(Collectors.groupingBy(e -> e, counting()));
        String parentFolder = envVariables.getParentSaveFolder();
        Path folderPath = Files.createDirectories(Paths.get(parentFolder).resolve(request.getFolderPath()));

        counts.keySet().forEach(link -> downloadToFolder(link, folderPath));
        log.info("All images saved. Image count: {}", counts.values().stream().reduce(0L, Long::sum));
        String infoTxtContent = counts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> "" + entry.getValue() + "," + parseFileName(entry.getKey()))
                .collect(Collectors.joining(lineSeparator()));
        Files.writeString(folderPath.resolve("info.txt"), infoTxtContent);
        log.info("info.txt saved.");
    }

    @SneakyThrows
    private byte[] downloadToBytes(String cardLink) {
        var url = new URL(cardLink);
        try (InputStream is = url.openStream()) {
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
            throw e;
        }
    }

    @SneakyThrows
    private void downloadToFolder(String link, Path folderPath) {
        try (InputStream in = new URL(removeParams(link)).openStream()) {
            Path fullPath = folderPath.resolve(parseFileName(link));
            Files.copy(in, fullPath);
            log.info("Saved {} to {}", link, fullPath);
        }
    }
}
