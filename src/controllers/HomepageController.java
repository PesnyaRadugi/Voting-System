package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageController extends ControllerBase {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button sayHiButton;

    @FXML
    void initialize() {
        sayHiButton.setOnAction(event -> 
        {
            System.out.println("HEY VSAUCE, MICHAEL HERE!");
        });
    }

}