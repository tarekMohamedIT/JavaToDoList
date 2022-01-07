package com.example.todolistfx.Notes;

import Application.Results.Result;
import Application.Results.ResultState;
import Domain.Entities.SimpleNote;
import Infrastructure.Notes.NotesService;
import SimpleNotes.NotesJsonCommandImpl;
import SimpleNotes.NotesJsonQueryImpl;
import com.example.todolistfx.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;

public class SimpleNoteController extends BaseController {
    @FXML
    private Button saveButton;
    @FXML private TextField titleInput;
    @FXML private TextArea textInput;

    private SimpleNote selectedNote;
    private boolean isUpdating = false;
    private final NotesService service;

    public SimpleNoteController(){
        service = new NotesService(
                () -> new NotesJsonQueryImpl("D:\\Json\\Notes.json"),
                simpleNote -> new NotesJsonCommandImpl("D:\\Json\\Notes.json", simpleNote));
        System.out.println("Constructed");
    }

    @FXML
    @Override
    protected void initialize(){
        Platform.runLater(() -> {
            initializeInputs();
            initializeButtons();
        });
    }

    private void initializeInputs() {
        if (selectedNote == null) return;

        titleInput.setText(selectedNote.getTitle());
        textInput.setText(selectedNote.getText());
    }

    private void setNoteToView(int selectedIndex) {
        if (selectedIndex == -1) return;

        titleInput.setText(selectedNote.getTitle());
        textInput.setText(selectedNote.getText());
    }

    private void initializeButtons() {
        saveButton.setOnAction((event) -> {
            SimpleNote note = new SimpleNote(
                    selectedNote == null ? 0 : selectedNote.getId(),
                    titleInput.getText(),
                    textInput.getText(),
                    new Date(),
                    new Date());

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

    public void setSelectedNote(SimpleNote selectedNote) {
        this.selectedNote = selectedNote;
        isUpdating = selectedNote != null;
    }
}
