package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;

public class ChangePasswordAfterFirstLoginTests {

    public void createUser() {
        new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem79", "111Gv5dvvf511", "PVS153215DSV"),
                API_HOST + API_REGISTRATION);
    }

    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
    }

    @TestRails(id = "C5923855")
    @Test(description = "positive test. Change password after first login")
    public void changePasswordAfterFirstLoginValidDateTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("Number1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Response response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin("Eminem79", "Number1"),
                API_HOST + API_LOGIN).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Password changed successfully! Please login again");
        Assert.assertEquals(response1.getMessage(), "Access granted");
        deleteUser();
    }

    @TestRails(id = "C5924630")
    @Test(description = "negative test. Change password after first login with less than 7 characters")
    public void changePasswordAfterFirstLoginLessThan7CharsTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("Num1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924631")
    @Test(description = "negative test. Change password after first login with more than 20 characters")
    public void changePasswordAfterFirstLoginMoreThan20CharsTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("NumLcnd78554C23569712D1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924632")
    @Test(description = "negative test. Change password after first login with only letters")
    public void changePasswordAfterFirstLoginOnlyLettersTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("NumLcndvS"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924633")
    @Test(description = "negative test. Change password after first login with only numbers")
    public void changePasswordAfterFirstLoginOnlyNumbersTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("569102561"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924634")
    @Test(description = "negative test. Change password after first login with special characters")
    public void changePasswordAfterFirstLoginWithSpecialCharactersTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("5691Lvd."),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        String getMessageFromAPI = response.getMessage();
        Assert.assertEquals(getMessageFromAPI, AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924635")
    @Test(description = "negative test. Change password after first login with empty new password line")
    public void changePasswordAfterFirstLoginWithEmptyNewPasswordLineTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword(""),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        String getMessageFromAPI = response.getMessage();
        Assert.assertEquals(getMessageFromAPI, AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924636")
    @Test(description = "negative test. Change password after first login with space new password line")
    public void changePasswordAfterFirstLoginWithSpaceNewPasswordLineTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("Number1 "),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924636")
    @Test(description = "negative test. Re-login when you cancel the password change at the first login")
    public void changePasswordAfterFirstLoginReLoginTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"),
                API_HOST + API_SESSIONCODE).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Required to change password on first login");
        deleteUser();
    }
}

