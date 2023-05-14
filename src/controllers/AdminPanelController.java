package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Candidate;
import models.Voting;

public class AdminPanelController extends ControllerBase{

    @FXML
    private Button addCandidateToVotingButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField candidateNameField;

    @FXML
    private Button newCandidateButton;

    @FXML
    private Button newVotingButton;
    
    @FXML
    private TextField votingTitleField;

    @FXML
    private ListView<String> availableCandidatesList;
    
    @FXML
    private ListView<String> votingsList;

    @FXML
    private ListView<String> votingCandidatesList;

    @FXML
    private Button deleteCandidateButton;

    @FXML
    private Button deleteCandidateFromVotingButton;

    @FXML
    private Button deleteVotingButton;

    @FXML
    private Button setVotingAsActiveButton;


    private Voting selectedVoting;
    private Candidate selectedCandidate;
    private List<Voting> votings = DAO.selectVotings();
    private List<Candidate> candidates = DAO.selectAllCandidates();

    @FXML
    void initialize() {
        loadCandidates();
        loadVotings();

        newCandidateButton.setOnAction(event -> {
            addCandidate();
        });

        newVotingButton.setOnAction(event -> {
            addVoting();
        });

        availableCandidatesList.setOnMouseClicked(event -> {

            deleteCandidateButton.disableProperty().bind(availableCandidatesList.getSelectionModel().selectedItemProperty().isNull());
            addCandidateToVotingButton.disableProperty().bind(availableCandidatesList.getSelectionModel().selectedItemProperty().isNull());
            if (!availableCandidatesList.getSelectionModel().isEmpty()) {
                selectedCandidate = candidates.get(availableCandidatesList.getSelectionModel().getSelectedIndex());
                System.out.println(selectedCandidate.getName());
            }

        });

        deleteCandidateButton.setOnAction(event -> {
            DAO.deleteCandidate(selectedCandidate);
            candidates.remove(selectedCandidate);
            availableCandidatesList.getItems().remove(selectedCandidate.getName());
            
            for (Voting voting : votings) {
                if (voting.getCandidates().contains(selectedCandidate)) {
                    voting.deleteCandidate(selectedCandidate);
                }
            }
            
            if (!availableCandidatesList.getSelectionModel().isEmpty()) {
                selectedCandidate = candidates.get(availableCandidatesList.getSelectionModel().getSelectedIndex());
                System.out.println(selectedCandidate.getName());
            }

        });

        addCandidateToVotingButton.setOnAction(evennt -> {
            if (selectedCandidate != null && selectedVoting != null) {
                DAO.insertCandidateToVotingList(selectedVoting, selectedCandidate);
                votingCandidatesList.getItems().add(selectedCandidate.getName());
                selectedVoting.addCandidate(selectedCandidate);
            }
        });

        votingsList.setOnMouseClicked(event -> {
            deleteVotingButton.disableProperty().bind(votingsList.getSelectionModel().selectedItemProperty().isNull());
            setVotingAsActiveButton.disableProperty().bind(votingsList.getSelectionModel().selectedItemProperty().isNull());
            selectedVoting = votings.get(votingsList.getSelectionModel().getSelectedIndex());
            System.out.println(selectedVoting.getTitle());
            if (selectedVoting != null && selectedVoting.getCandidates() != null) {
                List<String> candidatesNames = new ArrayList<>();

                for (Candidate candidate : selectedVoting.getCandidates()) {
                    candidatesNames.add(candidate.getName());
                }

                votingCandidatesList.getItems().setAll(candidatesNames);
            } else {
                votingCandidatesList.getItems().clear();
            }
        });

        deleteVotingButton.setOnAction(event -> {
            DAO.deleteVoting(selectedVoting);
            votingsList.getItems().remove(selectedVoting.getTitle());
            votings.remove(selectedVoting);
            votingCandidatesList.getItems().clear();
        });

        votingCandidatesList.setOnMouseClicked(event -> {
            selectedCandidate = selectedVoting.getCandidates().get(votingCandidatesList.getSelectionModel().getSelectedIndex());
            System.out.println(selectedCandidate.getName());
            deleteCandidateFromVotingButton.disableProperty().bind(votingCandidatesList.getSelectionModel().selectedItemProperty().isNull());
            System.out.println(selectedCandidate.getVoices());
        });

        deleteCandidateFromVotingButton.setOnAction(event -> {
            DAO.deleteCandidateFromVotingList(selectedVoting, selectedCandidate);
            votingCandidatesList.getItems().remove(selectedCandidate.getName());
            selectedVoting.deleteCandidate(selectedCandidate);
        });
    
    }

    private void addCandidate() {
        Candidate candidate = new Candidate(candidateNameField.getText());
        candidateNameField.clear();
        DAO.insertCandidate(candidate);
        candidates.add(candidate);
        availableCandidatesList.getItems().add(candidate.getName());
        availableCandidatesList.scrollTo(candidate.getName());
        availableCandidatesList.getSelectionModel().select(candidate.getName());
    }

    private void addVoting() {
        Voting voting = new Voting(votingTitleField.getText());
        votingTitleField.clear();
        DAO.insertVoting(voting);
        votings.add(voting);
        votingsList.getItems().add(voting.getTitle());
        votingsList.scrollTo(voting.getTitle());
        votingsList.getSelectionModel().select(voting.getTitle());
    }

    private void loadVotings() {
        for (Voting voting : votings) {
            votingsList.getItems().add(voting.getTitle());
        }
    }

    private void loadCandidates() {
        
        List<String> candidates = new ArrayList<>();

        for (Candidate candidate : DAO.selectAllCandidates()) {
            candidates.add(candidate.getName());
        }

        availableCandidatesList.getItems().setAll(candidates);
    }

    // private void loadListOfCandidates() {
    //     List<String> candidates = new ArrayList<>();

    //     for (Candidate candidate : DAO.selectCandidatesList(selectedVoting)) {
    //         candidates.add(candidate.getName());
    //     }

    //     votingCandidatesList.getItems().setAll(candidates);
    // }

}
