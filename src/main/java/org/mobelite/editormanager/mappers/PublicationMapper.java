package org.mobelite.editormanager.mappers;

import org.mobelite.editormanager.dto.PublicationDTO;
import org.mobelite.editormanager.entities.Book;
import org.mobelite.editormanager.entities.Magazine;
import org.mobelite.editormanager.entities.Publication;

public class PublicationMapper {
    public static PublicationDTO toDTO(Publication publication) {
        String type;
        if (publication instanceof Book) {
            type = "Book";
        } else if (publication instanceof Magazine) {
            type = "Magazine";
        } else {
            type = "Unknown";
        }

        return new PublicationDTO(
                type,
                publication.getTitle(),
                publication.getPublicationDate()
        );
    }
}