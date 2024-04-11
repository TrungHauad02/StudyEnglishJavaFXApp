package com.example.englishforkids;

import com.example.englishforkids.dao.AccountDAO;
import com.example.englishforkids.model.Account;
import com.example.englishforkids.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    TextField txtUsername;
    @FXML
    TextField txtPassword;
    @FXML
    Pane paneSignUp;
    public void initialize(){
        paneSignUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            String fullName = txtFullName.getText().trim();
            String grade = txtGrade.getText().trim();
            String school = txtSchool.getText().trim();
            String email = txtEmail.getText().trim();
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();
            if (fullName.isEmpty() || grade.isEmpty() || school.isEmpty() || email.isEmpty() ||username.isEmpty() || password.isEmpty()) {
                MessageBox.show("Error","Username and password cannot null", Alert.AlertType.ERROR);
            }
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            account.setRole(String.valueOf(Account.Role.STUDENT));

            AccountDAO accountDAO = new AccountDAO();
            accountDAO.insert(account);

            User user = new User();
            user.setFullname(fullName);
        });
    }

}
