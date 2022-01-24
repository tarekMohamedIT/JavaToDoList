package Infrastructure.Factories;

import Application.Factories.ServicesFactory;
import Application.Services.ChecklistsService;
import Application.Services.NotesService;
import Infrastructure.ChecklisNotes.ChecklistCommandJsonImpl;
import Infrastructure.ChecklisNotes.ChecklistQueryJsonImpl;
import Infrastructure.Notes.NotesCommandJsonImpl;
import Infrastructure.Notes.NotesQueryJsonImpl;

public class JsonServicesFactory implements ServicesFactory {
    private final String directory;

    public JsonServicesFactory(String directory){

        this.directory = directory;
    }

    @Override
    public NotesService createNotes() {
        return new NotesService(() -> new NotesQueryJsonImpl(directory + "/Notes.json"),
                note -> new NotesCommandJsonImpl(directory + "/Notes.json", note));
    }

    @Override
    public ChecklistsService createChecklists() {
        return new ChecklistsService(() -> new ChecklistQueryJsonImpl(directory + "/Checklists.json"),
                checklistNote -> new ChecklistCommandJsonImpl(directory + "/Checklists.json", checklistNote));    }
}
