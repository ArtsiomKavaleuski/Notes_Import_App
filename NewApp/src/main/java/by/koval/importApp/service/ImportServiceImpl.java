package by.koval.importApp.service;

import by.koval.importApp.dto.OldClientDto;
import by.koval.importApp.dto.OldNoteDto;
import by.koval.importApp.dto.ReqParams;
import by.koval.importApp.model.Import;
import by.koval.importApp.model.Note;
import by.koval.importApp.model.Patient;
import by.koval.importApp.model.User;
import by.koval.importApp.repository.ImportsRepository;
import by.koval.importApp.repository.NotesRepository;
import by.koval.importApp.repository.PatientsRepository;
import by.koval.importApp.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {
    private final NotesRepository notesRepository;
    private final PatientsRepository patientsRepository;
    private final UsersRepository usersRepository;
    private final ImportsRepository importsRepository;
    private final HttpClientService httpClientService;
    private static final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);


    @Value("${api-notes-url}")
    private String notesUrl;

    @Value("${api-clients-url}")
    private String clientsUrl;

    @Override
    public void importNotes() {
        int notesImported = 0;
        int notesFailed = 0;
        long startTime = System.currentTimeMillis();

        try {
            List<OldClientDto> oldClientsForCurrentImport = getOldClientsForCurrentImport();
            LocalDateTime dateFrom = getLastImportTime();
            LocalDateTime dateTo = LocalDateTime.now();

            logger.info("Начало импорта. Импортируются заметки с: {} и по: {}", dateFrom, dateTo);

            for (OldClientDto oldClient : oldClientsForCurrentImport) {
                try {
                    ReqParams reqParams = ReqParams.builder()
                            .agency(oldClient.getAgency())
                            .dateFrom(dateFrom)
                            .dateTo(dateTo)
                            .clientGuid(oldClient.getGuid())
                            .build();

                    OldNoteDto[] oldNotesForCurrentClientGuid = Objects.requireNonNull(
                            httpClientService.postRequest(notesUrl, reqParams, OldNoteDto[].class).getBody());

                    for (OldNoteDto oldNote : oldNotesForCurrentClientGuid) {
                        try {
                            notesImported += proceedOldNoteForClientGuid(oldNote);
                        } catch (Exception e) {
                            notesFailed++;
                            logger.error("Ошибка при импорте заметки для клиента {}: {}", oldClient.getGuid(), e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    logger.error("Ошибка при получении заметок для клиента через REST API {}: {}", oldClient.getGuid(), e.getMessage());
                }
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            logger.info("Импорт завершен. Успешно импортировано заметок: {}, Сбойных операций: {}, Время выполнения: {} мс",
                    notesImported, notesFailed, duration);

            importsRepository.save(Import.builder()
                    .notesNumberImported(notesImported)
                    .notesNumberFailed(notesFailed)
                    .importDateTime(dateTo)
                    .duration(duration)
                    .build());
        } catch (Exception e) {
            logger.error("Критическая ошибка при импорте: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Import> getImports() {
        return importsRepository.findAll();
    }

    private LocalDateTime getLastImportTime() {
        LocalDateTime lastImportTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        if (importsRepository.findTopByOrderByImportDateTimeDesc().isPresent()) {
            lastImportTime = importsRepository.findTopByOrderByImportDateTimeDesc().get().getImportDateTime();
        }
        return lastImportTime;
    }

    private List<OldClientDto> getOldClientsForCurrentImport() {
        List<OldClientDto> oldClients = Arrays.asList(Objects.requireNonNull(httpClientService
                .postRequest(clientsUrl, null, OldClientDto[].class).getBody()));
        List<Patient> patientsForImport = patientsRepository.findAllByStatusIdIn(List.of(200, 210, 230));
        List<String> patientsOldGuidsForImport = patientsForImport.stream()
                .map(Patient::getOldClientGuids)
                .flatMap(List::stream)
                .toList();
        oldClients = oldClients.stream()
                .filter(c -> patientsOldGuidsForImport.contains(c.getGuid()))
                .toList();
        return oldClients;
    }

    private int proceedOldNoteForClientGuid(OldNoteDto oldNoteForClientGuid) {
        int notesImported = 0;
        Patient patient = patientsRepository.findByOldClientGuidsContains(oldNoteForClientGuid.getClientGuid());
        User user = usersRepository.findByLogin(oldNoteForClientGuid.getLoggedUser()).orElse(null);
        if (user == null) {
            user = usersRepository.save(User.builder().login(oldNoteForClientGuid.getLoggedUser()).build());
        }
        Note note = notesRepository.findByPatientIdIsAndCreatedDateTimeEquals(patient.getId(),
                oldNoteForClientGuid.getCreatedDateTime()).orElse(null);
        if (note == null) {
            note = Note.builder()
                    .createdDateTime(oldNoteForClientGuid.getCreatedDateTime())
                    .note(oldNoteForClientGuid.getComments())
                    .lastModifiedDateTime(oldNoteForClientGuid.getModifiedDateTime())
                    .createdByUserId(user.getId())
                    .lastModifiedByUserId(user.getId())
                    .patientId(patient.getId())
                    .build();
            notesRepository.save(note);
            notesImported++;
        } else if (note.getLastModifiedDateTime().isBefore(oldNoteForClientGuid.getModifiedDateTime())) {
            note.setLastModifiedDateTime(oldNoteForClientGuid.getModifiedDateTime());
            note.setLastModifiedByUserId(user.getId());
            note.setNote(oldNoteForClientGuid.getComments());
            notesRepository.save(note);
            notesImported++;
        }
        return notesImported;
    }

}
