package com.example.englishforkids;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ForgetPasswordViewController {
    @FXML
    TextField txtEmail;
    @FXML
    TextField txtOTP;
    @FXML
    PasswordField txtPassword;
    @FXML
    Button btnSendOTP;
    @FXML
    Button btnConfirm;
    @FXML
    Button btnUpdate;

    public void initialize(){
        btnSendOTP.setOnAction(e->{
            String email = txtEmail.getText().trim();
            if(email.isEmpty()){
               MessageBox.show("Lỗi", "Email không được bỏ trống", Alert.AlertType.ERROR);
               return;
            }
            if(sendOTP(email)){

            }

        });
    }

    private boolean sendOTP(String email){
        return true;
    }
}
