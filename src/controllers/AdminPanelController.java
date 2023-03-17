package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class AdminPanelController extends ControllerBase{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button newCandidate;

    @FXML
    private Button newVoting;

    @FXML
    private ListView<?> votingList;

    @FXML
    void initialize() {
        // newCandidate.setOnAction(event -> {
        //     openNewScene(newCandidate, null);
        // });

    }

}
