package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;

public class MySQL implements SQL {
    Connection dbConnection;
    
    @Override
    public Connection connect() throws SQLException, ClassNotFoundException {

        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        
        return dbConnection;
    }

    @Override
    public void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        String delete = "DELETE FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + " =?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(delete);

            preparedStatement.setString(1, user.getLogin());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public void insertUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + 
                Const.USERS_NAME + "," + Const.USERS_USERNAME + "," +
                Const.USERS_PASSWORD + ")" + "VALUES(?,?,?)";
        
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(insert);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
        finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ResultSet selectUser(User user) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);
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

    @Override
    public void insertCandidate(Candidate candidate) {
        String insert = "INSERT INTO " + Const.CANDIDATE_TABLE + "(" +
                Const.CANDIDATES_NAME + ") VALUES(?)";
        
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(insert);
            preparedStatement.setString(1, candidate.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteCandidate(Candidate candidate) {
        String deleteFromCandidateTable = "DELETE FROM " + Const.CANDIDATE_TABLE + " WHERE " + Const.CANDIDATES_NAME + " =?";
        String deleteFromCandidateListTable = "DELETE FROM " + Const.VOTING_CANDIDATES_LIST_TABLE + " WHERE " +
                Const.VOTING_CANDIDATE_LIST_CANDIDATENAME + " =?";
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(deleteFromCandidateTable);
            preparedStatement.setString(1, candidate.getName());
            preparedStatement.executeUpdate();
            
            preparedStatement = connect().prepareStatement(deleteFromCandidateListTable);
            preparedStatement.setString(1, candidate.getName());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public ResultSet selectAllCandidates() {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CANDIDATE_TABLE;
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);
        
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
    
    @Override
    public void insertCandidateToVotingList(Voting voting, Candidate candidate) {
        String insert = "INSERT INTO " + Const.VOTING_CANDIDATES_LIST_TABLE + " (" +
                Const.VOTING_CANDIDATE_LIST_VOTINGID + "," + Const.VOTING_CANDIDATE_LIST_CANDIDATEID + ","
                + Const.VOTING_CANDIDATE_LIST_CANDIDATENAME + "," + Const.VOTING_CANDIDATE_LIST_CANDIDATE_VOICES + ") VALUES(?,?,?,?)";
        try {
            ResultSet resultSet;
            
            resultSet = selectVoting(voting);
            resultSet.next();
            int votingId = resultSet.getInt(Const.VOTINGS_ID);

            resultSet = selectCandidate(candidate);
            resultSet.next();
            int candidateId = resultSet.getInt(Const.CANDIDATES_ID);

            PreparedStatement preparedStatement = connect().prepareStatement(insert);
            preparedStatement.setInt(1, votingId);
            preparedStatement.setInt(2, candidateId);
            preparedStatement.setString(3, candidate.getName());
            preparedStatement.setInt(4, candidate.getVoices());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            // e.printStackTrace();
        } 
        catch (ClassNotFoundException e) {
            // e.printStackTrace();
        }
        finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteCandidateFromVotingList(Voting voting, Candidate candidate) {
        String delete = "DELETE FROM " + Const.VOTING_CANDIDATES_LIST_TABLE + " WHERE " + Const.VOTING_CANDIDATE_LIST_CANDIDATENAME + " =? AND "
                + Const.VOTING_CANDIDATE_LIST_VOTINGID + " =?";
    
        try {
            ResultSet resultSet;
            
            resultSet = selectVoting(voting);
            resultSet.next();
            int votingId = resultSet.getInt(Const.VOTINGS_ID);

            PreparedStatement preparedStatement = connect().prepareStatement(delete);

            preparedStatement.setString(1, candidate.getName());
            preparedStatement.setInt(2, votingId);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public ResultSet selectCandidate(Candidate candidate) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CANDIDATE_TABLE + " WHERE " + 
                Const.CANDIDATES_NAME + " =?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);

            preparedStatement.setString(1, candidate.getName());

            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    @Override
    public ResultSet selectCandidatesList(Voting voting) {
        ResultSet resultSet = null;
       
        String select = "SELECT " + Const.VOTING_CANDIDATE_LIST_CANDIDATENAME + ", " + Const.VOTING_CANDIDATE_LIST_CANDIDATE_VOICES + " FROM "
                + Const.VOTING_CANDIDATES_LIST_TABLE + " WHERE " + Const.VOTING_CANDIDATE_LIST_VOTINGID + " =?";  

        try {
            resultSet = selectVoting(voting);
            resultSet.next();
            int votingId = resultSet.getInt(Const.VOTINGS_ID);
            
            PreparedStatement preparedStatement = connect().prepareStatement(select);
            preparedStatement.setInt(1, votingId);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    @Override
    public ResultSet selectAdmin(Admin admin) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.ADMIN_TABLE + " WHERE " + 
                Const.ADMINS_USERNAME + " =? AND " + Const.ADMINS_PASSWORD + " =?";

            try {
                PreparedStatement preparedStatement = connect().prepareStatement(select);

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

    @Override
    public void insertVoting(Voting voting) {
        String insert = "INSERT INTO " + Const.VOTING_TABLE + "(" +
            Const.VOTINGS_NAME + ") VALUES(?)" ;

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(insert);
            preparedStatement.setString(1, voting.getTitle());

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVoting(Voting voting) {
        String delete = "DELETE FROM " + Const.VOTING_TABLE + " WHERE " + Const.VOTINGS_NAME + " =?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(delete);

            preparedStatement.setString(1, voting.getTitle());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public ResultSet selectVoting(Voting voting) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.VOTING_TABLE + " WHERE " + 
                Const.VOTINGS_NAME + " =?";
        
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);

            preparedStatement.setString(1, voting.getTitle());

            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    @Override
    public ResultSet selectVotings() {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.VOTING_TABLE;
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);
        
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

}
