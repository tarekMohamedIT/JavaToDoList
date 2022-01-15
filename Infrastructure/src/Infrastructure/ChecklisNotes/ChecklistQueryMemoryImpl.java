package Infrastructure.ChecklisNotes;

import Domain.Entities.ChecklistNote;
import Infrastructure.CQRS.BaseQueryMemoryImpl;

import java.util.List;

public class ChecklistQueryMemoryImpl extends BaseQueryMemoryImpl<ChecklistNote> {
    public ChecklistQueryMemoryImpl(List<ChecklistNote> _notes) {
        super(_notes);
    }
}
