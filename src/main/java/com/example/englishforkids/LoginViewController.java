package com.example.englishforkids;

import com.example.englishforkids.dao.AccountDAO;
import com.example.englishforkids.dao.UserDAO;
import com.example.englishforkids.model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class LoginViewController {
    @FXML
    private TextField usernameField;
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button btnLogin;
    @FXML
    private void handleLoginButtonAction(ActionEvent event){
        AccountDAO accountDAO = new AccountDAO();
        UserDAO userDAO = new UserDAO();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username and password cannot null");
            alert.showAndWait();
            return;
        }
        Account curAccount = accountDAO.login(username, password);
        if(curAccount == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username or password is wrong");
            alert.showAndWait();
        }
        MainController.curUser = userDAO.selectByIdAccount(curAccount.getIdAccount());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notify");
        alert.setHeaderText(null);
        alert.setContentText("Login Successfully");
        alert.showAndWait();
    }
    public void initialize() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/img/login_bg.png");
            Image backgroundImage = new Image(inputStream);
            backgroundImageView.setImage(backgroundImage);
        }catch (Exception ex){

        }
    }
}
