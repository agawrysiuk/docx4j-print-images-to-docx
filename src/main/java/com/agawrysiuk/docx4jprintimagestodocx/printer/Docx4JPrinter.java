package com.agawrysiuk.docx4jprintimagestodocx.printer;

import com.agawrysiuk.docx4jprintimagestodocx.printer.utils.ImageConverter;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

import static org.docx4j.wml.STPageOrientation.LANDSCAPE;
import static org.docx4j.wml.STPageOrientation.PORTRAIT;

@Slf4j
@Data
@Builder
public class Docx4JPrinter {
    private String pictureLink;
    private String fileName;
    private boolean landscape;
    private boolean internetLink;
    private int maxWidth;

    public void print() throws Exception {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage(PageSizePaper.A4,landscape);

        ImageConverter converter = new ImageConverter();
        byte[] bytes;
        if (internetLink) {
            BufferedImage bufferedImage = ImageIO.read(new URL(pictureLink));
            bytes = converter.convertBufferedImageToBytes(bufferedImage);
        } else {
            //won't work in a jar file, you need to use a iostream
            ClassPathResource resource = new ClassPathResource(pictureLink);
            bytes = converter.convertFileToBytes(resource.getFile());
        }

        if (bytes == null) {
            log.warn("Picture too big. Abandoning.");
            return;
        }

        addImageToWord(wordPackage, bytes);

        wordPackage.save(new File(fileName));
    }

    private void addImageToWord(WordprocessingMLPackage wordMLPackage,
                                       byte[] bytes) throws Exception {
        BinaryPartAbstractImage imagePart =
                BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);

        int docPrId = 0;
        int cNvPrId = 1;
        Inline inline = imagePart.createImageInline(null,
                null, docPrId, cNvPrId, false,maxWidth);

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


}
