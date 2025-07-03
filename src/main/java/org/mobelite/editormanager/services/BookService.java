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

    public BookDTO addBook(BookDTO bookDTO) {
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new RuntimeException("Book with isbn " + bookDTO.getIsbn() + " already exists");
        }

        Author author = authorRepository.findById(bookDTO.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + bookDTO.getAuthor().getId()));

        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setPublicationDate(bookDTO.getPublicationDate());

        Book savedBook = bookRepository.save(book);

        return new BookDTO(
                savedBook.getTitle(),
                savedBook.getIsbn(),
                new AuthorBasicDTO(
                        author.getId(),
                        author.getName(),
                        author.getNationality()
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
