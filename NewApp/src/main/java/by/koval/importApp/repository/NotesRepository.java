package by.koval.importApp.repository;

import by.koval.importApp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface NotesRepository extends JpaRepository<Note, Integer> {
    Optional<Note> findByPatientIdIsAndCreatedDateTimeEquals(int patientId, LocalDateTime createdDateTime);
}
