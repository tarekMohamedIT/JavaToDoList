package Infrastructure.ChecklisNotes;

import Application.Exceptions.EntityNotFoundException;
import Application.Queries.CrudQuery;
import Domain.Entities.ChecklistNote;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChecklistQueryJsonImpl implements CrudQuery<ChecklistNote> {
    private final String path;
    private List<ChecklistNote> _notes;
    private final Gson gson;

    public ChecklistQueryJsonImpl(String path) {
        this.path = path;
        gson = new Gson();

        try (FileReader reader = new FileReader(this.path)) {
            _notes = new ArrayList<>(gson.fromJson(new JsonReader(reader), ChecklistNote[].class));
        } catch (Exception e) {
            _notes = new ArrayList<>();
        }
    }

    @Override
    public List<ChecklistNote> getAll() {
        try (FileReader reader = new FileReader(this.path)) {
            return Arrays.asList(gson.fromJson(new JsonReader(reader), ChecklistNote[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ChecklistNote> getPaged() {
        try (FileReader reader = new FileReader(this.path)) {
            return Arrays.asList(gson.fromJson(new JsonReader(reader), ChecklistNote[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChecklistNote getById(int id) {
        try (FileReader reader = new FileReader(this.path)) {
            _notes = Arrays.asList(gson.fromJson(new JsonReader(reader), ChecklistNote[].class));
            for (ChecklistNote note : _notes) {
                if (note.getId() == id) return note;
            }
            throw new EntityNotFoundException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}