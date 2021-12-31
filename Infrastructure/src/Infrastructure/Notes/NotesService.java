package Infrastructure.Notes;

import Application.Repositories.GenericRepository;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultsHelper;
import Domain.Entities.SimpleNote;

import java.util.ArrayList;
import java.util.List;

public class NotesService {
    private final GenericRepository<SimpleNote> repository;

    public NotesService(GenericRepository<SimpleNote> repository) {
        this.repository = repository;
    }

    public ObjectResult<List<SimpleNote>> getAll(){
        return ResultsHelper.tryDo(() ->
                new NotesQueryMemoryImpl(new ArrayList<>(repository.getAll())).getAll());
    }

    public ObjectResult<SimpleNote> getById(int id){
        return ResultsHelper.tryDo(() ->
                new NotesQueryMemoryImpl(new ArrayList<>(repository.getAll())).getById(id));
    }

    public Result create(SimpleNote note){
        return ResultsHelper.tryDo(() ->
                new NotesCommandMemoryImpl(repository.getAll(), note).create());
    }

    public Result update(SimpleNote note){
        return ResultsHelper.tryDo(() ->
            new NotesCommandMemoryImpl(repository.getAll(), note).update());
    }

    public Result delete(SimpleNote note){

        return ResultsHelper.tryDo(() ->
                new NotesCommandMemoryImpl(repository.getAll(), note).delete());
    }
}
