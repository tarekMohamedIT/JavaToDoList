package Application.Services;

import Application.Commands.CrudCommand;
import Application.Queries.CrudQuery;
import Application.Utils.ParameterizedCallable;
import Domain.Entities.SimpleNote;

import java.util.concurrent.Callable;

public class NotesService extends CqrsService<SimpleNote>{
    public NotesService(
            Callable<CrudQuery<SimpleNote>> queriesFactory,
            ParameterizedCallable<SimpleNote, CrudCommand> commandsFactory) {
        super(queriesFactory, commandsFactory);
    }
}