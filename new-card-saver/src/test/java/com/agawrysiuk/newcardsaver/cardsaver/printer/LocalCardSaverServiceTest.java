package com.agawrysiuk.newcardsaver.cardsaver.printer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocalCardSaverServiceTest {
    @Autowired
    private LocalCardSaverService service;

    @Test
    public void fromFolderToDocxByFolderContent_should_create_docx_with_images_from_all_folder_images() {
        // given
        String folder = "/home/agawrysiuk/Downloads/Arkham Horror LCG/Investigator Starter Decks/Jacqueline Fine/ArkhamDB/original";

        // when
        service.fromFolderToDocxByFolderContent(folder);
    }

    @Test
    public void fromFolderToDocxByTxtContent_should_create_docx_with_images_with_provided_images_and_number_of_them() {
        // given
        String infoTxtPath = "/home/agawrysiuk//Downloads/Arkham Horror LCG/Standalone Adventures/The Blob That Ate Everything/info.txt";
        String folder = "/home/agawrysiuk//Downloads/Arkham Horror LCG/Standalone Adventures/The Blob That Ate Everything/original";

        // when
        service.fromFolderToDocxByTxtContent(infoTxtPath, folder);
    }

}