package com.example.todolistfx;

import Application.Exceptions.EntityValidationException;
import Application.Repositories.Impls.GenericRepositoryImpl;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultState;
import Application.Utils.ValidationDictionary;
import Domain.Entities.SimpleNote;
import Infrastructure.Notes.NotesService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private Button saveButton;

    @FXML
    private TextField titleInput;

    @FXML
    private TextArea textInput;

    private NotesService service;

    public HelloController(){
        service = new NotesService(new GenericRepositoryImpl<>());
    }

    @FXML
    private void initialize(){
        saveButton.setOnAction((event) -> {
            SimpleNote note = new SimpleNote(0, titleInput.getText(), textInput.getText(), new Date(), new Date());

            Result result = service.create(note);

            if (result.getState() == ResultState.FAIL){
                handleExceptionFromResult(result.getException());
                return;
            }

            showMessageBox("Success", "The message is added successfully", "");
        });
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
        }

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