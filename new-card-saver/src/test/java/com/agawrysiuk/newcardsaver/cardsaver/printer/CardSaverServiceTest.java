package com.agawrysiuk.newcardsaver.cardsaver.printer;

import com.agawrysiuk.newcardsaver.cardsaver.common.ApplicationEnvVariables;
import com.agawrysiuk.newcardsaver.cardsaver.dto.SaveToFolderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class CardSaverServiceTest {

    @MockBean
    private ApplicationEnvVariables envVariables;
    @Autowired
    private CardSaverService service;

    @Test
    public void toFolder_should_save_image() throws IOException, InterruptedException {
        // given
        String parentPath = ".";
        when(envVariables.getParentSaveFolder()).thenReturn(parentPath);
        String imageLink = "https://hallofarkham.files.wordpress.com/2020/08/d1.jpg?strip=info&w=826";
        String folderPath = "something";

        // when
        service.toFolder(new SaveToFolderRequest(List.of(imageLink), folderPath));

        // then
        sleep(1000);
        Path folder = Paths.get(parentPath).resolve(folderPath);
        assertThat(folder.resolve("d1.jpg").toFile().exists()).isTrue();

        // clean up
        Files.delete(folder.resolve("d1.jpg"));
        Files.delete(folder);
    }

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