package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Const;
import models.User;

public class RegistrationScreenController extends ControllerBase {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    void initialize() {
        registerButton.setOnAction(event -> {
            signUpUser();
        });

        goBackButton.setOnAction(event -> {
            switchScene(goBackButton, Const.LOGIN_SCREEN);
        });
    }

    private void signUpUser() {
        if (!nameField.getText().isEmpty() && !loginField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            DAO.insertUser(new User(nameField.getText(), loginField.getText(), passwordField.getText()));
            switchScene(registerButton, Const.HOMEPAGE);
        }
        else {
            System.out.println("Some fields are empty");
        }
    }

}
