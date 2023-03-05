package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.DbHandler;
import models.User;

public class LoginScreenController extends ControllerBase {

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
                openNewScene(loginScreenRegisterButton, "/views/RegistrationScreen.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        loginButton.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String loginPassword = passwordField.getText().trim();

            if (!loginText.isEmpty() && !loginPassword.isEmpty()) {
                loginUser(loginText, loginPassword);
            }
            else {
                System.out.println("Fields are empty");
            }
        });
    }

    private void loginUser(String loginText, String loginPassword) {
        DbHandler dbHandler = new DbHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet resultset = dbHandler.getUser(user);

        try {
            if (resultset.next()) {
                System.out.println("HUGE SUCCES");
                openNewScene(loginButton, "/views/Homepage.fxml");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}