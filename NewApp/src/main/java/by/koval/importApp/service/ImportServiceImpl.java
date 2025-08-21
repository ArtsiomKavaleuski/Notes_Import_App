package by.koval.importApp.service;

import by.koval.importApp.dto.OldClientDto;
import by.koval.importApp.dto.OldNoteDto;
import by.koval.importApp.dto.ReqParams;
import by.koval.importApp.model.Note;
import by.koval.importApp.model.Patient;
import by.koval.importApp.model.User;
import by.koval.importApp.repository.ImportsRepository;
import by.koval.importApp.repository.NotesRepository;
import by.koval.importApp.repository.PatientsRepository;
import by.koval.importApp.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportServiceImpl implements ImportService {
    private final NotesRepository notesRepository;
    private final PatientsRepository patientsRepository;
    private final UsersRepository usersRepository;
    private final ImportsRepository importsRepository;
    private final HttpClientService httpClientService;

    private final String oldAppUrl = "${old-app-url}";

    @Override
    public void importNotes() {
        List<OldClientDto> oldClientsForCurrentImport = getOldClientsForCurrentImport();
        LocalDateTime dateFrom = getLastImportTime();
        LocalDateTime dateTo = LocalDateTime.now();
//        ReqParams reqParams = ReqParams.builder()
//                .dateFrom(getLastImportTime())
//                .dateTo(LocalDateTime.now())
//                .build();
//        int batchSize = 50;
//        for (int i = 0; i < oldClientsForCurrentImport.size(); i+=batchSize) {
//            List<OldClientDto> batchOldClients = oldClientsForCurrentImport.subList(i, Math.min(i+batchSize, oldClientsForCurrentImport.size()));
//            List<OldNoteDto> batchOldNotes = batchOldClients.parallelStream()
//                    .map(oldClientDto -> Arrays.asList(Objects.requireNonNull(httpClientService
//                            .postRequest(oldAppUrl + "/notes",
//                                    ReqParams.builder()
//                                            .agency(oldClientDto.getAgency())
//                                            .dateFrom(dateFrom)
//                                            .dateTo(dateTo)
//                                            .clientGuid(oldClientDto.getGuid()).build(),
//                                    OldNoteDto[].class).getBody())))
//                    .flatMap(List::stream)
//                    .toList();
//
//        }
        for (OldClientDto oldClient : oldClientsForCurrentImport) {
            ReqParams reqParams = ReqParams.builder()
                    .agency(oldClient.getAgency())
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
                    .clientGuid(oldClient.getGuid())
                    .build();
            List<OldNoteDto> oldNotesForCurrentClientGuid = Arrays.asList(Objects.requireNonNull(httpClientService
                    .postRequest(oldAppUrl + "/notes", reqParams, OldNoteDto[].class).getBody()));

//            oldNotesForCurrentClientGuid.parallelStream()
//                    .map(n -> proceedOldNoteForClientGuid(n, n.getClientGuid()))
//                    .toList();
        }



    }

    private Note mapNote(OldNoteDto oldNoteDto, int patientId) {
        Note note = new Note();
        note.setNote(oldNoteDto.getComments());
        note.setLastModifiedDateTime(oldNoteDto.getModifiedDateTime());
        note.setCreatedDateTime(oldNoteDto.getCreatedDateTime());
        note.setPatientId(patientId);
        if (usersRepository.findByLogin(oldNoteDto.getLoggedUser()).isEmpty()) {
            usersRepository.save(User.builder().login(oldNoteDto.getLoggedUser()).build());
        }
        note.setCreatedByUserId(usersRepository.save(User.builder().login(oldNoteDto.getLoggedUser()).build()).getId());

        return note;
    }

    private LocalDateTime getLastImportTime() {
        LocalDateTime lastImportTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        if (importsRepository.findTopByOrderByLastImportDateTimeDesc().isPresent()) {
            lastImportTime = importsRepository.findTopByOrderByLastImportDateTimeDesc().get().getLastImportDateTime();
        }
        return lastImportTime;
    }

    private List<OldClientDto> getOldClientsForCurrentImport() {
        List<OldClientDto> oldClients = Arrays.asList(Objects.requireNonNull(httpClientService.postRequest(oldAppUrl + "/clients", OldClientDto[].class).getBody()));
        List<Patient> patientsForImport = patientsRepository.findAllByStatusIdIn(List.of(200, 210, 230));
        List<String> patientsOldGuidsForImport = patientsForImport.stream().map(Patient::getOldClientGuids).flatMap(List::stream).toList();
        oldClients = oldClients.stream().filter(c -> patientsOldGuidsForImport.contains(c.getGuid())).toList();
        return oldClients;
    }

    private void proceedOldNoteForClientGuid(OldNoteDto oldNoteForClientGuid, String clientGuid) {
        Patient patient = patientsRepository.findByOldClientGuidsContains(clientGuid);
        Optional<Note> note = notesRepository.findByPatientIdIsAndCreatedDateTimeEquals(patient.getId(), oldNoteForClientGuid.getCreatedDateTime());
        if (note.isPresent() && note.get().getLastModifiedDateTime().isBefore(oldNoteForClientGuid.getModifiedDateTime())) {
            Note newNote = mapNote(oldNoteForClientGuid, patient.getId());
            newNote.setId(note.get().getId());
            if (usersRepository.findByLogin(oldNoteForClientGuid.getLoggedUser()).isEmpty()) {
                newNote.setLastModifiedByUserId(usersRepository.save(User.builder().login(oldNoteForClientGuid.getLoggedUser()).build()).getId());
            } else {
                newNote.setLastModifiedByUserId(usersRepository.findByLogin(oldNoteForClientGuid.getLoggedUser()).get().getId());
            }
            notesRepository.save(newNote);
        } else {
            notesRepository.save(mapNote(oldNoteForClientGuid, patient.getId()));
        }
    }
}
