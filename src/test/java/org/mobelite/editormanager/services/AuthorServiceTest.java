package org.mobelite.editormanager.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mobelite.editormanager.dto.AuthorDTO;
import org.mobelite.editormanager.entities.Author;
import org.mobelite.editormanager.entities.Book;
import org.mobelite.editormanager.repositories.AuthorRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addAuthor_shouldSaveAndReturnAuthorDTO_whenAuthorDoesNotExist() {
        // Arrange
        AuthorDTO request = new AuthorDTO(
                "Jane Austen",
                LocalDate.of(1775, 12, 16),
                "British",
                new ArrayList<>()
        );

        when(authorRepository.findAuthorByName("Jane Austen")).thenReturn(Optional.empty());

        Author savedAuthor = new Author();
        savedAuthor.setId(1L);
        savedAuthor.setName("Jane Austen");
        savedAuthor.setBirthDate(LocalDate.of(1775, 12, 16));
        savedAuthor.setNationality("British");
        savedAuthor.setBooks(new ArrayList<>());

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        // Act
        AuthorDTO result = authorService.addAuthor(request);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Austen", result.getName());
        assertEquals(LocalDate.of(1775, 12, 16), result.getBirthDate());
        assertEquals("British", result.getNationality());

        verify(authorRepository).findAuthorByName("Jane Austen");
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    void addAuthor_shouldThrowException_whenAuthorAlreadyExists() {
        // Arrange
        AuthorDTO request = new AuthorDTO("Jane Austen", LocalDate.of(1775, 12, 16), "British", null);

        when(authorRepository.findAuthorByName("Jane Austen")).thenReturn(Optional.of(new Author()));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authorService.addAuthor(request));
        assertEquals("Author already exists", exception.getMessage());

        verify(authorRepository).findAuthorByName("Jane Austen");
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void getAllAuthors_shouldReturnListOfAuthors() {
        // Arrange
        List<Author> authors = List.of(new Author(), new Author());
        when(authorRepository.findAll()).thenReturn(authors);

        // Act
        List<Author> result = authorService.getAllAuthors();

        // Assert
        assertEquals(2, result.size());
        verify(authorRepository).findAll();
    }
}