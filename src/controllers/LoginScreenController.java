package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreenController {

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
            loginScreenRegisterButton.getScene().getWindow().hide();
            try {
                openRegistrationScreen(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void openRegistrationScreen(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegistrationScreen.fxml"));
        Parent root = loader.load();
        // RegistrationScreenController registrationScreenController = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Registration Screen");
        stage.show();
    }

}