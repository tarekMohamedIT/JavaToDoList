package Infrastructure.Notes;

import Domain.Entities.SimpleNote;
import Infrastructure.CQRS.BaseQueryMemoryImpl;

import java.util.List;

public class NotesQueryMemoryImpl extends BaseQueryMemoryImpl<SimpleNote> {
    public NotesQueryMemoryImpl(List<SimpleNote> _notes) {
        super(_notes);
    }
}
