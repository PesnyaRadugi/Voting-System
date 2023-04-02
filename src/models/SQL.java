package models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface SQL {
    public String dbHost = "localhost";
    public String dbPort = "3306";
    public String dbUser = "root";
    public String dbPass = "12345";
    public String dbName = "votingsystem";

    public Connection connect() throws SQLException, ClassNotFoundException;
    public void closeConnection();

    public void insertUser(User user);
    public void deleteUser(User user);
    public User selectUser(User user);

    public void insertCandidate(Candidate candidate);
    public void deleteCandidate(Candidate candidate);
    public int getCandidateId(Candidate candidate);
    public Candidate selectCandidate(Candidate candidate);
    public List<Candidate> selectAllCandidates();

    public void insertVoting(Voting voting);
    public void deleteVoting(Voting voting);
    public void insertCandidateToVotingList(Voting voting, Candidate candidate);
    public void deleteCandidateFromVotingList(Voting voting, Candidate candidate); 
    public int getVotingId(Voting voting);
    public Voting selectVoting(Voting voting);
    public List<Voting> selectVotings();
    public List<Candidate> selectCandidatesList(Voting voting);
    
    public Admin selectAdmin(Admin admin);
}
