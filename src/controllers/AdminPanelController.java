package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


    private Voting selectedVoting;
    private Candidate selectedCandidate;
    List<Voting> votings = DAO.selectVotings();

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

        availableCandidatesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                if (availableCandidatesList.getSelectionModel().isEmpty()) {
                    selectedCandidate = null;
                } else {
                    selectedCandidate = new Candidate(availableCandidatesList.getSelectionModel().getSelectedItem());
                    deleteCandidateButton.setDisable(false);
                    System.out.println(selectedCandidate.getName());
                }

                if (selectedVoting != null && selectedCandidate != null) {
                    
                    addCandidateToVotingButton.setDisable(false);

                    addCandidateToVotingButton.setOnAction(event -> {
                        if (!votingCandidatesList.getItems().contains(selectedCandidate.getName())) {
                            DAO.insertCandidateToVotingList(selectedVoting, selectedCandidate);
                            loadListOfCandidates();
                        } else {
                            System.out.println("Already added");
                        }
                    });
                } else {
                    addCandidateToVotingButton.setDisable(true);
                }
            }
        });

        deleteCandidateButton.setOnAction(event -> {
            DAO.deleteCandidate(selectedCandidate);
            availableCandidatesList.getItems().remove(selectedCandidate.getName());
            deleteCandidateButton.setDisable(true);
        });

        votingsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                if (votingsList.getSelectionModel().isEmpty()) {
                    selectedVoting = null;
                    votingCandidatesList.getItems().clear();
                } else {
                    selectedVoting = new Voting(votingsList.getSelectionModel().getSelectedItem());
                }

                if (selectedVoting != null) {
                    loadListOfCandidates();
                    System.out.println(selectedVoting.getTitle());
                }
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
            votingCandidatesList.getItems().remove(selectedCandidate.getName());
            deleteCandidateFromVotingButton.setDisable(true);
        });

    }

    private void addCandidate() {
        Candidate candidate = new Candidate(candidateNameField.getText());
        DAO.insertCandidate(candidate);
        availableCandidatesList.getItems().add(candidate.getName());
        availableCandidatesList.scrollTo(candidate.getName());
        availableCandidatesList.getSelectionModel().select(candidate.getName());
    }

    private void addVoting() {
        Voting voting = new Voting(votingTitleField.getText());
        DAO.insertVoting(voting);
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

    private void loadListOfCandidates() {
        List<String> candidates = new ArrayList<>();

        for (Candidate candidate : DAO.selectCandidatesList(selectedVoting)) {
            candidates.add(candidate.getName());
        }

        votingCandidatesList.getItems().setAll(candidates);
    }

    // private void showListViewElements(ListView<String> listView, ResultSet resultSet, String column) {
    //     List<String> values = new ArrayList<>();

    //     try {
    //         while (resultSet.next()) {
    //             values.add(resultSet.getString(column));
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     DAO.closeConnection();
    //     listView.getItems().setAll(values);
    // } 

}
