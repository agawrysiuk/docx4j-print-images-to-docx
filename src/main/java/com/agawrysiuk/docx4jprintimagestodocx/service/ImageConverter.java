package com.agawrysiuk.docx4jprintimagestodocx.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Slf4j
@Data
public class ImageConverter {

    public byte[] convertFileToBytes(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();

        //length must be integer, should be ok for most cases
        if (length > Integer.MAX_VALUE) {
            log.warn("File is too big!");
            return null;
        }

        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        is.close();

        return bytes;
    }

    public byte[] convertBufferedImageToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( image, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }
}
