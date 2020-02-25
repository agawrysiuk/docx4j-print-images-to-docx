package com.agawrysiuk.docx4jprintimagestodocx.service;

import com.agawrysiuk.docx4jprintimagestodocx.data.Card;
import org.springframework.stereotype.Service;

@Service
public class CardConverter {

    public String[] convertCardListToStringList(Card[] cardList) {
        String[] stringList = new String[cardList.length];
        for (int i = 0; i < cardList.length; i++) {
            stringList[i] = cardList[i].getCardImageLarge();
        }
        return stringList;
    }
}
