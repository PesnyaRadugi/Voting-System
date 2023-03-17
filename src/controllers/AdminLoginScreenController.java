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
import models.Admin;
import models.Const;

public class AdminLoginScreenController extends ControllerBase {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
        loginButton.setOnAction(event -> {
            loginAdmin(event);
        });
    }

    private void loginAdmin(ActionEvent event) {
        String loginText = loginField.getText().trim();
        String loginPassword = passwordField.getText();

        if (!loginText.isEmpty() && !loginPassword.isEmpty()) {
            Admin admin = new Admin();
            admin.setLogin(loginText);
            admin.setPassword(loginPassword);
            ResultSet resultSet = DAO.selectAdmin(admin);

            try {
                if (resultSet.next()) {
                    switchScene(loginButton, Const.ADMIN_PANEL);
                    DAO.closeConnection();
                }
                else {
                    System.out.println("Kekw");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Aboba");
        }
    }

}
