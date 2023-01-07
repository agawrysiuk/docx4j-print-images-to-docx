package com.agawrysiuk.newmtgdownloader.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private UUID id;
    private String uri;
    private String name;
    @JsonProperty("image_uris")
    private CardImages images;
    @JsonProperty("card_faces")
    private List<CardFaces> faces;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CardImages {
        private String large;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CardFaces {
        @JsonProperty("image_uris")
        private CardImages images;
    }
}
