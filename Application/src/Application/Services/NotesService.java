package Application.Services;

import Application.Commands.CrudCommand;
import Application.PubSub.Publisher;
import Application.Queries.CrudQuery;
import Application.Utils.ParameterizedCallable;
import Domain.Entities.SimpleNote;
import Domain.QueryObjects.SimpleNoteQuery;

import java.util.concurrent.Callable;

public class NotesService extends CqrsService<SimpleNote, SimpleNoteQuery>{
    public NotesService(
            Callable<CrudQuery<SimpleNote>> queriesFactory,
            ParameterizedCallable<SimpleNote, CrudCommand> commandsFactory) {
        super(queriesFactory, commandsFactory);
    }

    @Override
    protected void onCreate(SimpleNote note) {
        Publisher.getInstance().publish(note, this, "EntityAdded");
    }

    @Override
    protected void onUpdate(SimpleNote note) {
        Publisher.getInstance().publish(note, this, "EntityUpdated");
    }
}