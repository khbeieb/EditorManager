package org.mobelite.editormanager.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Authors", description = "Manage Authors")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Add a new Author")
    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDTO>> addAuthor(@Valid @RequestBody AuthorDTO author) {
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
                    "Failed to create author " + e.getMessage(),
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(summary = "Get all Authors")
    @GetMapping
    public List<Author> getAuthors() {
        try {
            return authorService.getAllAuthors();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
