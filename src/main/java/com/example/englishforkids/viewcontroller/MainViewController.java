package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.GetResourceController;
import com.example.englishforkids.dao.RememberLoginDAO;
import com.example.englishforkids.feature.*;
import com.example.englishforkids.model.RememberLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

public class MainViewController implements PaneChangeListener {
    @FXML
    Pane paneMain;
    @FXML
    Button btnLogout;
    @FXML
    Button btnListVocabulary;
    @FXML
    Button btnListLesson;
    public void initialize() {
        loadPane(GetResourceController.getFXMLResourcePath("list_lesson_view.fxml"));

        btnListLesson.setOnAction(event -> {
            loadPane(GetResourceController.getFXMLResourcePath("list_lesson_view.fxml"));
        });
        btnListVocabulary.setOnAction(event -> {
            loadPane(GetResourceController.getFXMLResourcePath("list_vocabulary_view.fxml"));
        });

        btnLogout.setOnAction(event -> {
            String macAddress = MacAddress.getMacAddress();
            String idAccount = CurrentUser.getInstance().getCurrentUser().getIdAccount();
            RememberLogin rememberLogin = new RememberLogin(idAccount, macAddress);
            RememberLoginDAO rememberLoginDAO = new RememberLoginDAO();
            rememberLoginDAO.delete(rememberLogin);
            CurrentUser.getInstance().setCurrentUser(null);
            ShowNewScene.close(event);
            FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("login_view.fxml"));
            ShowNewScene.show(loader,"Đăng nhập");
        });
    }

    private void loadPane(URL urlPane){
        try {
            FXMLLoader loader = new FXMLLoader(urlPane);
            Pane pane = loader.load();
            ChangeMainPane controller = loader.getController();
            controller.setMainViewController(this);
            paneMain.getChildren().clear();
            paneMain.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createPaneUsername(Label lblUsername, Pane paneUsername, ImageView imgAvatar) {
        lblUsername.setText(CurrentUser.getInstance().getCurrentUser().getFullname());
        paneUsername.setMaxWidth(Double.MAX_VALUE);
        Text text = new Text(lblUsername.getText());
        text.setFont(lblUsername.getFont());
        double minWidth = text.getBoundsInLocal().getWidth();
        paneUsername.setPrefWidth(minWidth + 110);
        String avatarURL = "avatar/" + CurrentUser.getInstance().getCurrentUser().getAvatar();
        Image image = new Image(String.valueOf(GetResourceController.getFXMLResourcePath(avatarURL)));
        imgAvatar.setImage(image);
    }
    @Override
    public void onPaneChange(Pane newPane) {
        paneMain.getChildren().clear();
        paneMain.getChildren().add(newPane);
    }
}
