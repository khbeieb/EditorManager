package org.mobelite.editormanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MagazineDTO {
    private int issueNumber;
    private String title;
    private List<AuthorBasicDTO> authors;
}
