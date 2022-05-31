package by.andersen.amnbanking.DBConnector;

import by.andersen.amnbanking.utils.BDHelper;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    DBWorker worker = new DBWorker();
    Statement statement;
    String query;
    String line;
   public static String LOGIN_USER;


    @Test
    public void deleteUser(String login) throws SQLException {
        query = new BDHelper().setRequestForDeleteUser(login);
        statement = worker.getConnection().createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    @Test
    public void getFirstRegistrationUserFromDB() throws SQLException {
        query = new BDHelper().getUserFormBD();
        statement = worker.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            LOGIN_USER = resultSet.getString("login");
        }
    }
}