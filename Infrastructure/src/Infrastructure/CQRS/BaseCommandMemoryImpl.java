package Infrastructure.CQRS;

import Application.Commands.CrudCommand;
import Application.Exceptions.EntityNotFoundException;
import Domain.Entities.Entity;
import java.util.List;

public abstract class BaseCommandMemoryImpl<T extends Entity> implements CrudCommand {

    private int currentId;
    private final List<T> _items;
    private final T _item;

    public BaseCommandMemoryImpl(List<T> _notes, T _item) {
        this._items = _notes;
        this._item = _item;

        currentId = _notes.size() > 0
                ? _notes.get(_notes.size() - 1).getId() + 1
                : 1;
    }

    @Override
    public void create() {
        validate(_item);
        incrementId(_item);
        _items.add(_item);
    }

    protected abstract void validate(T note);

    private void incrementId(T note){
        note.setId(currentId++);
    }

    @Override
    public void update() {
        validate(_item);

        boolean isSuccess = false;
        for (int i = 0; i < _items.size(); i++){
            if (_items.get(i).getId() != _item.getId()) continue;

            _items.set(i, _item);
            isSuccess = true;
            break;
        }

        if (!isSuccess) throw new EntityNotFoundException(_item.getId());
    }

    @Override
    public void delete() {
        for (int i = 0; i < _items.size(); i++){
            if (_items.get(i).getId() != _item.getId()) continue;

            _items.remove(i);
            break;
        }

        throw new EntityNotFoundException(_item.getId());
    }
}
