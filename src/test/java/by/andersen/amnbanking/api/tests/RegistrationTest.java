package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.api.tests.adapters.RegistrationAdapter;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.PropertyHelper;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.DBConnector.DBConnector.LOGIN_USER;

public class RegistrationTest {

    @Test
    public void registrationWithValidDateTest() throws SQLException {
        new DBConnector().deleteUser();
        new RegistrationAdapter().post(JsonObjectHelper.setJsonObjectForRegistration("7qqUqJm00LANA"),
                        PropertyHelper.getProperty("api.host") +
                                PropertyHelper.getProperty("api.registration"),
                        "{\"message\":\"User with login: 7qqUqJm00LANA added\"}").asString();
    }

    @Test
    public void registrationWithDuplicateLoginTest() throws SQLException {
        new DBConnector().getFirstRegistrationUserFromDB();
    new RegistrationAdapter().post(JsonObjectHelper.setJsonObjectForRegistration(LOGIN_USER),
                        PropertyHelper.getProperty("api.host") +
                                PropertyHelper.getProperty("api.registration"),
              "{\"message\":\"ERROR: duplicate key value violates unique constraint \\\"users_login_key\\\"\\n " +
                      " Detail: Key (login)=("+LOGIN_USER+") already exists.\"}" ).asString();
    }
}