package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;;

public class DbHandler extends Configs {
    Connection dbConnection;
    
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void RegistrateUser(User user) {
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

}
