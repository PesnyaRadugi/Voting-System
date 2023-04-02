package controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import models.Candidate;
import models.Const;
import models.Voting;
import models.VotingSytem;

public class HomepageController extends ControllerBase {

    @FXML
    private ListView<String> candidatesListView;

    @FXML
    private MenuItem exitButton;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem profileButton;

    @FXML
    private Button voteButton;

    @FXML
    private Button voteMenacinglyButton;

    @FXML
    private ListView<String> votingsListView;


    private Voting selectedVoting;
    private Candidate selectedCandidate;
    private List<Voting> votings = DAO.selectVotings();

    @FXML
    void initialize() {

        loadVotings();

        exitButton.setOnAction(event -> {
            switchScene(menuButton, Const.LOGIN_SCREEN);
            VotingSytem.currentUser = null;
        });

        votingsListView.setOnMouseClicked(event -> {
            selectedVoting = votings.get(votingsListView.getSelectionModel().getSelectedIndex());
            loadCandidates();
        });

        candidatesListView.setOnMouseClicked(even -> {
            if (!candidatesListView.getSelectionModel().isEmpty()) {
                selectedCandidate = selectedVoting.getCandidates().get(candidatesListView.getSelectionModel().getSelectedIndex());
                System.out.println(selectedCandidate.getName() + " voices: " + selectedCandidate.getVoices());
            }

            voteButton.disableProperty().bind(candidatesListView.getSelectionModel().selectedItemProperty().isNull());
            voteMenacinglyButton.disableProperty().bind(candidatesListView.getSelectionModel().selectedItemProperty().isNull());
        });
        
    }

    private void loadVotings() {
        for (Voting voting : votings) {
            votingsListView.getItems().add(voting.getTitle());
        }
    }

    private void loadCandidates() {
        
        List<String> candidatesNames = new ArrayList<>();

        if (selectedVoting != null) {
            for (Candidate candidate : selectedVoting.getCandidates()) {
                candidatesNames.add(candidate.getName());
            }
        }

        candidatesListView.getItems().setAll(candidatesNames);

        // for (Candidate candidate : DAO.selectAllCandidates()) {
        //     candidates.add(candidate.getName());
        // }

        // candidatesListView.getItems().setAll(candidates);
    }

}