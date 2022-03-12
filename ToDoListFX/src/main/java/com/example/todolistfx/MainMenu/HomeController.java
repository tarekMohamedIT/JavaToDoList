package com.example.todolistfx.MainMenu;

import Application.Context.ApplicationCore;
import Application.Factories.ServicesFactory;
import Application.Results.ObjectResult;
import Application.Results.ResultState;
import Application.Services.ChecklistsService;
import Application.Services.NotesService;
import Domain.Entities.ChecklistNote;
import Domain.Entities.NotesBase;
import Domain.Entities.SimpleNote;
import Domain.QueryObjects.SimpleNoteQuery;
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
        service = ApplicationCore.resolve(ServicesFactory.class)
                .createNotes();
        checklistService = ApplicationCore.resolve(ServicesFactory.class)
                .createChecklists();

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
                    if (NotesChoicesComboBox.getSelectionModel().getSelectedItem() == null)
                    {
                        showMessageBox("No type is selected", "You must select a type of notes", "");
                        return;
                    }

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

        notesList.setItems(FXCollections.observableList(new ArrayList<>()));

        addItemsToNotesList(service.getAll(null));
        addItemsToNotesList(checklistService.getAll(null));
    }

    private <T extends NotesBase>void addItemsToNotesList(ObjectResult<List<T>> notesListResult){
        if (notesListResult.getState() == ResultState.SUCCESS) {
            notesList.getItems().addAll(notesListResult.getObject());
        }
        else {
            notesListResult.getException().printStackTrace();
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