package com.agawrysiuk.newcardsaver.cardsaver.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringExtractorTest {

    @Test
    public void parseFilename_should_return_only_jpg_filename() {
        // given
        String link = "https://www.something.com/path-to-jpg/my-image.jpg?width=800";

        // when
        String result = StringExtractor.parseFileName(link);

        // then
        assertThat(result).isEqualTo("my-image.jpg");
    }

    @Test
    public void cleanParams_should_return_only_part_before_parameters() {
        // given
        String link = "https://www.something.com/path-to-jpg/my-image.jpg?width=800";

        // when
        String result = StringExtractor.removeParams(link);

        // then
        assertThat(result).isEqualTo("https://www.something.com/path-to-jpg/my-image.jpg");
    }

}