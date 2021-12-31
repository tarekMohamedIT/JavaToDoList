package Application.Queries;

import java.util.List;

public interface CrudQuery<T> {
    List<T> getAll();
    List<T> getPaged();
    T getById(int id);
}
