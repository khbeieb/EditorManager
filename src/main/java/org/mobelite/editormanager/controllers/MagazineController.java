package org.mobelite.editormanager.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mobelite.editormanager.dto.ApiResponse;
import org.mobelite.editormanager.dto.MagazineDTO;
import org.mobelite.editormanager.entities.Magazine;
import org.mobelite.editormanager.services.MagazineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/magazines")
@RequiredArgsConstructor
@Tag(name = "Magazines", description = "Manage Magazines")
public class MagazineController {

    private final MagazineService magazineService;

    @Operation(summary = "Add a new Magazine")
    @PostMapping
    public ResponseEntity<ApiResponse<MagazineDTO>> addMagazine(@Valid @RequestBody MagazineDTO request) {
        try {
            MagazineDTO savedMagazine = magazineService.addMagazine(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<>(
                            201,
                            "Magazine created successfully",
                            savedMagazine,
                            LocalDateTime.now()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            500,
                            "Failed to create magazine: " + e.getMessage(),
                            null,
                            LocalDateTime.now()
                    )
            );
        }
    }

    @Operation(summary = "Get all Magazines")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MagazineDTO>>> getAllMagazines() {
        List<MagazineDTO> magazines = magazineService.getAllMagazines();

        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(
                            201,
                            "Found " + magazines.size() + " magazines",
                            magazines,
                            LocalDateTime.now()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            500,
                            "Failed to fetch magazines " + e.getMessage(),
                            null,
                            LocalDateTime.now()
                    )
            );
        }
    }
}
