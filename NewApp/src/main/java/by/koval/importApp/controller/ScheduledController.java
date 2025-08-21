package by.koval.importApp.controller;

import by.koval.importApp.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduledController {
    private final ImportService importService;

    @Scheduled(initialDelayString = "${scheduled.initial-delay}",
    fixedRateString = "${scheduled.fixed-rate}")
    public void importNotes() {
        importService.importNotes();
    }
}
