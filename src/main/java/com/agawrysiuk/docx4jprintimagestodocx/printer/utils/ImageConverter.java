package com.agawrysiuk.docx4jprintimagestodocx.printer.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
}
