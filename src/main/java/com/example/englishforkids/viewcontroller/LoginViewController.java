package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.*;
import com.example.englishforkids.dao.AccountDAO;
import com.example.englishforkids.dao.RememberLoginDAO;
import com.example.englishforkids.dao.UserDAO;
import com.example.englishforkids.feature.CurrentUser;
import com.example.englishforkids.feature.MacAddress;
import com.example.englishforkids.feature.MessageBox;
import com.example.englishforkids.feature.ShowNewScene;
import com.example.englishforkids.model.Account;
import com.example.englishforkids.model.RememberLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private Button btnForgetPassword;
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws UnknownHostException {
        AccountDAO accountDAO = new AccountDAO();
        UserDAO userDAO = new UserDAO();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if (username.isEmpty() || password.isEmpty())
            MessageBox.show("Lỗi", "Tài khoản và mật khẩu không được bỏ trống", Alert.AlertType.ERROR);

        Account curAccount = accountDAO.login(username, password);
        if(curAccount == null)
            MessageBox.show("Lỗi", "Tài khoản và mật khẩu đã sai", Alert.AlertType.ERROR);
        CurrentUser curUser = CurrentUser.getInstance();
        curUser.setCurrentUser(userDAO.selectByIdAccount(curAccount.getIdAccount()));

        if(curUser.getCurrentUser() != null && chkRememberLogin.isSelected()){
            String macAddress = MacAddress.getMacAddress();
            String idAccount = curAccount.getIdAccount();
            RememberLogin rememberLogin = new RememberLogin(idAccount, macAddress);
            RememberLoginDAO rememberLoginDAO = new RememberLoginDAO();
            rememberLoginDAO.insert(rememberLogin);
        }

        MessageBox.show("Thành công", "Đăng nhập thành công.", Alert.AlertType.CONFIRMATION);
        ShowNewScene.close(event);
        FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("main_view.fxml"));
        ShowNewScene.show(loader, "English for kids");
    }
    public void initialize() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/img/login_bg.png");
            Image backgroundImage = new Image(inputStream);
            backgroundImageView.setImage(backgroundImage);

            btnSignUp.setOnAction(e->{
                ShowNewScene.close(e);
                FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("/com/example/englishforkids/signup_view.fxml"));
                ShowNewScene.show(loader, "Đăng ký");
            });

            btnForgetPassword.setOnAction(e -> {
                ShowNewScene.close(e);
                FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("/com/example/englishforkids/forget_password_view.fxml"));
                ShowNewScene.show(loader, "Quên mật khẩu");
            });

        }catch (Exception ex){

        }
    }
}
