package com.agawrysiuk.newcardsaver.cardsaver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveToFolderRequest {
    private List<String> cardLinks;
    private String folderPath;
}
