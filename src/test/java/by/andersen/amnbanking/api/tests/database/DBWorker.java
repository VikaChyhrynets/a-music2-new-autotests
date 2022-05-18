package by.andersen.amnbanking.api.tests.database;

import java.sql.Statement;

public class DBWorker {
    DBConnector connection = new DBConnector();
    Statement statement;
    String query;
    String line;
}
