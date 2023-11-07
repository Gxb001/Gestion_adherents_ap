module com.app.adherents.gestion_adherents {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires com.fasterxml.jackson.databind;
    requires json.simple;
    requires kernel;
    requires layout;
    requires io;

    opens com.app.adherents.gestion_adherents to javafx.fxml;
    exports com.app.adherents.gestion_adherents;
    exports com.app.adherents.gestion_adherents.DataManip;
    opens com.app.adherents.gestion_adherents.DataManip to javafx.fxml;
    exports com.app.adherents.gestion_adherents.Class;
    opens com.app.adherents.gestion_adherents.Class to javafx.fxml;
}

