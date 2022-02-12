package Infrastructure.ChecklisNotes;

import Application.Exceptions.EntityNotFoundException;
import Application.Queries.CrudQuery;
import Domain.Entities.ChecklistNote;
import Domain.QueryObjects.ChecklistQuery;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChecklistQueryJsonImpl implements CrudQuery<ChecklistNote> {
    private final String path;
    private final Gson gson;
    private ChecklistQuery query;
    private List<ChecklistNote> _notes;

    public ChecklistQueryJsonImpl(String path) {
        this.path = path;
        gson = new Gson();
    }

    @Override
    public List<ChecklistNote> getAll() {
        if (_notes == null) _notes = getItemsFromFile();

        List<ChecklistNote> notesToReturn = new ArrayList<>();
        for (ChecklistNote note : _notes) {
            if (isFilterCompatible(note)) notesToReturn.add(note);
        }
        return notesToReturn;
    }

    private List<ChecklistNote> getItemsFromFile() {
        try (FileReader reader = new FileReader(this.path)) {
            return Arrays.asList(gson.fromJson(new JsonReader(reader), ChecklistNote[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isFilterCompatible(ChecklistNote note) {
        if (query == null) return true;

        if (note.getId() != query.getId()) return false;

        return true;
    }

    @Override
    public List<ChecklistNote> getPaged(int pageNumber, int pageSize) {
        if (_notes == null) _notes = getItemsFromFile();

        List<ChecklistNote> notesToReturn = new ArrayList<>();
        int skipped = 0;
        for (ChecklistNote note : _notes) {
            if (!isFilterCompatible(note)) continue;

            if (skipped < pageNumber * pageSize) {
                skipped++;
                continue;
            }
            notesToReturn.add(note);
        }
        return notesToReturn;
    }

    @Override
    public void setQuery(Object query) {
        if (!(query instanceof ChecklistQuery))
            throw new InvalidParameterException("the query must be ChecklistQuery");
        this.query = (ChecklistQuery) query;
    }

    @Override
    public ChecklistNote getOne() {
        if (_notes == null) _notes = getItemsFromFile();

        for (ChecklistNote note : _notes) {
            if (isFilterCompatible(note)) return note;
        }

        throw new EntityNotFoundException();
    }
}