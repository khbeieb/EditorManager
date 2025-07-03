package org.mobelite.editormanager.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mobelite.editormanager.dto.ApiResponse;
import org.mobelite.editormanager.dto.BookDTO;
import org.mobelite.editormanager.dto.MagazineDTO;
import org.mobelite.editormanager.entities.Publication;
import org.mobelite.editormanager.services.BookService;
import org.mobelite.editormanager.services.MagazineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
@Tag(name = "Publications", description = "Manage Publications")
public class PublicationController {
    private final MagazineService magazineService;
    private final BookService bookService;

    @Operation(summary = "Get all Publications (Books + Magazines)")
    @GetMapping
    public  ResponseEntity<ApiResponse<Map<String, Object>>> getAllPublications() {
        try {
            List<BookDTO> books = bookService.getBooks();
            List<MagazineDTO> magazines = magazineService.getAllMagazines();

            Map<String, Object> groupedPublications = new HashMap<>();
            groupedPublications.put("books", books);
            groupedPublications.put("magazines", magazines);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(
                            201,
                            "Publications fetched successfully",
                            groupedPublications,
                            LocalDateTime.now()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            500,
                            "Failed to fetch all publications " + e.getMessage(),
                            null,
                            LocalDateTime.now()
                    )
            );        }
    }
}
