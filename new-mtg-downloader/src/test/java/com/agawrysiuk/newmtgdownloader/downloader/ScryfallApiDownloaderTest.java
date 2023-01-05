package com.agawrysiuk.newmtgdownloader.downloader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScryfallApiDownloaderTest {

    @Autowired
    private ScryfallApiDownloader scryfallApiDownloader;

    @Test
    public void should_download_card() {
        // Given
        var cardName = "Cover of Winter";

        // When
        var result = scryfallApiDownloader.get(cardName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getUri()).isNotNull();
        assertThat(result.getImages()).isNotNull();
        assertThat(result.getImages().getLarge()).isNotNull();
    }

}