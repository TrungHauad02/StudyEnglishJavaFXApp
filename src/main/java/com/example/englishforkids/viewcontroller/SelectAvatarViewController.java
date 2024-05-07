package com.example.englishforkids.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SelectAvatarViewController {
    @FXML
    ImageView imgAvatar1;
    @FXML
    ImageView imgAvatar2;
    @FXML
    ImageView imgAvatar3;
    @FXML
    ImageView imgAvatar4;
    @FXML
    ImageView imgAvatar5;
    @FXML
    ImageView imgAvatar6;
    @FXML
    ImageView imgAvatar7;
    @FXML
    ImageView imgAvatar8;
    @FXML
    ImageView imgAvatar9;
    private String selectedAvatarFileName;
    public void initialize() {
        imgAvatar1.setOnMouseClicked(event -> selectAvatar("avatar_1.png"));
        imgAvatar2.setOnMouseClicked(event -> selectAvatar("avatar_2.png"));
        imgAvatar3.setOnMouseClicked(event -> selectAvatar("avatar_3.png"));
        imgAvatar4.setOnMouseClicked(event -> selectAvatar("avatar_4.png"));
        imgAvatar5.setOnMouseClicked(event -> selectAvatar("avatar_5.png"));
        imgAvatar6.setOnMouseClicked(event -> selectAvatar("avatar_6.png"));
        imgAvatar7.setOnMouseClicked(event -> selectAvatar("avatar_7.png"));
        imgAvatar8.setOnMouseClicked(event -> selectAvatar("avatar_8.png"));
        imgAvatar9.setOnMouseClicked(event -> selectAvatar("avatar_9.png"));
    }
    private void selectAvatar(String avatarFileName) {
        selectedAvatarFileName = avatarFileName;
        Stage stage = (Stage) imgAvatar1.getScene().getWindow();
        stage.close();
    }

    public String getSelectedAvatarFileName() {
        return selectedAvatarFileName;
    }
}
