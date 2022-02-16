import Application.Context.ApplicationCore;
import Application.Exceptions.EntityValidationException;
import Application.Factories.ServicesFactory;
import Application.PubSub.Publisher;
import Application.PubSub.Subscriber;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultState;
import Application.Services.NotesService;
import Domain.Entities.Entity;
import Domain.Entities.SimpleNote;
import Domain.QueryObjects.SimpleNoteQuery;
import Infrastructure.Factories.MemoryServicesFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoTests {
    NotesService service;
    List<SimpleNote> notesList;

    public ToDoTests(){
        ApplicationCore
                .registerFactoryAs(ServicesFactory.class, MemoryServicesFactory::new);
    }

    @BeforeEach
    public void init(){
        notesList = new ArrayList<>();
        service = ApplicationCore.resolve(ServicesFactory.class).createNotes();

        Publisher.getInstance().subscribe("EntityAdded", new Subscriber() {
            @Override
            public void onMessage(Object item, Object source) {
                StringBuilder builder = new StringBuilder("Item Added");

                if (item instanceof Entity){
                    builder
                            .append(" With ID: ")
                            .append(((Entity) item).getId());
                }
                System.out.println(builder);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

        Publisher.getInstance().subscribe("EntityUpdated", new Subscriber() {
            @Override
            public void onMessage(Object item, Object source) {
                System.out.println("Item Updated");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Exception: " + throwable.getMessage());
            }
        });
    }

    @Test
    public void notesService_getById_success(){
        Result createResult = service.create(new SimpleNote(0, "test", "test text", new Date(), new Date()));
        Assertions.assertSame(createResult.getState(), ResultState.SUCCESS);

        ObjectResult<SimpleNote> fetchResult = service.getOne(new SimpleNoteQuery().setId(1));

        Assertions.assertSame(fetchResult.getState(), ResultState.SUCCESS);

        SimpleNote note = fetchResult.getObject();

        Assertions.assertEquals(1, note.getId(), "should not happen");
        Assertions.assertEquals("test", note.getTitle(), "should not happen");
        Assertions.assertEquals("test text", note.getText(), "should not happen");

        System.out.println("Item found with ID: " + note.getId());
    }

    @Test
    public void notesService_getById_fail(){
        ObjectResult<SimpleNote> noteResult = service.getOne(new SimpleNoteQuery().setId(1));
        Assertions.assertSame(noteResult.getState(), ResultState.FAIL, "Should not happen");
    }

    @Test
    public void notesService_addNew_failedWithValidationErrors(){
        Result createResult = service.create(new SimpleNote(0, "", "test text", new Date(), new Date()));
        Assertions.assertSame(createResult.getState(), ResultState.FAIL, "Should not happen");
        Assertions.assertTrue(createResult.getException() instanceof EntityValidationException, "Should not happen");

        EntityValidationException validationException = (EntityValidationException)createResult.getException();

        Assertions.assertEquals(1, validationException.getValidationErrors().size());

        System.out.println(validationException.getValidationErrors().get("Title"));
    }


    @Test
    public void notesService_addNew_success(){
        Result createResult = service.create(new SimpleNote(0, "test title", "test text", new Date(), new Date()));
        Assertions.assertSame(createResult.getState(), ResultState.SUCCESS, "Should not happen");
    }

    @Test
    public void notesService_update_failedWithValidationErrors(){
        Result createResult = service.create(new SimpleNote(0, "test", "test text", new Date(), new Date()));
        Assertions.assertSame(createResult.getState(), ResultState.SUCCESS, "Should not happen");

        ObjectResult<SimpleNote> fetchResult = service.getOne(new SimpleNoteQuery().setId(1));
        Assertions.assertSame(fetchResult.getState(), ResultState.SUCCESS, "Should not happen");

        SimpleNote fetchedNote = fetchResult.getObject();
        fetchedNote.setTitle("");

        Result updateResult = service.update(fetchedNote);
        Assertions.assertSame(updateResult.getState(), ResultState.FAIL, "Should not happen");
        Assertions.assertTrue(updateResult.getException() instanceof EntityValidationException, "Should not happen");

        EntityValidationException validationException = (EntityValidationException)updateResult.getException();

        Assertions.assertEquals(1, validationException.getValidationErrors().size());

        System.out.println(validationException.getValidationErrors().get("Title"));
    }


    @Test
    public void notesService_update_success(){
        Result createResult = service.create(new SimpleNote(0, "test", "test text", new Date(), new Date()));
        Assertions.assertSame(createResult.getState(), ResultState.SUCCESS, "Should not happen");

        ObjectResult<SimpleNote> fetchResult = service.getOne(new SimpleNoteQuery().setId(1));
        Assertions.assertSame(fetchResult.getState(), ResultState.SUCCESS, "Should not happen");

        SimpleNote fetchedNote = fetchResult.getObject();
        fetchedNote.setTitle("test title");

        Result updateResult = service.update(fetchedNote);
        Assertions.assertSame(updateResult.getState(), ResultState.SUCCESS, "Should not happen");

        ObjectResult<SimpleNote> afterUpdateFetchResult = service.getOne(new SimpleNoteQuery().setId(1));
        Assertions.assertSame(afterUpdateFetchResult.getState(), ResultState.SUCCESS, "Should not happen");

        SimpleNote updatedFetchedNote = afterUpdateFetchResult.getObject();
        Assertions.assertSame(updatedFetchedNote.getTitle(), "test title", "Should not happen");
    }
}
