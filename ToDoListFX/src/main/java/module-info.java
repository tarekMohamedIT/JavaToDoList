module com.example.todolistfx {
    requires Domain;
    requires Application;
    requires Infrastructure;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.example.todolistfx to javafx.fxml;
    opens com.example.todolistfx.MainMenu to javafx.fxml;
    opens com.example.todolistfx.Notes to javafx.fxml;

    exports com.example.todolistfx;
    exports com.example.todolistfx.MainMenu;
    exports com.example.todolistfx.Notes;
}