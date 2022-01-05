package Infrastructure.ChecklisNotes;

import Application.Commands.CrudCommand;
import Application.Queries.CrudQuery;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultsHelper;
import Application.Utils.ParameterizedCallable;
import Domain.Entities.ChecklistNote;

import java.util.List;
import java.util.concurrent.Callable;

public class ChecklistsService {

    private final Callable<CrudQuery<ChecklistNote>> queriesFactory;
    private final ParameterizedCallable<ChecklistNote, CrudCommand> commandsFactory;

    public ChecklistsService(
            Callable<CrudQuery<ChecklistNote>> queriesFactory,
            ParameterizedCallable<ChecklistNote, CrudCommand> commandsFactory) {

        this.queriesFactory = queriesFactory;
        this.commandsFactory = commandsFactory;
    }

    public ObjectResult<List<ChecklistNote>> getAll(){
        return ResultsHelper.tryDo(() ->
                queriesFactory.call().getAll());
    }

    public ObjectResult<ChecklistNote> getById(int id){
        return ResultsHelper.tryDo(() ->
                queriesFactory.call().getById(id));
    }

    public Result create(ChecklistNote note){
        return ResultsHelper.tryDo(() ->
                commandsFactory.call(note).create());
    }

    public Result update(ChecklistNote note){
        return ResultsHelper.tryDo(() ->
                commandsFactory.call(note).update());
    }

    public Result delete(ChecklistNote note){
        return ResultsHelper.tryDo(() ->
                commandsFactory.call(note).delete());
    }
}
