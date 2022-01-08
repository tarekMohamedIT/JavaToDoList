package com.example.todolistfx.MainMenu;

import Application.Results.ObjectResult;
import Application.Results.ResultState;
import Domain.Entities.SimpleNote;
import Infrastructure.Notes.NotesService;
import Infrastructure.Notes.NotesJsonCommandImpl;
import Infrastructure.Notes.NotesJsonQueryImpl;
import com.example.todolistfx.BaseController;
import com.example.todolistfx.Notes.SimpleNoteController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HomeController extends BaseController {
    public ComboBox<String> NotesChoicesComboBox;
    @FXML private Button addNewButton;
    @FXML private ListView<SimpleNote> notesList;

    private SimpleNote selectedNote;
    private final NotesService service;

    public HomeController(){
        service = new NotesService(
                () -> new NotesJsonQueryImpl("D:\\Json\\Notes.json"),
                simpleNote -> new NotesJsonCommandImpl("D:\\Json\\Notes.json", simpleNote));
        System.out.println("Constructed");
    }

    @FXML
    @Override
    protected void initialize(){
        Platform.runLater(() -> {
            initializeButtons();
            initializeList();
            initializeComboBoxes();
        });
    }

    private void initializeComboBoxes() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Simple");
        choices.add("Checklist");

        NotesChoicesComboBox.setItems(FXCollections.observableList(choices));
    }

    private void initializeButtons() {

        addNewButton.setOnAction(
                event -> {
                    String path = "/com/example/todolistfx/simple-note.fxml";
                    if (NotesChoicesComboBox.getSelectionModel().getSelectedItem().equals("Checklist"))
                        path = "/com/example/todolistfx/checklist-note.fxml";

                    startTransition(
                            this,
                            (Stage) addNewButton.getScene().getWindow(),
                            path);
                });
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

            cell.setOnMousePressed(
                    arg0 -> setNoteToView(notesList.getSelectionModel().getSelectedIndex()));

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

        this.<SimpleNoteController>startControllerTransition(
                this,
                (Stage) addNewButton.getScene().getWindow(),
                "/com/example/todolistfx/simple-note.fxml",
                controller -> {
                    controller.setSelectedNote(notesList.getSelectionModel().getSelectedItem());
                    return controller;
                });
    }
}