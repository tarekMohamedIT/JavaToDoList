package Infrastructure.Notes;

import Application.Exceptions.EntityNotFoundException;
import Application.Queries.CrudQuery;
import Domain.Entities.SimpleNote;
import Domain.QueryObjects.SimpleNoteQuery;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotesQueryJsonImpl implements CrudQuery<SimpleNote> {
    private final String path;
    private SimpleNoteQuery query;
    private List<SimpleNote> _notes;
    private final Gson gson;

    public NotesQueryJsonImpl(String path){
        this.path = path;
        gson = new Gson();
    }

    @Override
    public List<SimpleNote> getAll() {
        if (_notes == null) _notes = getItemsFromFile();

        List<SimpleNote> notesToReturn = new ArrayList<>();
        for (SimpleNote note : _notes) {
            if (isFilterCompatible(note)) notesToReturn.add(note);
        }
        return notesToReturn;
    }
    private List<SimpleNote> getItemsFromFile(){
        try (FileReader reader = new FileReader(this.path)) {
            return Arrays.asList(gson.fromJson(new JsonReader(reader), SimpleNote[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isFilterCompatible(SimpleNote note){
        if (query == null) return true;

        return note.getId() == query.getId();
    }

    @Override
    public List<SimpleNote> getPaged(int pageNumber, int pageSize) {
        if (_notes == null) _notes = getItemsFromFile();

        List<SimpleNote> notesToReturn = new ArrayList<>();
        int skipped = 0;
        for (SimpleNote note : _notes) {
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
        if (query != null && !(query instanceof SimpleNoteQuery))
            throw new InvalidParameterException("the query must be SimpleNoteQuery");
        this.query = (SimpleNoteQuery) query;
    }

    @Override
    public SimpleNote getOne() {
        if (_notes == null) _notes = getItemsFromFile();

        for (SimpleNote note : _notes) {
            if (isFilterCompatible(note)) return note;
        }

        throw new EntityNotFoundException();
    }
}
