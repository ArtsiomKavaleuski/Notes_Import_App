package by.koval.importApp.service;

import by.koval.importApp.model.OldClient;
import by.koval.importApp.model.OldNote;
import by.koval.importApp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledTasksOldService {
    private final ClientRepository clientRepository;
    private final NoteService noteService;

    @Scheduled(initialDelayString = "30000")
    public void addClients() {
        clientRepository.save(OldClient.builder()
                .agency("vhh4")
                .guid("01588E84-D45A-EB98-F47F-716073A4F1EF")
                .firstName("Ne")
                .lastName("Abr")
                .status("INACTIVE")
                .dob(LocalDate.of(1999, 10, 15))
                .createdDateTime(LocalDateTime.of(2021, 11, 15, 11, 51, 59))
                .build());
    }

    @Scheduled(initialDelayString = "30000")
    public void addNote() {
        noteService.addNote(OldNote.builder()
                .comments("Patient Care Coordinator, reached out to patient caregiver is still in the hospital.")
                .guid("20CBCEDA-3764-7F20-0BB6-4D6DD46BA9F8")
                .modifiedDateTime(LocalDateTime.of(2021, 11, 15, 11, 51, 59))
                .clientGuid("C5DCAA49-ADE5-E65C-B776-3F6D7B5F2055")
                .datetime(LocalDateTime.of(2021, 9, 16, 12, 2, 26))
                .loggedUser("p.vasya")
                .createdDateTime(LocalDateTime.of(2021, 11, 15, 11, 51, 59))
                .build());
    }
}
