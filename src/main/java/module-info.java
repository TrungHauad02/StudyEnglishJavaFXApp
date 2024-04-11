module com.example.englishforkids {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mail;

    opens com.example.englishforkids to javafx.fxml;
    exports com.example.englishforkids;
}