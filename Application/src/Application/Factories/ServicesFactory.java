package Application.Factories;

import Application.Services.ChecklistsService;
import Application.Services.NotesService;

public interface ServicesFactory {
    NotesService createNotes();
    ChecklistsService createChecklists();
}
