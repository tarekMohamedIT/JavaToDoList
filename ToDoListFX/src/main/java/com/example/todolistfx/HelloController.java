package com.example.todolistfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Button saveButton;

    public HelloController(){

    }

    @FXML
    private void initialize(){
        saveButton.setOnAction((event) -> {
            System.out.println("Clicked the button");
        });
    }
}