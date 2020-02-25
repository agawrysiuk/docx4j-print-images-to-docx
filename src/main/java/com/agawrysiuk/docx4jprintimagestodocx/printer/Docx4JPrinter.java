package com.agawrysiuk.docx4jprintimagestodocx.printer;

import com.agawrysiuk.docx4jprintimagestodocx.printer.utils.ImageConverter;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.*;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;

@Slf4j
@Data
@Builder
public class Docx4JPrinter {
    //
    private String fileName;
    private boolean landscape;
    private int maxWidth;
    PageSizePaper pageSize;

    private boolean internetLink;
    private String[] pictureLinks;

    public void print() throws Exception {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage(pageSize, landscape);

        setPageMargins(wordPackage.getMainDocumentPart().getContents().getBody());

        for (int i = 0; i < pictureLinks.length; i++) {
            byte[] bytes = createByteArray(pictureLinks[i]);
            if (bytes == null) {
                log.warn("Picture too big. Abandoning.");
                return;
            }
            addImageToWord(wordPackage, bytes);
        }
        wordPackage.save(new File(fileName));
    }

    private byte[] createByteArray(String pictureLink) throws IOException {
        ImageConverter converter = new ImageConverter();
        if (internetLink) {
            BufferedImage bufferedImage = ImageIO.read(new URL(pictureLink));
            return converter.convertBufferedImageToBytes(bufferedImage);
        } else {
            //won't work in a jar file, you need to use a iostream
            ClassPathResource resource = new ClassPathResource(pictureLink);
            return converter.convertFileToBytes(resource.getFile());
        }
    }

    private void addImageToWord(WordprocessingMLPackage wordMLPackage,
                                byte[] bytes) throws Exception {
        BinaryPartAbstractImage imagePart =
                BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);

        int docPrId = 0;
        int cNvPrId = 1;
        Inline inline = imagePart.createImageInline(null,
                null, docPrId, cNvPrId, false, maxWidth);

        P paragraph = addInlineImageToParagraph(inline);

        wordMLPackage.getMainDocumentPart().addObject(paragraph);
    }


    private P addInlineImageToParagraph(Inline inline) {
        ObjectFactory factory = new ObjectFactory();
        P paragraph = factory.createP();
        R run = factory.createR();
        paragraph.getContent().add(run);
        Drawing drawing = factory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return paragraph;
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
