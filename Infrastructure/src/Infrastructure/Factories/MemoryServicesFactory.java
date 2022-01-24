package Infrastructure.Factories;

import Application.Factories.ServicesFactory;
import Application.Services.ChecklistsService;
import Application.Services.NotesService;
import Domain.Entities.ChecklistItem;
import Domain.Entities.ChecklistNote;
import Domain.Entities.SimpleNote;
import Infrastructure.ChecklisNotes.ChecklistCommandJsonImpl;
import Infrastructure.ChecklisNotes.ChecklistCommandMemoryImpl;
import Infrastructure.ChecklisNotes.ChecklistQueryJsonImpl;
import Infrastructure.ChecklisNotes.ChecklistQueryMemoryImpl;
import Infrastructure.Notes.NotesCommandJsonImpl;
import Infrastructure.Notes.NotesCommandMemoryImpl;
import Infrastructure.Notes.NotesQueryJsonImpl;
import Infrastructure.Notes.NotesQueryMemoryImpl;

import java.util.ArrayList;
import java.util.List;

public class MemoryServicesFactory implements ServicesFactory {
    List<SimpleNote> notes;
    List<ChecklistNote> checklists;
    public MemoryServicesFactory(){
        notes = new ArrayList<>();
        checklists = new ArrayList<>();
    }

    @Override
    public NotesService createNotes() {
        return new NotesService(() -> new NotesQueryMemoryImpl(notes),
                note -> new NotesCommandMemoryImpl(notes, note));
    }

    @Override
    public ChecklistsService createChecklists() {
        return new ChecklistsService(() -> new ChecklistQueryMemoryImpl(checklists),
                checklistNote -> new ChecklistCommandMemoryImpl(checklists, checklistNote));    }
}