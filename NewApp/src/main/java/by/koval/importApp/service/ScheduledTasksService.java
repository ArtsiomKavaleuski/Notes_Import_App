package by.koval.importApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasksService {
    private final ImportService importService;

    @Scheduled(initialDelayString = "${scheduled.initial-delay}",
            fixedRateString = "${scheduled.fixed-rate}")
    public void importNotes() {
        importService.importNotes();
    }
}
