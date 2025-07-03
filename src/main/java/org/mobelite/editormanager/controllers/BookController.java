package org.mobelite.editormanager.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mobelite.editormanager.dto.ApiResponse;
import org.mobelite.editormanager.dto.AuthorDTO;
import org.mobelite.editormanager.dto.BookDTO;
import org.mobelite.editormanager.entities.Book;
import org.mobelite.editormanager.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Books", description = "Manage Books")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Add a new Book")
    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info("Received BookDTO: {}", bookDTO);

        try {
            BookDTO savedBook = bookService.addBook(bookDTO);
            ApiResponse<BookDTO> response = new ApiResponse<>(
                    201,
                    "Book created successfully",
                    savedBook,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error creating book", e);
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(
                    500,
                    "Failed to create book: " + e.getMessage(),
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(summary = "Get all Books")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBooks() {
        try {
            List<BookDTO> books = bookService.getBooks();
            ApiResponse<List<BookDTO>> response = new ApiResponse<>(
                    200,
                    "Books fetched successfully",
                    books,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<BookDTO>> errorResponse = new ApiResponse<>(
                    500,
                    "Failed to fetch books " + e.getMessage(),
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(summary = "Get book by isbn")
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<ApiResponse<BookDTO>> getByIsbn(@PathVariable String isbn) {
        try {
            Optional<BookDTO> book = bookService.getByIsbn(isbn);

            if (book.isPresent()) {
                ApiResponse<BookDTO> response = new ApiResponse<>(
                        200,
                        "Book fetched successfully",
                        book.get(),
                        LocalDateTime.now()
                );
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<BookDTO> response = new ApiResponse<>(
                        404,
                        "Book not found with ISBN " + isbn,
                        null,
                        LocalDateTime.now()
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            log.error("Error fetching book", e);
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(
                    500,
                    "Failed to fetch book with ISBN " + isbn + "error: " + e.getMessage(),
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}
