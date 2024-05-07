package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.GetResourceController;
import com.example.englishforkids.dao.RememberLoginDAO;
import com.example.englishforkids.feature.CurrentUser;
import com.example.englishforkids.feature.DataUpdateListener;
import com.example.englishforkids.feature.MacAddress;
import com.example.englishforkids.feature.ShowNewScene;
import com.example.englishforkids.model.RememberLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class MainViewController {
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
            ShowNewScene.show(loader,"Login");
        });
    }

    private void loadPane(URL urlPane){
        try {
            FXMLLoader loader = new FXMLLoader(urlPane);
            Pane pane = loader.load();
            paneMain.getChildren().clear();
            paneMain.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
