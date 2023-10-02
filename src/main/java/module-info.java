module com.example.gestion_adherents_ap {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.example.gestion_adherents_ap to javafx.fxml;
    exports com.example.gestion_adherents_ap;
}