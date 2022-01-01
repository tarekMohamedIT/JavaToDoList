package SimpleNotes;

import Application.Exceptions.EntityNotFoundException;
import Application.Queries.CrudQuery;
import Domain.Entities.SimpleNote;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotesJsonQueryImpl implements CrudQuery<SimpleNote> {
    private final String path;
    private List<SimpleNote> _notes;
    private final Gson gson;

    public NotesJsonQueryImpl(String path){
        this.path = path;
        gson = new Gson();

        try (FileReader reader = new FileReader(this.path)){
            _notes = new ArrayList<>(gson.fromJson(new JsonReader(reader), SimpleNote[].class));
        } catch (Exception e) {
            _notes = new ArrayList<>();
        }
    }

    @Override
    public List<SimpleNote> getAll() {
        try (FileReader reader = new FileReader(this.path)){
            return Arrays.asList(gson.fromJson(new JsonReader(reader), SimpleNote[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SimpleNote> getPaged() {
        try (FileReader reader = new FileReader(this.path)){
            return Arrays.asList(gson.fromJson(new JsonReader(reader), SimpleNote[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SimpleNote getById(int id) {
        try (FileReader reader = new FileReader(this.path)){
            _notes =  Arrays.asList(gson.fromJson(new JsonReader(reader), SimpleNote[].class));
            for (SimpleNote note : _notes) {
                if (note.getId() == id) return note;
            }
            throw new EntityNotFoundException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
