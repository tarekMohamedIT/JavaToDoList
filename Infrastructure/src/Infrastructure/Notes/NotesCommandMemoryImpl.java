package Infrastructure.Notes;

import Application.Commands.CrudCommand;
import Application.Exceptions.EntityNotFoundException;
import Domain.Entities.SimpleNote;

import java.util.List;

public class NotesCommandMemoryImpl implements CrudCommand {

    private int currentId;
    private final List<SimpleNote> _notes;
    private final SimpleNote _item;

    public NotesCommandMemoryImpl(List<SimpleNote> _notes, SimpleNote _item) {
        this._notes = _notes;
        this._item = _item;

        currentId = _notes.size() > 0
                ? _notes.get(_notes.size() - 1).getId() + 1
                : 1;
    }

    @Override
    public void create() {
        incrementId(_item);
        _notes.add(_item);
    }

    private void incrementId(SimpleNote note){
        note.setId(currentId++);
    }

    @Override
    public void update() {
        for (int i = 0 ;i < _notes.size(); i++){
            if (_notes.get(i).getId() != _item.getId()) continue;

            _notes.set(i, _item);
            break;
        }

        throw new EntityNotFoundException();
    }

    @Override
    public void delete() {
        for (int i = 0 ;i < _notes.size(); i++){
            if (_notes.get(i).getId() != _item.getId()) continue;

            _notes.remove(i);
            break;
        }

        throw new EntityNotFoundException();
    }
}
