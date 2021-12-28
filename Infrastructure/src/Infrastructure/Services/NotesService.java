package Infrastructure.Services;

import Application.Repositories.GenericRepository;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultsHelper;
import Domain.Entities.SimpleNote;

import java.util.List;

public class NotesService {
    private final GenericRepository<SimpleNote> repository;

    public NotesService(GenericRepository<SimpleNote> repository) {
        this.repository = repository;
    }

    public ObjectResult<List<SimpleNote>> getAll(){
        return ResultsHelper.tryDo(repository::getAll);
    }

    public ObjectResult<SimpleNote> getById(int id){
        return ResultsHelper.tryDo(() -> repository.getById(id));
    }

    public Result create(SimpleNote note){
        return ResultsHelper.tryDo(() -> repository.add(note));
    }

    public Result update(SimpleNote note){
        return ResultsHelper.tryDo(() -> {
            repository.update(note);
            return note;
        });
    }

    public Result delete(SimpleNote note){
        return ResultsHelper.tryDo(() -> repository.delete(note));
    }
}
