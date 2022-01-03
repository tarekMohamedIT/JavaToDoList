package com.example.todolistfx;

import Application.Exceptions.EntityValidationException;
import Application.Utils.ParameterizedCallable;
import Application.Utils.ValidationDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public abstract class BaseController {

    protected Scene scene;

    protected void setScene(Scene scene) {
        this.scene = scene;
    }

    protected void close(Stage stage) {
        startTransition(this, stage, "/com/example/todolistfx/hello-view.fxml");
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

    protected <T>void startControllerTransition(Object caller
            , Stage stage
            , String fxmlPath
            , ParameterizedCallable<T, T> onInitializeCallback){
        try {
            FXMLLoader loader = new FXMLLoader(caller.getClass().getResource(fxmlPath));
            Parent root = loader.load();

            T controller = loader.getController();

            if (onInitializeCallback != null) onInitializeCallback.call(controller);

            Scene scene = new Scene(root);
            stage.setScene(scene);

            setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void handleExceptionFromResult(Exception exception) {
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

    protected void showMessageBox(String title, String headerText, String contentText){
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
