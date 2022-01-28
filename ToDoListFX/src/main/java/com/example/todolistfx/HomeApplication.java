package com.example.todolistfx;

import Application.Context.ApplicationCore;
import Application.PubSub.Publisher;
import Application.PubSub.Subscriber;
import Domain.Entities.Entity;
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

        Publisher.getInstance().subscribe("EntityAdded", new Subscriber() {
            @Override
            public void onMessage(Object item, Object source) {
                StringBuilder builder = new StringBuilder("Item Added");

                if (item instanceof Entity){
                    builder.append(((Entity) item).getId());
                }
                System.out.println(builder);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

        Publisher.getInstance().subscribe("EntityUpdated", new Subscriber() {
            @Override
            public void onMessage(Object item, Object source) {
                System.out.println("Item Updated");
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}