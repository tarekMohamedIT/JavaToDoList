package Infrastructure.Notes;

import Domain.Entities.SimpleNote;
import Domain.QueryObjects.ChecklistQuery;
import Domain.QueryObjects.SimpleNoteQuery;
import Infrastructure.CQRS.BaseQueryMemoryImpl;

import java.security.InvalidParameterException;
import java.util.List;

public class NotesQueryMemoryImpl extends BaseQueryMemoryImpl<SimpleNote> {
    private SimpleNoteQuery query;

    public NotesQueryMemoryImpl(List<SimpleNote> _notes) {
        super(_notes);
    }

    @Override
    protected boolean isFilterCompatible(SimpleNote note) {
        if (query == null) return true;

        if (note.getId() != query.getId()) return false;

        return true;
    }

    @Override
    public void setQuery(Object query) {
        if (!(query instanceof SimpleNoteQuery))
            throw new InvalidParameterException("the query must be SimpleNoteQuery");
        this.query = (SimpleNoteQuery) query;
    }
}
