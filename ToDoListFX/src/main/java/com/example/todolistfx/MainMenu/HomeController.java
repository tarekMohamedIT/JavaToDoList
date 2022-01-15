package com.example.todolistfx.MainMenu;

import Application.Results.ObjectResult;
import Application.Results.ResultState;
import Application.Services.ChecklistsService;
import Domain.Entities.ChecklistNote;
import Domain.Entities.NotesBase;
import Domain.Entities.SimpleNote;
import Application.Services.NotesService;
import Infrastructure.ChecklisNotes.ChecklistCommandMemoryImpl;
import Infrastructure.ChecklisNotes.ChecklistQueryMemoryImpl;
import Infrastructure.Notes.NotesJsonCommandImpl;
import Infrastructure.Notes.NotesJsonQueryImpl;
import com.example.todolistfx.BaseController;
import com.example.todolistfx.Notes.ChecklistNoteController;
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
    @FXML private ListView<NotesBase> notesList;

    private final NotesService service;
    private final ChecklistsService checklistService;

    public HomeController(){
        service = new NotesService(
                () -> new NotesJsonQueryImpl("D:\\Json\\Notes.json"),
                simpleNote -> new NotesJsonCommandImpl("D:\\Json\\Notes.json", simpleNote));

        List<ChecklistNote> notes = new ArrayList<>();
        checklistService = new ChecklistsService(() -> new ChecklistQueryMemoryImpl(notes),
                checklistNote -> new ChecklistCommandMemoryImpl(notes, checklistNote));

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
            ListCell<NotesBase> cell = new ListCell<>() {
                @Override
                protected void updateItem(NotesBase item, boolean empty) {
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
        ObjectResult<List<ChecklistNote>> fetchAllChecklistResult = checklistService.getAll();
        notesList.setItems(FXCollections.observableList(new ArrayList<>()));

        if (fetchAllResult.getState() == ResultState.SUCCESS) {
            notesList.getItems().addAll(fetchAllResult.getObject());
        }
        else {
            fetchAllResult.getException().printStackTrace();
        }

        if (fetchAllChecklistResult.getState() == ResultState.SUCCESS) {
            notesList.getItems().addAll(fetchAllChecklistResult.getObject());
        }
        else {
            fetchAllChecklistResult.getException().printStackTrace();
        }
    }

    private void setNoteToView(int selectedIndex) {
        if (selectedIndex == -1) return;

        if (notesList.getSelectionModel().getSelectedItem() instanceof SimpleNote){
            this.<SimpleNoteController>startControllerTransition(
                    this,
                    (Stage) addNewButton.getScene().getWindow(),
                    "/com/example/todolistfx/simple-note.fxml",
                    controller -> {
                        controller.setSelectedNote((SimpleNote) notesList.getSelectionModel().getSelectedItem());
                        return controller;
                    });
            return;
        }

        if (notesList.getSelectionModel().getSelectedItem() instanceof ChecklistNote){
            this.<ChecklistNoteController>startControllerTransition(
                    this,
                    (Stage) addNewButton.getScene().getWindow(),
                    "/com/example/todolistfx/checklist-note.fxml",
                    controller -> {
                        controller.setSelectedNote((ChecklistNote) notesList.getSelectionModel().getSelectedItem());
                        return controller;
                    });
        }
    }
}