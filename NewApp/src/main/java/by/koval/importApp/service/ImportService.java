package by.koval.importApp.service;

import by.koval.importApp.model.Import;

import java.util.List;

public interface ImportService {
    void importNotes();
    List<Import> getImports();
}
