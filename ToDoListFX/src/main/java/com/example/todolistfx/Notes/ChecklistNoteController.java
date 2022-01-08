package com.example.todolistfx.Notes;

import Application.Results.Result;
import Application.Results.ResultState;
import Domain.Entities.ChecklistItem;
import Domain.Entities.ChecklistNote;
import Domain.Entities.SimpleNote;
import Infrastructure.ChecklisNotes.ChecklistCommandMemoryImpl;
import Infrastructure.ChecklisNotes.ChecklistQueryMemoryImpl;
import Infrastructure.ChecklisNotes.ChecklistsService;
import com.example.todolistfx.BaseController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChecklistNoteController extends BaseController {

    public TextField titleInput;
    public ListView<ChecklistItem> checkItemsList;
    public Button saveButton;

    public TextField itemTextInput;
    public CheckBox isDoneCheckbox;
    public Button saveItemButton;
    public Button addNewButton;

    private final ChecklistsService service;
    private ChecklistNote selectedNote;
    private ChecklistItem selectedItem;
    private boolean isUpdating = false;

    public ChecklistNoteController() {
        List<ChecklistNote> notesList = new ArrayList<>();
        service = new ChecklistsService(
                () -> new ChecklistQueryMemoryImpl(notesList),
                simpleNote -> new ChecklistCommandMemoryImpl(notesList, simpleNote));
        System.out.println("Constructed");
    }

    public void setSelectedNote(ChecklistNote selectedNote) {
        this.selectedNote = selectedNote;
        isUpdating = true;
    }

    @Override
    protected void initialize() {
        Platform.runLater(()-> {
            initializeList();
            initializeButtons();
            initializeInputs();
        });
    }

    private void initializeButtons() {
        saveItemButton.setOnAction(event -> {
            ChecklistItem item = checkItemsList.getSelectionModel().getSelectedItem();

            ChecklistItem updatedItem = new ChecklistItem();
            updatedItem.setText(itemTextInput.getText());
            updatedItem.setChecked(isDoneCheckbox.isSelected());

            if (item == null) {
                checkItemsList.getItems().add(updatedItem);
                return;
            }

            item.setText(updatedItem.getText());
            item.setChecked(updatedItem.isChecked());

            checkItemsList.getItems().notify();
        });

        saveButton.setOnAction(event -> {
            ChecklistNote note = new ChecklistNote(
                    selectedNote == null ? 0 : selectedNote.getId(),
                    titleInput.getText(),
                    new ArrayList<>(checkItemsList.getItems()));

            Result result = !isUpdating
                    ? service.create(note)
                    : service.update(note);

            if (result.getState() == ResultState.FAIL){
                handleExceptionFromResult(result.getException());
                return;
            }

            showMessageBox("Success", getSuccessMessageFromNote(), "");

            close((Stage)saveButton.getScene().getWindow());
        });
    }

    private String getSuccessMessageFromNote() {
        return isUpdating
                ? "The message is added successfully"
                : "The message is updated successfully";
    }

    private void initializeList() {
        checkItemsList.setCellFactory(simpleNoteListView -> {
            ListCell<ChecklistItem> cell = new ListCell<>() {
                @Override
                protected void updateItem(ChecklistItem item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {

                        Label label = new Label(item.getText());
                        setGraphic(label);
                    }
                }
            };

            cell.setOnMousePressed(
                    arg0 -> setNoteToView(checkItemsList.getSelectionModel().getSelectedIndex()));

            return cell;
        });

        if (selectedNote == null) return;
        checkItemsList.setItems(FXCollections.observableList(selectedNote.getItems()));
    }
    private void setNoteToView(int selectedIndex) {
        if (selectedIndex == -1) return;

        ChecklistItem item = selectedNote.getItems().get(selectedIndex);
        titleInput.setText(selectedNote.getTitle());
        itemTextInput.setText(item.getText());
        isDoneCheckbox.setSelected(item.isChecked());

        selectedItem = item;
    }

    private void initializeInputs(){
        if (selectedNote == null) return;
        titleInput.setText(selectedNote.getTitle());
    }
}
