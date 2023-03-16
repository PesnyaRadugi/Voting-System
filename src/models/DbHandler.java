package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DbHandler extends Configs {
    Connection dbConnection;
    
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void createNewUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + 
                Const.USERS_NAME + "," + Const.USERS_USERNAME + "," +
                Const.USERS_PASSWORD + ")" + "VALUES(?,?,?)";
        
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
    }

    public void createNewCandidate(Candidate candidate) {
        String insert = "INSERT INTO " + Const.CANDIDATE_TABLE + "(" +
                Const.CANDIDATES_NAME + ")" + "VALUES(?,?)";
        
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, candidate.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void putCandidateToVotingList(Voting voting, Candidate candidate) {
        String insert = "INSERT INTO " + Const.VOTING_CANDIDATES_LIST_TABLE + "(" +
                Const.VOTING_CANDIDATE_LIST_VOTINGID + "," + Const.VOTING_CANDIDATE_LIST_CANDIDATEID + ","
                + Const.VOTING_CANDIDATE_LIST_CANDIDATENAME + "," + Const.VOTING_CANDIDATE_LIST_CANDIDATE_VOICES + ")" + 
                "VALUES(?,?,?,?)";

        String selectVotingId = "(SELECT " + Const.VOTINGS_ID + " FROM " + Const.VOTING_TABLE + " WHERE "
                + Const.VOTINGS_NAME + " = " + voting.getTitle() + ")"; 
        String selectCandidateId = "(SELECT " + Const.CANDIDATES_ID + " FROM " + Const.CANDIDATE_TABLE + " WHERE "
                + Const.CANDIDATES_NAME + " = " + candidate.getName() + ")";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, selectVotingId);
            preparedStatement.setString(2, selectCandidateId);
            preparedStatement.setString(3, candidate.getName());
            preparedStatement.setInt(4, candidate.getVoices());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getCandidatesList(Voting voting) {
        ResultSet resultSet = null;
       
        String select = "SELECT " + Const.VOTING_CANDIDATE_LIST_CANDIDATENAME + ", " + Const.VOTING_CANDIDATE_LIST_CANDIDATE_VOICES + " FROM "
                + Const.VOTING_CANDIDATES_LIST_TABLE + " WHERE " + Const.VOTING_CANDIDATE_LIST_VOTINGID + " =?";  
    
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, voting.getTitle());

            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
        
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getAdmin(Admin admin) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.ADMIN_TABLE + " WHERE " + 
                Const.ADMINS_USERNAME + " =? AND " + Const.ADMINS_PASSWORD + " =?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

                preparedStatement.setString(1, admin.getLogin());
                preparedStatement.setString(2, admin.getPassword());

                resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        return resultSet;
    }
}
