package com.agawrysiuk.newcardsaver.cardsaver.printer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.model.structure.PageSizePaper;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocalCardSaverService {

    @SneakyThrows
    public void fromFolderToDocxByFolderContent(String folderPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            List<byte[]> byteList = paths
                    .filter(Files::isRegularFile)
                    .map(this::toBytes)
                    .toList();

            Docx4JPrinter printer = Docx4JPrinter.builder()
                    .pictureLinks(byteList)
                    .fileName("test-print.docx")
                    .landscape(true)
                    .maxWidth(3600) //3580
                    .pageSize(PageSizePaper.LETTER)
                    .build();

            printer.print();
        }
    }

    @SneakyThrows
    public void fromFolderToDocxByTxtContent(String infoTxtPath, String folderPath) {
        List<byte[]> byteList = Files.readAllLines(Path.of(infoTxtPath)).stream()
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    var lineInfo = line.split(",");
                    return new ContentTxtLines(Integer.parseInt(lineInfo[0]), lineInfo[1]);
                })
                .flatMap(contentTxtLines ->
                        IntStream.range(0, contentTxtLines.numOfCards).mapToObj(n -> toBytes(Path.of(folderPath).resolve(contentTxtLines.cardFilename))))
                .toList();

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
    private byte[] toBytes(Path path) {
        return Files.readAllBytes(path);
    }

    @AllArgsConstructor
    @Getter
    private static class ContentTxtLines {
        private int numOfCards;
        private String cardFilename;
    }
}
