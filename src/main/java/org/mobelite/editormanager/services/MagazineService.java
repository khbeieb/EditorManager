package org.mobelite.editormanager.services;

import lombok.AllArgsConstructor;
import org.mobelite.editormanager.entities.Magazine;
import org.mobelite.editormanager.repositories.MagazineRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MagazineService {
    private final MagazineRepository magazineRepository;

    Magazine addMagazine(Magazine magazine) {
        return magazineRepository.save(magazine);
    }

//    Optional<Magazine> getMagazineByTitle(String title) {
//    }
}
