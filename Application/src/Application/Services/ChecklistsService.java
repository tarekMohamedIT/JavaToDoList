package Application.Services;

import Application.Commands.CrudCommand;
import Application.Queries.CrudQuery;
import Application.Utils.ParameterizedCallable;
import Domain.Entities.ChecklistNote;
import Domain.QueryObjects.ChecklistQuery;

import java.util.concurrent.Callable;

public class ChecklistsService extends CqrsService<ChecklistNote, ChecklistQuery> {
    public ChecklistsService(
            Callable<CrudQuery<ChecklistNote>> queriesFactory,
            ParameterizedCallable<ChecklistNote, CrudCommand> commandsFactory) {
        super(queriesFactory, commandsFactory);
    }
}
