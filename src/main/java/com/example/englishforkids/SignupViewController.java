package com.example.englishforkids;

import com.example.englishforkids.dao.AccountDAO;
import com.example.englishforkids.dao.UserDAO;
import com.example.englishforkids.model.Account;
import com.example.englishforkids.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SignupViewController {
    @FXML
    TextField txtFullName;
    @FXML
    TextField txtGrade;
    @FXML
    DatePicker dtpBirth;
    @FXML
    TextField txtSchool;
    @FXML
    TextField txtEmail;
    @FXML
    TextField txtAddress;
    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    Pane paneSignUp;
    @FXML
    Pane paneMain;

    public void initialize(){
        paneSignUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            signUp();
        });
    }

    private void signUp(){
        String fullName = txtFullName.getText().trim();
        String grade = txtGrade.getText().trim();
        String school = txtSchool.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        LocalDate birthDate = dtpBirth.getValue();
        if (fullName.isEmpty() || grade.isEmpty() || school.isEmpty() || email.isEmpty()
                || username.isEmpty() || password.isEmpty() || birthDate == null || address.isEmpty()) {
            MessageBox.show("Lỗi","Hãy điền đầy đủ thông tin", Alert.AlertType.ERROR);
            return;
        }
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole(String.valueOf(Account.Role.STUDENT));

        AccountDAO accountDAO = new AccountDAO();
        if(accountDAO.insert(account)){
            User user = new User();
            user.setFullname(fullName);
            user.setGrade(grade);
            user.setSchool(school);
            user.setEmailParent(email);
            user.setScore(0);
            user.setStatus(true);
            user.setAddress(address);
            user.setBirthday(Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            UserDAO userDAO = new UserDAO();
            if(userDAO.insert(user)){
                MessageBox.show("Thành công","Tài khoản đã được đăng ký thành công", Alert.AlertType.CONFIRMATION);
                showLoginForm();
            }else {
                MessageBox.show("Lỗi","Không thêm được người dùng", Alert.AlertType.ERROR);
            }
        }else {
            MessageBox.show("Lỗi","Tài khoản đã tồn tại", Alert.AlertType.ERROR);
        }
    }

    private void showLoginForm(){
        Stage stage = (Stage) paneMain.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/englishforkids/login_view.fxml"));
        ShowNewScene.show(loader, "Login");
    }

}
