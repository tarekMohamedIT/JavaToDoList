package com.example.todolistfx;

import Application.Exceptions.EntityValidationException;
import Application.Results.ObjectResult;
import Application.Results.Result;
import Application.Results.ResultState;
import Application.Utils.ValidationDictionary;
import Domain.Entities.SimpleNote;
import Infrastructure.Notes.NotesCommandMemoryImpl;
import Infrastructure.Notes.NotesQueryMemoryImpl;
import Infrastructure.Notes.NotesService;
import SimpleNotes.NotesJsonCommandImpl;
import SimpleNotes.NotesJsonQueryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloController {
    @FXML private Button saveButton;
    @FXML private Button addNewButton;
    @FXML private TextField titleInput;
    @FXML private TextArea textInput;
    @FXML private ListView<SimpleNote> notesList;

    private SimpleNote selectedNote;
    private final List<SimpleNote> notesArrayList;
    private final NotesService service;

    public HelloController(){
        notesArrayList = new ArrayList<>();
        service = new NotesService(
                () -> new NotesJsonQueryImpl("D:\\Json\\Notes.json"),
                simpleNote -> new NotesJsonCommandImpl("D:\\Json\\Notes.json", simpleNote));
        System.out.println("Constructed");
    }

    @FXML
    private void initialize(){
        initializeButtons();
        initializeList();
    }

    private void initializeList() {
        notesList.setCellFactory(simpleNoteListView -> {
            ListCell<SimpleNote> cell = new ListCell<>() {
                @Override
                protected void updateItem(SimpleNote item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {

                        Label label = new Label(item.getTitle());
                        setGraphic(label);
                    }
                }
            };

            cell.setOnMousePressed(arg0 -> setNoteToView(notesList.getSelectionModel().getSelectedIndex()));

            return cell;
        });

        ObjectResult<List<SimpleNote>> fetchAllResult = service.getAll();
        if (fetchAllResult.getState() == ResultState.SUCCESS) {
            notesList.setItems(FXCollections.observableList(fetchAllResult.getObject()));
        }
        else {
            fetchAllResult.getException().printStackTrace();
        }
    }

    private void setNoteToView(int selectedIndex) {
        if (selectedIndex == -1) return;
        selectedNote = notesList.getSelectionModel().getSelectedItem();

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
            if (selectedNote == null) notesList.getItems().add(note);
            else notesList.getItems().set(notesList.getSelectionModel().getSelectedIndex(), note);
        });

        addNewButton.setOnAction((event) -> {
            SimpleNote note = new SimpleNote(
                    0,
                    titleInput.getText(),
                    textInput.getText(),
                    new Date(),
                    new Date());

            Result result = service.create(note);

            if (result.getState() == ResultState.FAIL){
                handleExceptionFromResult(result.getException());
                return;
            }

            showMessageBox("Success", getSuccessMessageFromNote(null), "");
            notesList.getItems().add(note);
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