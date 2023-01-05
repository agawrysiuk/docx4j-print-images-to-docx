package com.agawrysiuk.newmtgdownloader.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class CardImages {
        private String large;
    }
}
