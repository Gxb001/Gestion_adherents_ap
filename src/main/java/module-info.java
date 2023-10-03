module com.app.adherents.gestion_adherents {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.app.adherents.gestion_adherents to javafx.fxml;
    exports com.app.adherents.gestion_adherents;
}