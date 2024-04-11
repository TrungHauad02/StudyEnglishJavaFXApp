package com.example.englishforkids;

import com.example.englishforkids.dao.AccountDAO;
import com.example.englishforkids.dao.UserDAO;
import com.example.englishforkids.model.Account;
import com.example.englishforkids.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.Random;

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

    private String otp;
    public void initialize(){
        btnConfirm.setDisable(true);
        btnUpdate.setDisable(true);
        btnSendOTP.setOnAction(e->{
            String email = txtEmail.getText().trim();
            if(email.isEmpty()){
               MessageBox.show("Lỗi", "Email không được bỏ trống", Alert.AlertType.ERROR);
               return;
            }
            if(sendOTP(email)){
                txtOTP.setEditable(true);
                btnConfirm.setDisable(false);
            }else {
                MessageBox.show("Lỗi", "Gửi email thất bại", Alert.AlertType.ERROR);
            }

        });
        btnConfirm.setOnAction(e->{
            String enterOTP = txtOTP.getText().trim();
            if(enterOTP.isEmpty()){
                MessageBox.show("Lỗi", "OTP không được bỏ trống", Alert.AlertType.ERROR);
                return;
            }
            if(enterOTP.equals(this.otp)){
                txtPassword.setEditable(true);
                btnUpdate.setDisable(false);
            }else {
                MessageBox.show("Lỗi", "Sai mã thất bại", Alert.AlertType.ERROR);
            }
        });

        btnUpdate.setOnAction(e -> {
            String password = txtPassword.getText().trim();
            if(password.isEmpty()){
                return;
            }
            UserDAO userDAO = new UserDAO();
            User user = userDAO.selectByEmail(txtEmail.getText().trim());
            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.selectById(user.getIdAccount());
            account.setPassword(password);
            accountDAO.update(account);

            ShowNewScene.close(e);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/englishforkids/login_view.fxml"));
            ShowNewScene.show(loader, "Login");
        });
    }

    private boolean sendOTP(String email){
        Random random = new Random();
        this.otp = String.valueOf(random.nextInt(999999 - 100000 + 1) + 100000);
        String host = "smtp.gmail.com";
        String port = "587";
        String userName = "trunghausender@gmail.com";
        String password = "dahk vatg lofa rqli";
        String subject = "Change password OTP";
        String message = "Do not share this otp "+ otp;

        try {
            EmailUtility.sendEmail(host, port, userName, password, email, subject, message);
            System.out.println("Email sent successfully!");
        } catch (AddressException e) {
            System.out.println("Invalid email address: " + e.getMessage());
            return false;
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            return false;
        }
        return true;
    }
}
