package Infrastructure.ChecklisNotes;

import Application.Commands.CrudCommand;
import Application.Exceptions.EntityNotFoundException;
import Application.Exceptions.EntityValidationException;
import Application.Utils.ValidationDictionary;
import Domain.Entities.ChecklistNote;
import Infrastructure.CQRS.BaseCommandMemoryImpl;

import java.util.List;

public class ChecklistCommandMemoryImpl extends BaseCommandMemoryImpl<ChecklistNote> {

    public ChecklistCommandMemoryImpl(List<ChecklistNote> _notes, ChecklistNote _item) {
        super(_notes, _item);
    }

    @Override
    protected void validate(ChecklistNote note){
        ValidationDictionary dictionary = new ValidationDictionary()
            .validateString(note.getTitle(), "Title", null);

        if (dictionary.isEmpty()) return;

        throw new EntityValidationException(dictionary);
    }
}
