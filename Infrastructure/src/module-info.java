module Infrastructure {
    requires com.google.gson;

    exports Infrastructure.Notes;
    exports Infrastructure.ChecklisNotes;
    exports Infrastructure.Factories;
    requires Domain;
    requires Application;
}