package by.andersen.amnbanking.DBConnector;

import by.andersen.amnbanking.utils.BDHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    DBWorker worker = new DBWorker();
    Statement statement;
    String query;
    public static String LOGIN_USER;

    public void deleteUser(String login) throws SQLException {
        query = new BDHelper().setRequestForDeleteUser(login);
        statement = worker.getConnection().createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    public void getFirstRegistrationUserFromDB() throws SQLException {
        query = new BDHelper().getUserFormBD();
        statement = worker.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            LOGIN_USER = resultSet.getString("login");
        }
    }
}