package com.example.englishforkids;

import com.example.englishforkids.model.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("first_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 570);
        stage.setTitle("English for kids");
        stage.setScene(scene);
        String imagePath = "/img/main_ico.png";
        Image icon = new Image(getClass().getResource(imagePath).toExternalForm());
        stage.getIcons().add(icon);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}