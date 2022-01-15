package Infrastructure.Notes;

import Application.Exceptions.EntityValidationException;
import Application.Utils.ValidationDictionary;
import Domain.Entities.SimpleNote;
import Infrastructure.CQRS.BaseCommandMemoryImpl;

import java.util.List;

public class NotesCommandMemoryImpl extends BaseCommandMemoryImpl<SimpleNote> {

    public NotesCommandMemoryImpl(List<SimpleNote> _notes, SimpleNote _item) {
        super(_notes, _item);
    }

    @Override
    protected void validate(SimpleNote note) {
        ValidationDictionary dictionary = new ValidationDictionary()
                .validateString(note.getTitle(), "Title", null)
                .validateString(note.getText(), "Text", null);

        if (dictionary.isEmpty()) return;

        throw new EntityValidationException(dictionary);
    }
}
