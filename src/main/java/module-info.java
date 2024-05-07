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
    requires gax;
    requires com.google.auth.oauth2;
    requires google.cloud.speech;
    requires protobuf.java;

    opens com.example.englishforkids to javafx.fxml;
    opens com.example.englishforkids.model;
    exports com.example.englishforkids;
    exports com.example.englishforkids.viewcontroller;
    opens com.example.englishforkids.viewcontroller to javafx.fxml;
    exports com.example.englishforkids.feature;
    opens com.example.englishforkids.feature to javafx.fxml;
}