package com.agawrysiuk.newmtgdownloader.downloader;

import com.agawrysiuk.newmtgdownloader.data.Card;

public interface ApiDownloader {

    Card get(String name);
}
