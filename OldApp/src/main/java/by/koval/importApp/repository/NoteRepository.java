package by.koval.importApp.repository;

import by.koval.importApp.model.OldNote;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<OldNote, String> {
    List<OldNote> findAll(Specification<OldNote> specification);
}
