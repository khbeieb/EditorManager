package org.mobelite.editormanager.services;

import lombok.AllArgsConstructor;
import org.mobelite.editormanager.dto.AuthorBasicDTO;
import org.mobelite.editormanager.dto.BookDTO;
import org.mobelite.editormanager.entities.Author;
import org.mobelite.editormanager.entities.Book;
import org.mobelite.editormanager.repositories.AuthorRepository;
import org.mobelite.editormanager.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookDTO addBook(Book request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new RuntimeException("Book with isbn " + request.getIsbn() + " already exists");
        }

        if (request.getAuthor() == null || request.getAuthor().getId() == null) {
            throw new RuntimeException("Author ID is required");
        }

        Author author = authorRepository.findById(request.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + request.getAuthor().getId()));

        Book book = new Book();
        book.setIsbn(request.getIsbn());
        book.setTitle(request.getTitle());
        book.setAuthor(author);
        book.setPublicationDate(request.getPublicationDate());

        Book savedBook = bookRepository.save(book);

        return new BookDTO(
                savedBook.getTitle(),
                savedBook.getIsbn(),
                new AuthorBasicDTO(
                        book.getAuthor().getId(),
                        book.getAuthor().getName(),
                        book.getAuthor().getNationality()
                ),
                savedBook.getPublicationDate()
        );
    }

    public Optional<BookDTO> getByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(book -> new BookDTO(
                        book.getTitle(),
                        book.getIsbn(),
                        new AuthorBasicDTO(
                                book.getAuthor().getId(),
                                book.getAuthor().getName(),
                                book.getAuthor().getNationality()
                        ),
                        book.getPublicationDate()
                ));
    }

    public List<BookDTO> getBooks() {
        return bookRepository.findAll().stream().map(
                book -> new BookDTO(
                        book.getTitle(),
                        book.getIsbn(),
                        new AuthorBasicDTO(
                                book.getAuthor().getId(),
                                book.getAuthor().getName(),
                                book.getAuthor().getNationality()
                        ),
                        book.getPublicationDate()
                )
        ).collect(Collectors.toList());
    }
}
