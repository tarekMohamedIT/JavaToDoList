package Application.Queries;

import java.util.List;

public interface CrudQuery<T> {
    void setQuery(Object query);
    T getOne();
    List<T> getAll();
    List<T> getPaged(int pageNumber, int pageSiz);
}
