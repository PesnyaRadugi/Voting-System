package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class VotingSystem extends Application {

    public static final VotingSystem VOTING_SYSTEM = new VotingSystem();
    private Voting currentVoting;
    private User currentUser;


    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Const.LOGIN_SCREEN));
        Scene scene = new Scene(root);
        
        stage.setTitle("Voting System");
        stage.setScene(scene);
        stage.show();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentVoting(Voting currentVoting) {
        this.currentVoting = currentVoting;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Voting getCurrentVoting() {
        return currentVoting;
    }

}