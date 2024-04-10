package com.example.englishforkids;

import com.example.englishforkids.dao.AccountDAO;
import com.example.englishforkids.dao.RememberLoginDAO;
import com.example.englishforkids.dao.UserDAO;
import com.example.englishforkids.model.Account;
import com.example.englishforkids.model.RememberLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.InetAddress;

import java.io.InputStream;
import java.net.UnknownHostException;

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
    private CheckBox chkRememberLogin;
    @FXML
    private Button btnSignUp;
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws UnknownHostException {
        AccountDAO accountDAO = new AccountDAO();
        UserDAO userDAO = new UserDAO();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if (username.isEmpty() || password.isEmpty())
            MessageBox.show("Error", "Username and passsword cannot null", Alert.AlertType.ERROR);

        Account curAccount = accountDAO.login(username, password);
        if(curAccount == null)
            MessageBox.show("Error", "Username or password is wrong", Alert.AlertType.ERROR);
        MainController.curUser = userDAO.selectByIdAccount(curAccount.getIdAccount());

        if(MainController.curUser != null && chkRememberLogin.isSelected()){
            InetAddress localHost = InetAddress.getLocalHost();
            String ip = localHost.getHostAddress();
            String idAccount = curAccount.getIdAccount();
            RememberLogin rememberLogin = new RememberLogin(idAccount, ip);
            RememberLoginDAO rememberLoginDAO = new RememberLoginDAO();
            rememberLoginDAO.insert(rememberLogin);
        }

        MessageBox.show("Notify", "Login Successfully", Alert.AlertType.CONFIRMATION);
    }
    public void initialize() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/img/login_bg.png");
            Image backgroundImage = new Image(inputStream);
            backgroundImageView.setImage(backgroundImage);

            btnSignUp.setOnAction(e->{

            });
        }catch (Exception ex){

        }
    }
}
