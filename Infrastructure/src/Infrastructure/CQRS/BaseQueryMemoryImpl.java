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
        List<T> itemsToReturn = new ArrayList<>();
        for (T item : _items) {
            if (isFilterCompatible(item)) itemsToReturn.add(item);
        }
        return itemsToReturn;
    }

    protected abstract boolean isFilterCompatible(T item);

    @Override
    public List<T> getPaged(int pageNumber, int pageSize) {
        List<T> itemsToReturn = new ArrayList<>();
        int skipped = 0;
        for (T item : _items) {
            if (!isFilterCompatible(item)) continue;

            if (skipped < pageNumber * pageSize) {
                skipped++;
                continue;
            }
            itemsToReturn.add(item);
        }
        return itemsToReturn;    }

    @Override
    public T getOne() {
        for (T item : _items) {
            if (isFilterCompatible(item)) return item;
        }

        throw new EntityNotFoundException();
    }
}
