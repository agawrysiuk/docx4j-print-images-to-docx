package com.agawrysiuk.newcardsaver.cardsaver.common;

import org.springframework.stereotype.Component;

@Component
public class ApplicationEnvVariables {

    public String getParentSaveFolder() {
        return System.getenv("CARD_SAVER_PARENT_FOLDER");
    }
}
