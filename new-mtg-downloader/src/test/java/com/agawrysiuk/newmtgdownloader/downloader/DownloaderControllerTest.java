package com.agawrysiuk.newmtgdownloader.downloader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DownloaderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_200_when_requesting_get_cards() throws Exception {
        // Given
        var map = new LinkedMultiValueMap<String, String>();
        List<String> requestedCards = List.of("Cover of Winter", "Dark Depths");
        map.put("cardNames", requestedCards);

        // When && Then
        mockMvc.perform(get("/cards")
                        .params(map))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }
}