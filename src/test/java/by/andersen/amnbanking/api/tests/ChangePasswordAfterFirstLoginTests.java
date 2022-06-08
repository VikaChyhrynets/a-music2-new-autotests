package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.ParserJson;
import by.andersen.amnbanking.utils.TestRails;
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

    @AfterTest
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
    }

    @TestRails(id = "C5923855")
    @Test(description = "positive test. Change password after first login")
    public void changePasswordAfterFirstLoginValidDateTest() {
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        String response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("Number1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).asString();
        String response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin("Eminem79", "Number1"),
                API_HOST + API_LOGIN).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), "Password changed successfully! Please login again");
        Assert.assertEquals(ParserJson.parser(response1, "message"), AlertAPI.LOGIN_SUCCESS.getValue());
    }

    @TestRails(id = "C5924630")
    @Test(description = "negative test. Change password after first login with less than 7 characters")
    public void changePasswordAfterFirstLoginLessThan7CharsTest() {
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("Num1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924631")
    @Test(description = "negative test. Change password after first login with more than 20 characters")
    public void changePasswordAfterFirstLoginMoreThan20CharsTest() {
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("NumLcnd78554C23569712D1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924632")
    @Test(description = "negative test. Change password after first login with only letters")
    public void changePasswordAfterFirstLoginOnlyLettersTest() {
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("NumLcndvS"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924633")
    @Test(description = "negative test. Change password after first login with only numbers")
    public void changePasswordAfterFirstLoginOnlyNumbersTest() {
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        String response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("569102561"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924634")
    @Test(description = "negative test. Change password after first login with special characters")
    public void changePasswordAfterFirstLoginWithSpecialCharactersTest() {
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        String response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("5691Lvd."),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924635")
    @Test(description = "negative test. Change password after first login with empty new password line")
    public void changePasswordAfterFirstLoginWithEmptyNewPasswordLineTest() {
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        String response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword(""),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924636")
    @Test(description = "negative test. Change password after first login with space new password line")
    public void changePasswordAfterFirstLoginWithSpaceNewPasswordLineTest(){
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        String response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setNewPassword("Number1 "),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());

    }

    @TestRails(id = "C5924636")
    @Test(description = "negative test. Re-login when you cancel the password change at the first login")
    public void changePasswordAfterFirstLoginReLoginTest() {
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"), API_HOST + API_SESSIONCODE);
        Response response = new PostAdapters().postAuthWithSessionCode(authTokenChangePassword, setSmsCode("1234"),
                API_HOST + API_SESSIONCODE).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Required to change password on first login");
    }
}

