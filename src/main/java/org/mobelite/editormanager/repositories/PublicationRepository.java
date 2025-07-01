package org.mobelite.editormanager.repositories;

import org.mobelite.editormanager.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByTitleContainingIgnoreCase(String titleString);
}
