package com.example.todolistfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseController {

    protected Scene scene;

    protected void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    public void close(ActionEvent event) {
        startTransition(this, (Stage) scene.getWindow(), "/UI/MainMenu/main_menu.fxml");
    }

    protected void startTransition(Object caller, Stage stage, String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(caller.getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            Object controller = loader.getController();
            setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
