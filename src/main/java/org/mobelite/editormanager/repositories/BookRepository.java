package org.mobelite.editormanager.repositories;

import org.mobelite.editormanager.dto.BookDTO;
import org.mobelite.editormanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

}
