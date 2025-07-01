package org.mobelite.editormanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mobelite.editormanager.entities.Book;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class AuthorDTO {
    private String name;
    private LocalDate birthDate;
    private String nationality;
    private List<Book> books;
}
