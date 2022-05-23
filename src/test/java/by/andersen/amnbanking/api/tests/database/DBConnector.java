package by.andersen.amnbanking.api.tests.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    private final String HOST = "jdbc:postgresql://10.10.15.32:5432/amusic";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "3PgZJ2%S2PeX!R-";
    private Connection connection;
    private Statement statement;

    public DBConnector() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}
