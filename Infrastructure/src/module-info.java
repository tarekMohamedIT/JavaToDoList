module Infrastructure {
    requires com.google.gson;

    exports Infrastructure.Notes;
    exports Infrastructure.ChecklisNotes;
    requires Domain;
    requires Application;
}