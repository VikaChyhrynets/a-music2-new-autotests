package by.andersen.amnbanking.DBConnector;

import by.andersen.amnbanking.utils.BDHelper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnector {
    DBWorker worker = new DBWorker();
    Statement statement;
    Connection connection;
    String query;
    public static String LOGIN_USER;

    public static ArrayList<String> WORKING_HOURS;
    public static String LOCAL_PHONE_NUMBER_FOR_INDIVIDUALS;
    public static String INTERNATIONAL_PHONE_NUMBER_FOR_INDIVIDUALS;
    public static ArrayList<String> OPENING_HOURS_FOR_CARD_SUPPORT;
    public static String LOCAL_PHONE_NUMBER_FOR_CARD_SUPPORT;
    public static String INTERNATIONAL_PHONE_NUMBER_FOR_CARD_SUPPORT;

    public void deleteUser(String login) throws SQLException {
        query = new BDHelper().setRequestForDeleteUser(login);
        connection = worker.getConnection();
        statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
        connection.close();
    }

    public void getFirstRegistrationUserFromDB() throws SQLException {
        query = new BDHelper().getUserFormBD();
        statement = worker.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            LOGIN_USER = resultSet.getString("login");
        }
        statement.close();
    }

    public void getIndividualWorkingHoursFromDB() throws SQLException {
        query = new BDHelper().getIndividualWorkingHours();
        connection = worker.getConnection();
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        WORKING_HOURS = new ArrayList<String>();
        while (resultSet.next()) {
            WORKING_HOURS.add(resultSet.getString("workingHours"));
        }
        statement.close();
        connection.close();
    }

    public void getPhoneNumberForIndividualsFromDB() throws SQLException {
        query = new BDHelper().getPhoneNumberForIndividuals();
        connection = worker.getConnection();
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            LOCAL_PHONE_NUMBER_FOR_INDIVIDUALS = resultSet.getString("local_phone");
            INTERNATIONAL_PHONE_NUMBER_FOR_INDIVIDUALS = resultSet.getString("international_phone");
        }
        statement.close();
        connection.close();
    }

    public void getOpeningHoursForCardSupportFromDB() throws SQLException {
        query = new BDHelper().getOpeningHoursForCardSupport();
        connection = worker.getConnection();
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        OPENING_HOURS_FOR_CARD_SUPPORT = new ArrayList<String>();
        while (resultSet.next()) {
            OPENING_HOURS_FOR_CARD_SUPPORT.add(resultSet.getString("openingHours"));
        }
        statement.close();
        connection.close();
    }

    public void getPhoneNumberForCardSupportFromDB() throws SQLException {
        query = new BDHelper().getPhoneNumberForCardSupport();
        connection = worker.getConnection();
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            LOCAL_PHONE_NUMBER_FOR_CARD_SUPPORT = resultSet.getString("local_phone");
            INTERNATIONAL_PHONE_NUMBER_FOR_CARD_SUPPORT = resultSet.getString("international_phone");
        }
        statement.close();
        connection.close();
    }

}