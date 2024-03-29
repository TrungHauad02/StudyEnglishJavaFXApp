package com.example.englishforkids;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainController {
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private Button btnContinue;
    @FXML
    private Label lblTitle;
    public void initialize() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/img/loginBG.png");
            Image backgroundImage = new Image(inputStream);
            backgroundImageView.setImage(backgroundImage);
            btnContinue.setManaged(true);
            StackPane.setMargin(btnContinue, new Insets(0, 10, 20, 0));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoginButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/englishforkids/login_view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}