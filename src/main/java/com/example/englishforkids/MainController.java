package com.example.englishforkids;

import com.example.englishforkids.dao.RememberLoginDAO;
import com.example.englishforkids.dao.UserDAO;
import com.example.englishforkids.feature.CurrentUser;
import com.example.englishforkids.feature.MacAddress;
import com.example.englishforkids.feature.MessageBox;
import com.example.englishforkids.feature.ShowNewScene;
import com.example.englishforkids.model.RememberLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.InputStream;

public class MainController {
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private Button btnContinue;
    public void initialize() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/img/first_bg.png");
            Image backgroundImage = new Image(inputStream);
            backgroundImageView.setImage(backgroundImage);
            btnContinue.setManaged(true);
            StackPane.setMargin(btnContinue, new Insets(0, 10, 20, 0));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoginButtonClick(ActionEvent event){
        String macAddress = MacAddress.getMacAddress();
        System.out.println(macAddress);
        RememberLoginDAO rememberLoginDAO = new RememberLoginDAO();
        RememberLogin rememberLogin = rememberLoginDAO.selectById(macAddress);
        if(rememberLogin != null){
            String idAccount = rememberLogin.getIdAccount();
            UserDAO userDAO = new UserDAO();
            CurrentUser curUser = CurrentUser.getInstance();
            curUser.setCurrentUser(userDAO.selectByIdAccount(idAccount));

            MessageBox.show("Notify","Login Successfully", Alert.AlertType.CONFIRMATION);
            ShowNewScene.close(event);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/englishforkids/main_view.fxml"));
            ShowNewScene.show(loader, "List lesson");
            return;
        }

        ShowNewScene.close(event);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/englishforkids/login_view.fxml"));
        ShowNewScene.show(loader, "Login");
    }

}