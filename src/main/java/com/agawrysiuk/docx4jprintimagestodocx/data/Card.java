package com.agawrysiuk.docx4jprintimagestodocx.data;

import javafx.scene.image.Image;
import lombok.Data;


@Data
public class Card {
    String name;
    String cardImageLargeLink;
    String cardImageTransformLargeLink;
    Image cardImage;
    Image cardImageTransform;
}
