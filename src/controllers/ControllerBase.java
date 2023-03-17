package controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.MySQL;
import models.SQL;

public abstract class ControllerBase {

    protected SQL DAO = new MySQL();

    // Opens a new window on top of previous
    protected void openNewScene(Button button, String window) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource(window));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Switches scenes without opening new one
    protected void switchScene(Node node, String window) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(window));
            Stage stage = (Stage)node.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
