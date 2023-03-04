package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.DbHandler;
import models.User;

public class RegistrationScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

        DbHandler dbHandler = new DbHandler();

        registerButton.setOnAction(event -> {
            dbHandler.RegistrateUser(new User(nameField.getText(), loginField.getText(), passwordField.getText()));
        });
    }

}
