package com.example.todolistfx.Notes;

import Application.Exceptions.EntityValidationException;
import Application.Results.Result;
import Application.Results.ResultState;
import Application.Utils.ValidationDictionary;
import Domain.Entities.SimpleNote;
import Infrastructure.Notes.NotesService;
import SimpleNotes.NotesJsonCommandImpl;
import SimpleNotes.NotesJsonQueryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;

public class SimpleNoteController {
    @FXML
    private Button saveButton;
    @FXML private TextField titleInput;
    @FXML private TextArea textInput;

    private SimpleNote selectedNote;
    private final NotesService service;

    public SimpleNoteController(){
        service = new NotesService(
                () -> new NotesJsonQueryImpl("D:\\Json\\Notes.json"),
                simpleNote -> new NotesJsonCommandImpl("D:\\Json\\Notes.json", simpleNote));
        System.out.println("Constructed");
    }

    @FXML
    private void initialize(){
        initializeButtons();
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

            Result result = note.getId() == 0
                    ? service.create(note)
                    : service.update(note);

            if (result.getState() == ResultState.FAIL){
                handleExceptionFromResult(result.getException());
                return;
            }

            showMessageBox("Success", getSuccessMessageFromNote(selectedNote), "");
        });
    }

    private String getSuccessMessageFromNote(SimpleNote selectedNote) {
        return selectedNote == null
                ? "The message is added successfully"
                : "The message is updated successfully";
    }

    private void handleExceptionFromResult(Exception exception) {
        if (exception instanceof EntityValidationException){
            StringBuilder builder = new StringBuilder();
            ValidationDictionary dictionary = ((EntityValidationException) exception).getValidationErrors();
            for (String key : dictionary.keySet()){
                builder.append(dictionary.get(key))
                        .append("\n");
            }

            showMessageBox("ValidationError"
                    , "You have some validation errors"
                    , builder.toString());

            return;
        }

        exception.printStackTrace();
    }

    private void showMessageBox(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }
}
