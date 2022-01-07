package com.example.todolistfx.Notes;

import com.example.todolistfx.BaseController;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChecklistNoteController extends BaseController {

    public TextField titleInput;
    public ListView checkItemsList;
    public Button saveButton;

    public TextField itemTextInput;
    public CheckBox isDoneCheckbox;
    public Button saveItemButton;

    @Override
    protected void initialize() {

    }
}
