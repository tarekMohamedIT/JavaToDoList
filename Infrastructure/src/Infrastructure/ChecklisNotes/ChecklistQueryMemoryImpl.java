package Infrastructure.ChecklisNotes;

import Application.Exceptions.EntityNotFoundException;
import Application.Queries.CrudQuery;
import Domain.Entities.ChecklistNote;

import java.util.ArrayList;
import java.util.List;

public class ChecklistQueryMemoryImpl implements CrudQuery<ChecklistNote> {
    private final List<ChecklistNote> _notes;

    public ChecklistQueryMemoryImpl(List<ChecklistNote> _notes) {
        this._notes = _notes;
    }

    public ChecklistQueryMemoryImpl() {
        _notes = new ArrayList<>();
    }

    @Override
    public List<ChecklistNote> getAll() {
        return _notes;
    }

    @Override
    public List<ChecklistNote> getPaged() {
        return _notes;
    }

    @Override
    public ChecklistNote getById(int id) {
        for (ChecklistNote note : _notes) {
            if (note.getId() == id) return note;
        }
        throw new EntityNotFoundException();
    }
}
