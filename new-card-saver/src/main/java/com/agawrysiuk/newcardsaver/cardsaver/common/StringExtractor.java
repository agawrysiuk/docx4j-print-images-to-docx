package com.agawrysiuk.newcardsaver.cardsaver.common;

import lombok.SneakyThrows;

import java.io.File;
import java.net.URI;
import java.net.URL;

public class StringExtractor {

    @SneakyThrows
    public static String parseFileName(String link) {
        URL url = new URI(removeParams(link)).toURL();
        return new File(url.getFile()).getName();
    }

    public static String removeParams(String link) {
        return link.split("[?]")[0];
    }
}
