package org.mobelite.editormanager.services;

import lombok.AllArgsConstructor;
import org.mobelite.editormanager.dto.PublicationDTO;
import org.mobelite.editormanager.repositories.BookRepository;
import org.mobelite.editormanager.repositories.MagazineRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationService {
    private final MagazineRepository magazineRepository;
    private final BookRepository bookRepository;


    public List<PublicationDTO> searchByTitle(String title) {
        List<PublicationDTO> books = bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(book -> new PublicationDTO("Book", book.getTitle(), book.getPublicationDate()))
                .toList();

        List<PublicationDTO> magazines = magazineRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(mag -> new PublicationDTO("Magazine", mag.getTitle(), mag.getPublicationDate()))
                .toList();

        List<PublicationDTO> results = new ArrayList<>();
        results.addAll(books);
        results.addAll(magazines);
        return results;
    }
}
