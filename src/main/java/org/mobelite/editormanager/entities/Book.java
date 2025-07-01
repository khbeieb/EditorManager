package org.mobelite.editormanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book extends Publication {
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
