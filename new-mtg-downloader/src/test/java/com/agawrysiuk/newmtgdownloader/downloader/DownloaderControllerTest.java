package com.agawrysiuk.newmtgdownloader.downloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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
        List<String> requestedCards = List.of("Cover of Winter");

        // When && Then
        mockMvc.perform(get("/cards")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(requestedCards)))
                .andExpect(status().isOk());
    }
}