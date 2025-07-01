package org.mobelite.editormanager.services;

import lombok.AllArgsConstructor;
import org.mobelite.editormanager.dto.AuthorDTO;
import org.mobelite.editormanager.entities.Author;
import org.mobelite.editormanager.entities.Book;
import org.mobelite.editormanager.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorDTO addAuthor(AuthorDTO request) {
        if(authorRepository.findAuthorByName(request.getName()).isPresent()) {
            throw new RuntimeException("Author already exists");
        }

        Author author = new Author();
        author.setName(request.getName());
        author.setBirthDate(request.getBirthDate());
        author.setNationality(request.getNationality());

        if (request.getBooks() != null) {
            for (Book book : request.getBooks()) {
                book.setAuthor(author);
            }
        }
        author.setBooks(request.getBooks());

        Author savedAuthor = authorRepository.save(author);

        return new AuthorDTO(
                savedAuthor.getName(),
                savedAuthor.getBirthDate(),
                savedAuthor.getNationality(),
                savedAuthor.getBooks()
        );
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
