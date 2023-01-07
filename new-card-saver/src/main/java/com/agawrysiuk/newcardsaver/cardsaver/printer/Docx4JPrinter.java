package com.agawrysiuk.newcardsaver.cardsaver.printer;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.*;

import java.io.*;
import java.math.BigInteger;
import java.util.List;

@Slf4j
@Data
@Builder
public class Docx4JPrinter {
    //
    private String fileName;
    private boolean landscape;
    private int maxWidth;
    PageSizePaper pageSize;
    private List<byte[]> pictureLinks;


    public void print() throws Exception {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage(pageSize, landscape);

        setPageMargins(wordPackage.getMainDocumentPart().getContents().getBody());

        ObjectFactory factory = new ObjectFactory();
        P paragraph = factory.createP();

        for (byte[] pictureLink : pictureLinks) {
            addImageToWord(wordPackage, pictureLink, paragraph, factory);
        }

        wordPackage.getMainDocumentPart().addObject(paragraph);
        wordPackage.save(new File(fileName));
        log.info("Printing done!");
    }

    private void addImageToWord(WordprocessingMLPackage wordPackage,
                                byte[] bytes, P paragraph,
                                ObjectFactory factory) throws Exception {
        BinaryPartAbstractImage imagePart =
                BinaryPartAbstractImage.createImagePart(wordPackage, bytes);

        int docPrId = 1;
        int cNvPrId = 2;
        Inline inline = imagePart.createImageInline(null,
                null, docPrId, cNvPrId, false, maxWidth);

        addInlineImageToParagraph(inline, paragraph, factory);
    }


    private void addInlineImageToParagraph(Inline inline, P paragraph, ObjectFactory factory) {
        R run = factory.createR();
        paragraph.getContent().add(run);
        Drawing drawing = factory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
    }

    private void setPageMargins(Body body) {
        SectPr.PgMar pgMar = Context.getWmlObjectFactory().createSectPrPgMar();
        pgMar.setBottom(BigInteger.valueOf(720));
        pgMar.setTop(BigInteger.valueOf(720));
        pgMar.setLeft(BigInteger.valueOf(620));
        pgMar.setRight(BigInteger.valueOf(620));
        body.getSectPr().setPgMar(pgMar);
    }
}
