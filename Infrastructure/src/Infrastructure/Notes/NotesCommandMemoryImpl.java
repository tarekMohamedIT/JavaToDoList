package Infrastructure.Notes;

import Application.Commands.CrudCommand;
import Application.Exceptions.EntityNotFoundException;
import Application.Exceptions.EntityValidationException;
import Application.Utils.ValidationDictionary;
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
        validate(_item);
        incrementId(_item);
        _notes.add(_item);
    }

    private void validate(SimpleNote note){
        ValidationDictionary dictionary = new ValidationDictionary()
            .validateString(note.getTitle(), "Title", null)
            .validateString(note.getText(), "Text", null);

        if (dictionary.isEmpty()) return;

        throw new EntityValidationException(dictionary);
    }

    private void incrementId(SimpleNote note){
        note.setId(currentId++);
    }

    @Override
    public void update() {
        validate(_item);

        boolean isSuccess = false;
        for (int i = 0 ;i < _notes.size(); i++){
            if (_notes.get(i).getId() != _item.getId()) continue;

            _notes.set(i, _item);
            isSuccess = true;
            break;
        }

        if (!isSuccess) throw new EntityNotFoundException(_item.getId());
    }

    @Override
    public void delete() {
        for (int i = 0 ;i < _notes.size(); i++){
            if (_notes.get(i).getId() != _item.getId()) continue;

            _notes.remove(i);
            break;
        }

        throw new EntityNotFoundException(_item.getId());
    }
}
