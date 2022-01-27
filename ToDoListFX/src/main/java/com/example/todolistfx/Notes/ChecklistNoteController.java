package com.example.todolistfx.Notes;

import Application.Context.ApplicationCore;
import Application.Results.Result;
import Application.Results.ResultState;
import Application.Services.ChecklistsService;
import Domain.Entities.ChecklistItem;
import Domain.Entities.ChecklistNote;
import com.example.todolistfx.BaseController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
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
    private boolean isUpdating = false;

    public ChecklistNoteController() {
        service = ApplicationCore.getServices().createChecklists();
        System.out.println("Constructed");

        selectedNote = new ChecklistNote(0, "", new ArrayList<>());
    }

    public void setSelectedNote(ChecklistNote selectedNote) {
        this.selectedNote = selectedNote;
        isUpdating = selectedNote != null;
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

            List<ChecklistItem> currentItems = new ArrayList<>(checkItemsList.getItems());
            checkItemsList.setItems(FXCollections.observableList(currentItems));
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
                ? "The message is updated successfully"
                : "The message is added successfully";
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
    }

    private void initializeInputs(){
        if (selectedNote == null) return;
        titleInput.setText(selectedNote.getTitle());
    }
}
