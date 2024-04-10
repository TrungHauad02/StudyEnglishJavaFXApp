package com.example.englishforkids;

import com.example.englishforkids.model.Account;
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
        String fullName = txtFullName.getText().trim();
        String grade = txtGrade.getText().trim();
        String school = txtSchool.getText().trim();
        String email = txtEmail.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        if (fullName.isEmpty() || grade.isEmpty() || school.isEmpty() || email.isEmpty() ||username.isEmpty() || password.isEmpty()) {
            MessageBox.show("Error","Username and password cannot null", Alert.AlertType.ERROR);
        }
        paneSignUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Account account = new Account();

        });
    }
}
