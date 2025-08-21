package by.koval.importApp.repository;

import by.koval.importApp.model.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImportsRepository extends JpaRepository<Import, Integer> {
    Optional<Import> findTopByOrderByLastImportDateTimeDesc();
}
