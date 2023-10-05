module com.app.adherents.gestion_adherents {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires com.fasterxml.jackson.databind;
    requires json.simple;


    opens com.app.adherents.gestion_adherents to javafx.fxml;
    exports com.app.adherents.gestion_adherents;
}