package by.koval.importApp.controller;

import by.koval.importApp.model.Import;
import by.koval.importApp.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImportController {
    private final ImportService importService;

    @GetMapping("/imports")
    public List<Import> getImports() {
        return importService.getImports();
    }
}
