package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class VotingSytem extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Const.ADMIN_PANEL));
        Scene scene = new Scene(root);
        
        stage.setTitle("Voting System");
        stage.setScene(scene);
        stage.show();
    }
}