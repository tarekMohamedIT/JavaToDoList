package com.example.todolistfx;

import Application.Context.ApplicationCore;
import Infrastructure.Factories.JsonServicesFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("/com/example/todolistfx/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        initialize();
    }

    private void initialize() {
        ApplicationCore.setServices(new JsonServicesFactory("D:\\Json"));
    }

    public static void main(String[] args) {
        launch();
    }
}