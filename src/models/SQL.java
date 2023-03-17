package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQL {
    public String dbHost = "localhost";
    public String dbPort = "3306";
    public String dbUser = "root";
    public String dbPass = "12345";
    public String dbName = "votingsystem";

    public Connection connect() throws SQLException, ClassNotFoundException;
    public void closeConnection();
    public void insertUser(User user);
    public ResultSet selectUser(User user);
    public void insertCandidate(Candidate candidate);
    public void insertCandidateToVotingList(Voting voting, Candidate candidate);
    public ResultSet selectCandidatesList(Voting voting);
    public ResultSet selectAdmin(Admin admin);
}
