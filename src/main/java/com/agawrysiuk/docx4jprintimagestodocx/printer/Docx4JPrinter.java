package com.agawrysiuk.docx4jprintimagestodocx.printer;

import com.agawrysiuk.docx4jprintimagestodocx.service.ImageConverter;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
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
    private List<String> pictureLinks;


    public void print() throws Exception {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage(pageSize, landscape);

        setPageMargins(wordPackage.getMainDocumentPart().getContents().getBody());

        ObjectFactory factory = new ObjectFactory();
        P paragraph = factory.createP();

        for (int i = 0; i < pictureLinks.size(); i++) {
            byte[] bytes = createByteArray(pictureLinks.get(i));
            if (bytes == null) {
                log.warn("Picture too big. Abandoning.");
                return;
            }
            addImageToWord(wordPackage, bytes, paragraph, factory);
        }
        wordPackage.getMainDocumentPart().addObject(paragraph);
        wordPackage.save(new File(fileName));
    }

    private byte[] createByteArray(String pictureLink) throws IOException {
        ImageConverter converter = new ImageConverter();
        BufferedImage bufferedImage = ImageIO.read(new URL(pictureLink));
        return converter.convertBufferedImageToBytes(bufferedImage);
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
