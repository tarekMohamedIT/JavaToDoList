package Infrastructure.Notes;

import Application.Exceptions.EntityNotFoundException;
import Application.Queries.CrudQuery;
import Domain.Entities.SimpleNote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesQueryMemoryImpl implements CrudQuery<SimpleNote> {
    private final List<SimpleNote> _notes;

    public NotesQueryMemoryImpl(List<SimpleNote> _notes) {
        this._notes = _notes;
    }

    public NotesQueryMemoryImpl() {
        _notes = new ArrayList<>();
    }

    @Override
    public List<SimpleNote> getAll() {
        return _notes;
    }

    @Override
    public List<SimpleNote> getPaged() {
        return _notes;
    }

    @Override
    public SimpleNote getById(int id) {
        for (SimpleNote note : _notes) {
            if (note.getId() == id) return note;
        }
        throw new EntityNotFoundException();
    }
}
