package Application.Repositories.Impls;

import Application.Exceptions.EntityNotFoundException;
import Application.Repositories.GenericRepository;
import Domain.Entities.Entity;

import java.util.*;

public class GenericRepositoryImpl<T extends Entity> implements GenericRepository<T> {

    private Collection<T> _itemsList;

    public GenericRepositoryImpl(){
        _itemsList = new HashSet<T>();
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(_itemsList);
    }

    @Override
    public T getById(int id) throws EntityNotFoundException {
        for (T item : _itemsList) {
            if (item.getId() == id)
                return item;
        }

        throw new EntityNotFoundException();
    }

    @Override
    public void add(T entity) {
        _itemsList.add(entity);
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        T entityFromDb = getById(entity.getId());
    }

    @Override
    public void delete(T entity) {

    }
}
