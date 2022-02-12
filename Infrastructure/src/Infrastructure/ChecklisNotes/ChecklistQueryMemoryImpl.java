package Infrastructure.ChecklisNotes;

import Domain.Entities.ChecklistNote;
import Domain.QueryObjects.ChecklistQuery;
import Domain.QueryObjects.SimpleNoteQuery;
import Infrastructure.CQRS.BaseQueryMemoryImpl;

import java.security.InvalidParameterException;
import java.util.List;

public class ChecklistQueryMemoryImpl extends BaseQueryMemoryImpl<ChecklistNote> {
    private ChecklistQuery query;

    public ChecklistQueryMemoryImpl(List<ChecklistNote> _notes) {
        super(_notes);
    }

    @Override
    protected boolean isFilterCompatible(ChecklistNote note) {
        if (query == null) return true;

        if (note.getId() != query.getId()) return false;

        return true;
    }

    @Override
    public void setQuery(Object query) {
        if (!(query instanceof ChecklistQuery))
            throw new InvalidParameterException("the query must be ChecklistQuery");
        this.query = (ChecklistQuery) query;
    }
}
