package Application.Services;

import Application.Commands.CrudCommand;
import Application.Queries.CrudQuery;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultsHelper;
import Application.Utils.ParameterizedCallable;

import java.util.List;
import java.util.concurrent.Callable;

public class CqrsService<T> {
    private final Callable<CrudQuery<T>> queriesFactory;
    private final ParameterizedCallable<T, CrudCommand> commandsFactory;

    public CqrsService(
            Callable<CrudQuery<T>> queriesFactory,
            ParameterizedCallable<T, CrudCommand> commandsFactory) {
        this.queriesFactory = queriesFactory;
        this.commandsFactory = commandsFactory;
    }

    public ObjectResult<List<T>> getAll() {
        return ResultsHelper.tryDo(() ->
                queriesFactory.call().getAll());
    }

    public ObjectResult<T> getById(int id) {
        return ResultsHelper.tryDo(() ->
                queriesFactory.call().getById(id));
    }

    public final Result create(T note) {
        return ResultsHelper.tryDo(() -> {
            onBeforeCreate(note);
            commandsFactory.call(note).create();
            onCreate(note);
        });
    }

    protected void onBeforeCreate(T note){

    }

    protected void onCreate(T note){

    }

    public final Result update(T note) {
        return ResultsHelper.tryDo(() -> {
            onBeforeUpdate(note);
            commandsFactory.call(note).update();
            onUpdate(note);
        });
    }

    protected void onBeforeUpdate(T note){

    }

    protected void onUpdate(T note){

    }

    public Result delete(T note) {
        return ResultsHelper.tryDo(() ->
                commandsFactory.call(note).delete());
    }
}
