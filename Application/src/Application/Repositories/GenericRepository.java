package Application.Repositories;

import Application.Exceptions.EntityNotFoundException;

import java.util.List;
public interface GenericRepository<T> {
    List<T> getAll();
    T getById(int id) throws EntityNotFoundException;

    void add(T entity);
    void update(T entity) throws EntityNotFoundException;
    void delete(T entity);
}
