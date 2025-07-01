package org.mobelite.editormanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String isbn;
    private AuthorBasicDTO author;
    private LocalDate publicationDate;
}
