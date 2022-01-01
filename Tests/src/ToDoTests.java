import Application.Exceptions.EntityValidationException;
import Application.Repositories.Impls.GenericRepositoryImpl;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultState;
import Domain.Entities.SimpleNote;
import Infrastructure.Notes.NotesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ToDoTests {
    NotesService service;
    @BeforeEach
    public void init(){
        service = new NotesService(new GenericRepositoryImpl<>());
    }

    @Test
    public void notesService_getById_success(){
        Result createResult = service.create(new SimpleNote(0, "test", "test text", new Date(), new Date()));
        Assertions.assertSame(createResult.getState(), ResultState.SUCCESS);

        ObjectResult<SimpleNote> fetchResult = service.getById(1);

        Assertions.assertSame(fetchResult.getState(), ResultState.SUCCESS);

        SimpleNote note = fetchResult.getObject();

        Assertions.assertEquals(1, note.getId(), "should not happen");
        Assertions.assertEquals("test", note.getTitle(), "should not happen");
        Assertions.assertEquals("test text", note.getText(), "should not happen");

        System.out.println("Item found with ID: " + note.getId());
    }

    @Test
    public void notesService_getById_fail(){
        ObjectResult<SimpleNote> noteResult = service.getById(1);
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

        ObjectResult<SimpleNote> fetchResult = service.getById(1);
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

        ObjectResult<SimpleNote> fetchResult = service.getById(1);
        Assertions.assertSame(fetchResult.getState(), ResultState.SUCCESS, "Should not happen");

        SimpleNote fetchedNote = fetchResult.getObject();
        fetchedNote.setTitle("test title");

        Result updateResult = service.update(fetchedNote);
        Assertions.assertSame(updateResult.getState(), ResultState.SUCCESS, "Should not happen");

        ObjectResult<SimpleNote> afterUpdateFetchResult = service.getById(1);
        Assertions.assertSame(afterUpdateFetchResult.getState(), ResultState.SUCCESS, "Should not happen");

        SimpleNote updatedFetchedNote = afterUpdateFetchResult.getObject();
        Assertions.assertSame(updatedFetchedNote.getTitle(), "test title", "Should not happen");
    }
}
