package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.ParserJson;
import by.andersen.amnbanking.utils.TestRails;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;
import static by.andersen.amnbanking.data.FilesPath.duplicateRegistrationUser;
import static by.andersen.amnbanking.data.FilesPath.validRegistration;

public class RegistrationTests {
    @TestRails(id="C5888304")
    @Test
    public void registrationWithValidDateTest() throws SQLException {
        new DBConnector().deleteUser();
        new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistration("7qqUqJm00LANA"),
                        API_HOST + API_REGISTRATION).asString();
        Assert.assertEquals( new  ParserJson().parser(validRegistration, "message"),
                "User with login: 7qqUqJm00LANA added");
    }

    @TestRails(id = "C5895599")
    @Test
    public void registrationWithDuplicateLoginTest() {
    new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistration("7qqUqJm00WDVd"),
            API_HOST + API_REGISTRATION).asString();
    Assert.assertEquals(new  ParserJson().parser(duplicateRegistrationUser, "message"),
            "ERROR: duplicate key value violates unique constraint \"users_login_key\"\n" +
                    "  Detail: Key (login)=(7qqUqJm00WDVd) already exists.");
    }

}