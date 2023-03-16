package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Const;
import models.DbHandler;
import models.User;

public class LoginScreenController extends ControllerBase {

    @FXML
    private Button adminScreenButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private Button loginScreenRegisterButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void initialize() {
        loginScreenRegisterButton.setOnAction(event -> {
            try {
                switchScene(loginScreenRegisterButton, Const.REGISTRATION_SCREEN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        loginButton.setOnAction(event -> {
            loginUser(event);
        });

        adminScreenButton.setOnAction(event -> {
            try {
                switchScene(adminScreenButton, Const.ADMIN_LOGIN_SCREEN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void loginUser(ActionEvent event) {
        String loginText = loginField.getText().trim();
        String loginPassword = passwordField.getText();
        
        if (!loginText.isEmpty() && !loginPassword.isEmpty()) {
            DbHandler dbHandler = new DbHandler();
            User user = new User();
            user.setLogin(loginText);
            user.setPassword(loginPassword);
            ResultSet resultset = dbHandler.getUser(user);

            try {
                if (resultset.next()) {
                    System.out.println("HUGE SUCCES");
                    switchScene(loginButton, Const.HOMEPAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Fields are empty");
        }

    }
}