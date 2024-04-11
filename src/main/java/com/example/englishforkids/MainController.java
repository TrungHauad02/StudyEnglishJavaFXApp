package com.example.englishforkids;

import com.example.englishforkids.dao.RememberLoginDAO;
import com.example.englishforkids.dao.UserDAO;
import com.example.englishforkids.model.Account;
import com.example.englishforkids.model.RememberLogin;
import com.example.englishforkids.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainController {
    public static User curUser;
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
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String ipAddress = localHost.getHostAddress();
        RememberLoginDAO rememberLoginDAO = new RememberLoginDAO();
        RememberLogin rememberLogin = rememberLoginDAO.selectById(ipAddress);
        if(rememberLogin != null){
            String idAccount = rememberLogin.getIdAccount();
            UserDAO userDAO = new UserDAO();
            MainController.curUser = userDAO.selectByIdAccount(idAccount);

            MessageBox.show("Notify","Login Successfully", Alert.AlertType.CONFIRMATION);
            ShowNewScene.close(event);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/englishforkids/list_lesson_view.fxml"));
            ShowNewScene.show(loader, "List lesson");
            return;
        }

        ShowNewScene.close(event);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/englishforkids/login_view.fxml"));
        ShowNewScene.show(loader, "Login");
    }
}