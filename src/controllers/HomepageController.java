package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class HomepageController extends ControllerBase {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem exitButton;

    @FXML
    private MenuItem profileButton;

    @FXML
    void initialize() {
        exitButton.setOnAction(event -> {
            switchScene(menuButton, "/views/LoginScreen.fxml");
            System.out.println("cock");
        });
        
    }

}