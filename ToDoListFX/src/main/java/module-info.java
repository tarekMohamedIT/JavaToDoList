module com.example.todolistfx {
    requires Domain;
    requires Application;
    requires Infrastructure;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.example.todolistfx to javafx.fxml;
    exports com.example.todolistfx;
}