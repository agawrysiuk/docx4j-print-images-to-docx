package com.agawrysiuk.newmtgdownloader.downloader;

import com.agawrysiuk.newmtgdownloader.data.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloaderService {

    private final ApiDownloader apiDownloader;

    public List<Card> getCards(List<String> cardNames) {
        return cardNames.stream().map(apiDownloader::get).toList();
    }
}
