package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.api.tests.adapters.PostAdapters;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.ParserJson;
import by.andersen.amnbanking.utils.PropertyHelper;
import by.andersen.amnbanking.utils.TestRails;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.DBConnector.DBConnector.LOGIN_USER;
import static by.andersen.amnbanking.data.FilesPath.validRegistration;

public class RegistrationTests {
    @TestRails(id="C5888304")
    @Test
    public void registrationWithValidDateTest() throws SQLException {
        new DBConnector().deleteUser();
        new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistration("7qqUqJm00LANA"),
                        PropertyHelper.getProperty("api.host") +
                                PropertyHelper.getProperty("api.registration")).asString();
        Assert.assertEquals( new  ParserJson().parser(validRegistration, "message"), "User with login: 7qqUqJm00LANA added");
    }

    @Test
    public void registrationWithDuplicateLoginTest() throws SQLException {
        new DBConnector().getFirstRegistrationUserFromDB();
    new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistration(LOGIN_USER),
                        PropertyHelper.getProperty("api.host") +
                                PropertyHelper.getProperty("api.registration")).asString();
    }
//    , "message",
//            "ERROR: duplicate key value violates unique constraint \\\"users_login_key\\\" " +
//            " Detail: Key (login)=(1116149135) already exists."
}