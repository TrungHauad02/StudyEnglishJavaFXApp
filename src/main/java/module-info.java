module com.example.englishforkids {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mail;
    requires mysql.connector.j;
    requires java.datatransfer;
    requires java.desktop;

    opens com.example.englishforkids to javafx.fxml;
    exports com.example.englishforkids;
}