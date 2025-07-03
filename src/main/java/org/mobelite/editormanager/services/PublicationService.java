package org.mobelite.editormanager.services;

import lombok.AllArgsConstructor;
import org.mobelite.editormanager.dto.PublicationDTO;
import org.mobelite.editormanager.entities.Book;
import org.mobelite.editormanager.entities.Magazine;
import org.mobelite.editormanager.entities.Publication;
import org.mobelite.editormanager.repositories.PublicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;

    public List<PublicationDTO> searchByTitle(String title) {
        List<Publication> publications = publicationRepository.findByTitleContainingIgnoreCase(title);

        return publications.stream().map(pub -> {
            String type = pub instanceof Book ? "Book" :
                    pub instanceof Magazine ? "Magazine" : "Unknown";

            return new PublicationDTO(
                    type,
                    pub.getTitle(),
                    pub.getPublicationDate()
            );
        }).collect(Collectors.toList());
    }
}
