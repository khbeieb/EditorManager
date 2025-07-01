package org.mobelite.editormanager.controllers;

import lombok.RequiredArgsConstructor;
import org.mobelite.editormanager.dto.ApiResponse;
import org.mobelite.editormanager.dto.AuthorDTO;
import org.mobelite.editormanager.entities.Author;
import org.mobelite.editormanager.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDTO>> addAuthor(@RequestBody AuthorDTO author) {
        try {
            AuthorDTO savedAuthor = authorService.addAuthor(author);
            ApiResponse<AuthorDTO> response = new ApiResponse<>(
                    201,
                    "Author created successfully",
                    savedAuthor,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<AuthorDTO> errorResponse = new ApiResponse<>(
                    500,
                    "Failed to create author",
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping
    public List<Author> getAuthors() {
        try {
            return authorService.getAllAuthors();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
