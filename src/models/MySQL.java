package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        } finally {
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public User selectUser(User user) {
        User selectedUser = null;
        ResultSet resultSet = null;
        
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
        
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            selectedUser = new User(resultSet.getString(Const.USERS_NAME), resultSet.getString(Const.USERS_USERNAME), resultSet.getString(Const.USERS_PASSWORD));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return selectedUser;
        
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
        } finally {
            closeConnection();
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
    public List<Candidate> selectAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CANDIDATE_TABLE;
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);
        
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                candidates.add(new Candidate(resultSet.getString(Const.CANDIDATES_NAME)));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return candidates;
    }
    
    @Override
    public void insertCandidateToVotingList(Voting voting, Candidate candidate) {
        String insert = "INSERT INTO " + Const.VOTING_CANDIDATES_LIST_TABLE + " (" +
                Const.VOTING_CANDIDATE_LIST_VOTINGID + "," + Const.VOTING_CANDIDATE_LIST_CANDIDATEID + ","
                + Const.VOTING_CANDIDATE_LIST_CANDIDATENAME + "," + Const.VOTING_CANDIDATE_LIST_CANDIDATE_VOICES + ") VALUES(?,?,?,?)";  
        
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(insert);
            preparedStatement.setInt(1, getVotingId(voting));
            preparedStatement.setInt(2, getCandidateId(candidate));
            preparedStatement.setString(3, candidate.getName());
            preparedStatement.setInt(4, candidate.getVoices());

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
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
            PreparedStatement preparedStatement = connect().prepareStatement(delete);
            preparedStatement.setString(1, candidate.getName());
            preparedStatement.setInt(2, getVotingId(voting));
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Candidate selectCandidate(Candidate candidate) {
        Candidate selectedCandidate = null;
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CANDIDATE_TABLE + " WHERE " + 
                Const.CANDIDATES_NAME + " =?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);

            preparedStatement.setString(1, candidate.getName());

            resultSet = preparedStatement.executeQuery();

            selectedCandidate = new Candidate(resultSet.getString(Const.CANDIDATES_NAME));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return selectedCandidate;
    }

    @Override
    public List<Candidate> selectCandidatesList(Voting voting) {
        List<Candidate> candidates = new ArrayList<>();
        ResultSet resultSet = null;
       
        String select = "SELECT " + Const.VOTING_CANDIDATE_LIST_CANDIDATENAME + ", " + Const.VOTING_CANDIDATE_LIST_CANDIDATE_VOICES + " FROM "
                + Const.VOTING_CANDIDATES_LIST_TABLE + " WHERE " + Const.VOTING_CANDIDATE_LIST_VOTINGID + " =?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);
            preparedStatement.setInt(1, getVotingId(voting));

            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                candidates.add(new Candidate(resultSet.getString(Const.VOTING_CANDIDATE_LIST_CANDIDATENAME), resultSet.getInt(Const.VOTING_CANDIDATE_LIST_CANDIDATE_VOICES)));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return candidates;
    }

    @Override
    public Admin selectAdmin(Admin admin) {
        Admin selectedAdmin = null;
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.ADMIN_TABLE + " WHERE " + 
                Const.ADMINS_USERNAME + " =? AND " + Const.ADMINS_PASSWORD + " =?";

            try {
                PreparedStatement preparedStatement = connect().prepareStatement(select);

                preparedStatement.setString(1, admin.getLogin());
                preparedStatement.setString(2, admin.getPassword());

                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                selectedAdmin = new Admin(resultSet.getString(2), resultSet.getString(2), resultSet.getString(4));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }

        return selectedAdmin;
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
        } finally {
            closeConnection();
        }
    }

    @Override
    public void deleteVoting(Voting voting) {
        String delete1 = "DELETE FROM " + Const.VOTING_TABLE + " WHERE " + Const.VOTINGS_ID + " =?";
        String delete2 = "DELETE FROM " + Const.VOTING_CANDIDATES_LIST_TABLE + " WHERE " + Const.VOTING_CANDIDATE_LIST_VOTINGID + " =?";

        String selectVotingId = "SELECT " + Const.VOTINGS_ID + " FROM " + Const.VOTING_TABLE + " WHERE " +
                Const.VOTINGS_NAME + " =?";  

        try {
            PreparedStatement preparedStatement;
            ResultSet resultSet;  
            
            preparedStatement = connect().prepareStatement(selectVotingId);
            preparedStatement.setString(1, voting.getTitle());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int votingId = resultSet.getInt(Const.VOTINGS_ID);

            preparedStatement = connect().prepareStatement(delete1);
            preparedStatement.setInt(1, votingId);
            preparedStatement.executeUpdate();

            preparedStatement = connect().prepareStatement(delete2);
            preparedStatement.setInt(1, votingId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Voting selectVoting(Voting voting) {
        Voting selectedVoting = null;
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.VOTING_TABLE + " WHERE " + 
                Const.VOTINGS_NAME + " =?";
        
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);

            preparedStatement.setString(1, voting.getTitle());

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            selectedVoting = new Voting(resultSet.getString(Const.VOTINGS_NAME));
            selectedVoting.setCandidates(selectCandidatesList(voting));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return selectedVoting;
    }

    @Override
    public List<Voting> selectVotings() {
        List<Voting> votings = new ArrayList<>();
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.VOTING_TABLE;
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Voting voting = new Voting(resultSet.getString(Const.VOTINGS_NAME));
                voting.setCandidates(selectCandidatesList(voting));
                votings.add(voting);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return votings;
    }

    @Override
    public int getCandidateId(Candidate candidate) {
        ResultSet resultSet = null;

        String select = "SELECT " + Const.CANDIDATES_ID + " FROM " + Const.CANDIDATE_TABLE + " WHERE "
        + Const.CANDIDATES_NAME + " =?";  

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);
            preparedStatement.setString(1, candidate.getName());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int candidateId = resultSet.getInt(Const.CANDIDATES_ID);
            return candidateId;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return 0;
    }

    @Override
    public int getVotingId(Voting voting) {
        ResultSet resultSet = null;

        String select = "SELECT " + Const.VOTINGS_ID + " FROM " + Const.VOTING_TABLE + " WHERE "
        + Const.VOTINGS_NAME + " =?";  

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(select);
            preparedStatement.setString(1, voting.getTitle());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int votingId = resultSet.getInt(Const.VOTINGS_ID);
            return votingId;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return 0;
    }

}
