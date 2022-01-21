package Infrastructure.ChecklisNotes;

import Application.Commands.CrudCommand;
import Application.Exceptions.EntityNotFoundException;
import Application.Exceptions.EntityValidationException;
import Application.Utils.ValidationDictionary;
import Domain.Entities.ChecklistNote;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChecklistCommandJsonImpl implements CrudCommand {
    private final String path;
    private int currentId;
    private List<ChecklistNote> _notes;
    private final ChecklistNote _item;
    private final Gson gson;

    public ChecklistCommandJsonImpl(String path, ChecklistNote _item){
        this._item = _item;
        this.path = path;
        gson = new Gson();

        try {
            _notes = Arrays.asList(gson.fromJson(new JsonReader(new FileReader(this.path)), ChecklistNote[].class));
        } catch (FileNotFoundException e) {
            _notes = new ArrayList<>();
        }

        currentId = _notes.size() > 0
                ? _notes.get(_notes.size() - 1).getId() + 1
                : 1;
    }

    @Override
    public void create() {
        validate(_item);
        incrementId(_item);
        _notes.add(_item);
        saveChanges();
    }

    private void saveChanges() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String jsonData = gson.toJson(_notes);
            writer.write(jsonData);
        }
        catch (IOException io){
            File file = new File(path);
            if (file.getParentFile().mkdirs()) saveChanges();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void validate(ChecklistNote note){
        ValidationDictionary dictionary = new ValidationDictionary()
                .validateString(note.getTitle(), "Title", null);

        if (dictionary.isEmpty()) return;

        throw new EntityValidationException(dictionary);
    }

    private void incrementId(ChecklistNote note){
        note.setId(currentId++);
    }

    @Override
    public void update() {
        validate(_item);

        boolean isSuccess = false;
        for (int i = 0 ;i < _notes.size(); i++){
            if (_notes.get(i).getId() != _item.getId()) continue;

            _notes.set(i, _item);
            saveChanges();
            isSuccess = true;
            break;
        }

        if (!isSuccess) throw new EntityNotFoundException(_item.getId());
    }

    @Override
    public void delete() {
        boolean isSuccess = false;
        for (int i = 0 ;i < _notes.size(); i++){
            if (_notes.get(i).getId() != _item.getId()) continue;

            _notes.remove(i);
            saveChanges();
            isSuccess = true;
            break;
        }

        if (!isSuccess) throw new EntityNotFoundException(_item.getId());
    }
}
