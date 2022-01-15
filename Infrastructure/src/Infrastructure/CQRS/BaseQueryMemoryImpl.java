package Infrastructure.CQRS;

import Application.Exceptions.EntityNotFoundException;
import Application.Queries.CrudQuery;
import Domain.Entities.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseQueryMemoryImpl<T extends Entity> implements CrudQuery<T> {
    private final List<T> _items;

    public BaseQueryMemoryImpl(List<T> _notes) {
        this._items = _notes;
    }

    public BaseQueryMemoryImpl() {
        _items = new ArrayList<>();
    }

    @Override
    public List<T> getAll() {
        return _items;
    }

    @Override
    public List<T> getPaged() {
        return _items;
    }

    @Override
    public T getById(int id) {
        for (T note : _items) {
            if (note.getId() == id) return note;
        }
        throw new EntityNotFoundException();
    }
}
