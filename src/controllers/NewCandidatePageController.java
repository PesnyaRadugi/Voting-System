package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NewCandidatePageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createCandidateButton;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField nameField;

    @FXML
    void initialize() {
        assert createCandidateButton != null : "fx:id=\"createCandidateButton\" was not injected: check your FXML file 'NewCandidatePage.fxml'.";
        assert goBackButton != null : "fx:id=\"goBackButton\" was not injected: check your FXML file 'NewCandidatePage.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'NewCandidatePage.fxml'.";

    }

}
