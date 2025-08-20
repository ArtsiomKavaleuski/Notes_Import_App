package by.koval.importApp.controller;

import by.koval.importApp.dto.ReqParams;
import by.koval.importApp.model.OldNote;
import by.koval.importApp.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/notes")
public class NotesController {
    private final NoteService noteService;

    @PostMapping
    public List<OldNote> getNotes(@RequestBody ReqParams requestParam) {
        return noteService.getAll(requestParam);
    }
}

