package org.mobelite.editormanager.repositories;

import org.mobelite.editormanager.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
