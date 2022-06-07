package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;

public class ChangePasswordAfterFirstLoginTests {


    @BeforeTest
    public void createUser() {
        new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem79", "111Gv5dvvf511", "PVS153215DSV"),
                API_HOST + API_REGISTRATION);
    }


    @Test
    public void changePasswordAfterFirstLoginValidDateTest() {
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("Number1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Password changed successfully! Please login again");
    }

    @AfterTest
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
    }
}

