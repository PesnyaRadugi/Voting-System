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
import models.VotingSystem;

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
    private Button joinVotingButton;

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

        // TO DO, probably.
        // if (VotingSytem.VOTING_SYTEM.getCurrentVoting() != null) {
        //     joinVotingButton.setDisable(false);
        // }

        exitButton.setOnAction(event -> {
            switchScene(menuButton, Const.LOGIN_SCREEN);
            VotingSystem.VOTING_SYSTEM.setCurrentUser(null);
            // VotingSytem.currentUser = null;
        });

        votingsListView.setOnMouseClicked(event -> {
            if (!votingsListView.getSelectionModel().isEmpty()) {
                selectedVoting = votings.get(votingsListView.getSelectionModel().getSelectedIndex());
                VotingSystem.VOTING_SYSTEM.setCurrentVoting(selectedVoting);
                loadCandidates();
                System.out.println(VotingSystem.VOTING_SYSTEM.getCurrentUser().getName());
            }
        });

        candidatesListView.setOnMouseClicked(even -> {
            if (!candidatesListView.getSelectionModel().isEmpty()) {
                selectedCandidate = selectedVoting.getCandidates().get(candidatesListView.getSelectionModel().getSelectedIndex());
                System.out.println(selectedCandidate.getName() + " voices: " + selectedCandidate.getVoices());
            }
        });

        voteMenacinglyButton.disableProperty().bind(candidatesListView.getSelectionModel().selectedItemProperty().isNull());
        voteMenacinglyButton.setOnAction(event -> {
            if (!DAO.votingHasParticipant(selectedVoting, VotingSystem.VOTING_SYSTEM.getCurrentUser())) {
                DAO.insertParticipant(VotingSystem.VOTING_SYSTEM.getCurrentUser(), selectedVoting);
                DAO.addVoiceToCandidate(selectedCandidate);
                selectedCandidate.addVoice();
                System.out.println("Succesfully voted!");
            } else {
                System.out.println("Already voted!");
            }
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
    }

}