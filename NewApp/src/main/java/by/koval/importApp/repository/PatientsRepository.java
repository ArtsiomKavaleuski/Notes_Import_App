package by.koval.importApp.repository;

import by.koval.importApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientsRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findAllByStatusIdIn(List<Integer> statusIds);
    Patient findByOldClientGuidsContains(String oldClientGuid);
}
