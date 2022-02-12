package Application.Services;

import Application.Commands.CrudCommand;
import Application.Queries.CrudQuery;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultsHelper;
import Application.Utils.ParameterizedCallable;

import java.util.List;
import java.util.concurrent.Callable;

public class CqrsService<T, TQuery> {
    private final Callable<CrudQuery<T>> queriesFactory;
    private final ParameterizedCallable<T, CrudCommand> commandsFactory;

    public CqrsService(
            Callable<CrudQuery<T>> queriesFactory,
            ParameterizedCallable<T, CrudCommand> commandsFactory) {
        this.queriesFactory = queriesFactory;
        this.commandsFactory = commandsFactory;
    }

    public ObjectResult<List<T>> getAll(TQuery query) {
        return ResultsHelper.tryDo(() -> {
            CrudQuery<T> queryHandler = queriesFactory.call();
            queryHandler.setQuery(query);
            return queryHandler.getAll();
        });
    }

    public ObjectResult<List<T>> getOne(TQuery query, int pageNumber, int pageSize) {
        return ResultsHelper.tryDo(() -> {
            CrudQuery<T> queryHandler = queriesFactory.call();
            queryHandler.setQuery(query);
            return queryHandler.getPaged(pageNumber, pageSize);
        });
    }

    public ObjectResult<T> getOne(TQuery query) {
        return ResultsHelper.tryDo(() -> {
            CrudQuery<T> queryHandler = queriesFactory.call();
            queryHandler.setQuery(query);
            return queryHandler.getOne();
        });
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
