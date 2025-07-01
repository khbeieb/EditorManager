package org.mobelite.editormanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorBasicDTO {
    private Long id;
    private String name;
    private String nationality;
}
