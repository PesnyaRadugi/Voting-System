package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Candidate;
import models.Const;
import models.Voting;

public class AdminPanelController extends ControllerBase{

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


    private Voting selectedVoting;
    private Candidate selectedCandidate;

    @FXML
    void initialize() {
        showListViewElements(availableCandidatesList, DAO.selectAllCandidates(), Const.CANDIDATES_NAME);
        showListViewElements(votingsList, DAO.selectVotings(), Const.VOTINGS_NAME);

        newCandidateButton.setOnAction(event -> {
            addCandidate();
        });

        newVotingButton.setOnAction(event -> {
            addVoting();
        });

        availableCandidatesList.setOnMouseClicked(event -> {
            if (availableCandidatesList.getSelectionModel().isEmpty()) {
                selectedCandidate = null;
                deleteCandidateButton.setDisable(true);
            } else {
                selectedCandidate = new Candidate(availableCandidatesList.getSelectionModel().getSelectedItem());
                deleteCandidateButton.setDisable(false);
                System.out.println(selectedCandidate.getName());
            }
    
            if (selectedVoting != null && selectedCandidate != null) {
                
                if (!votingCandidatesList.getItems().contains(selectedCandidate.getName())) {
                    DAO.insertCandidateToVotingList(selectedVoting, selectedCandidate);
                }
                showListViewElements(votingCandidatesList, DAO.selectCandidatesList(selectedVoting), Const.VOTING_CANDIDATE_LIST_CANDIDATENAME);
            }

        });

        deleteCandidateButton.setOnAction(event -> {
            DAO.deleteCandidate(selectedCandidate);
            showListViewElements(availableCandidatesList, DAO.selectAllCandidates(), Const.CANDIDATES_NAME);
            deleteCandidateButton.setDisable(true);
        });

        votingsList.setOnMouseClicked(event -> {
            if (votingsList.getSelectionModel().isEmpty()) {
                selectedVoting = null;
                votingCandidatesList.getItems().clear();
            } else {
                selectedVoting = new Voting(votingsList.getSelectionModel().getSelectedItem());
            }

            if (selectedVoting != null) {
                votingCandidatesList.getItems().setAll(selectedVoting.getCandidatesNames());
                showListViewElements(votingCandidatesList, DAO.selectCandidatesList(selectedVoting), Const.VOTING_CANDIDATE_LIST_CANDIDATENAME);
                System.out.println(selectedVoting.getTitle());
            }

        });

        votingCandidatesList.setOnMouseClicked(event -> {
            if (votingCandidatesList.getSelectionModel().isEmpty()) {
                deleteCandidateFromVotingButton.setDisable(true);
            } else {
                deleteCandidateFromVotingButton.setDisable(false);
                selectedCandidate = new Candidate(votingCandidatesList.getSelectionModel().getSelectedItem());
            }
        });

        deleteCandidateFromVotingButton.setOnAction(event -> {
            DAO.deleteCandidateFromVotingList(selectedVoting, selectedCandidate);
            showListViewElements(votingCandidatesList, DAO.selectCandidatesList(selectedVoting), Const.VOTING_CANDIDATE_LIST_CANDIDATENAME);
            deleteCandidateFromVotingButton.setDisable(true);
        });

    }

    private void addCandidate() {
        Candidate candidate = new Candidate(candidateNameField.getText());
        DAO.insertCandidate(candidate);
        showListViewElements(availableCandidatesList, DAO.selectAllCandidates(), Const.CANDIDATES_NAME);
        availableCandidatesList.scrollTo(candidate.getName());
        availableCandidatesList.getSelectionModel().select(candidate.getName());
    }

    private void addVoting() {
        Voting voting = new Voting(votingTitleField.getText());
        DAO.insertVoting(voting);
        showListViewElements(votingsList, DAO.selectVotings(), Const.VOTINGS_NAME);
        votingsList.scrollTo(voting.getTitle());
        votingsList.getSelectionModel().select(voting.getTitle());
    }


    private void showListViewElements(ListView<String> listView, ResultSet resultSet, String column) {
        List<String> values = new ArrayList<>();

        try {
            while (resultSet.next()) {
                values.add(resultSet.getString(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DAO.closeConnection();
        listView.getItems().setAll(values);
    } 

}
