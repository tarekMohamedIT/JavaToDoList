import Application.Exceptions.EntityNotFoundException;
import Application.Repositories.GenericRepository;
import Application.Repositories.Impls.GenericRepositoryImpl;
import Domain.Entities.SimpleNote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ToDoTests {
    GenericRepository<SimpleNote> _notesRepository;

    @BeforeEach
    public void init(){
        _notesRepository = new GenericRepositoryImpl<>();
    }

    @Test
    public void notesRepository_getById_success(){
        try {
            _notesRepository.add(new SimpleNote(1, "test", "test text", new Date(), new Date()));
            SimpleNote note = _notesRepository.getById(1);
            Assertions.assertNotNull(note, "should not happen");
            System.out.println("Item found with ID: " + note.getId());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void notesRepository_getById_fail(){
        try {
            SimpleNote note = _notesRepository.getById(1);
            Assertions.assertNull(note, "should not happen");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
