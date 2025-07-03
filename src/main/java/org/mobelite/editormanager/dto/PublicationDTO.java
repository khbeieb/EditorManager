package org.mobelite.editormanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PublicationDTO {
    private String type;

    private String title;

    private LocalDate publicationDate;

}
