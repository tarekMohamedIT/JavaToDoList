package Application.Services;

import Application.Commands.CrudCommand;
import Application.Queries.CrudQuery;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultsHelper;
import Application.Utils.ParameterizedCallable;
import Domain.Entities.SimpleNote;

import java.util.List;
import java.util.concurrent.Callable;

public class NotesService {

    private final Callable<CrudQuery<SimpleNote>> queriesFactory;
    private final ParameterizedCallable<SimpleNote, CrudCommand> commandsFactory;

    public NotesService(
            Callable<CrudQuery<SimpleNote>> queriesFactory,
            ParameterizedCallable<SimpleNote, CrudCommand> commandsFactory) {

        this.queriesFactory = queriesFactory;
        this.commandsFactory = commandsFactory;
    }

    public ObjectResult<List<SimpleNote>> getAll(){
        return ResultsHelper.tryDo(() ->
                queriesFactory.call().getAll());
    }

    public ObjectResult<SimpleNote> getById(int id){
        return ResultsHelper.tryDo(() ->
                queriesFactory.call().getById(id));
    }

    public Result create(SimpleNote note){
        return ResultsHelper.tryDo(() ->
                commandsFactory.call(note).create());
    }

    public Result update(SimpleNote note){
        return ResultsHelper.tryDo(() ->
                commandsFactory.call(note).update());
    }

    public Result delete(SimpleNote note){
        return ResultsHelper.tryDo(() ->
                commandsFactory.call(note).delete());
    }
}
