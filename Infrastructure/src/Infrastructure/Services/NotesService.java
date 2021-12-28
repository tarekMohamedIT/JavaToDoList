package Infrastructure.Services;

import Application.Repositories.GenericRepository;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultsHelper;
import Domain.Entities.SimpleNote;

import java.util.List;

public class NotesService {
    private GenericRepository<SimpleNote> repository;

    public NotesService(GenericRepository<SimpleNote> repository) {
        this.repository = repository;
    }

    public ObjectResult<List<SimpleNote>> GetAll(){
        return ResultsHelper.tryDo(() -> repository.getAll());
    }
}
