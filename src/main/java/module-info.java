module com.app.adherents.gestion_adherents {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.app.adherents.gestion_adherents to javafx.fxml;
    exports com.app.adherents.gestion_adherents;
}