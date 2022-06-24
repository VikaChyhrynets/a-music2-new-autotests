package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.ParserJson;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;

@Story("UC 1.5 - Changing password on first login")
public class ChangePasswordAfterFirstLoginTests extends BaseTest{
    @TestRails(id = "C5923855")
    @Step("Change password after first login, valid date")
    @Test(description = "positive test. Change password after first login")
    public void changePasswordAfterFirstLoginValidDateTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 200);
        String response = new PostAdapters().post( setNewPassword("Number1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, 200).asString();
        String response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin("Eminem79", "Number1"),
                API_HOST + API_LOGIN, 200).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), "Password changed successfully! Please login again");
        Assert.assertEquals(ParserJson.parser(response1, "message"), AlertAPI.LOGIN_SUCCESS.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924630")
    @Step("Change password with less than 7 characters, invalid data")
    @Test(description = "negative test. Change password after first login with less than 7 characters")
    public void changePasswordAfterFirstLoginLessThan7CharsTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 200);
        Response response = new PostAdapters().post(setNewPassword("Num1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924631")
    @Step("Change password with with more than 20 characters, invalid data")
    @Test(description = "negative test. Change password after first login with more than 20 characters")
    public void changePasswordAfterFirstLoginMoreThan20CharsTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 200);
        String response = new PostAdapters().post(setNewPassword("NumLcnd78554C23569712D1"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, 400).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924632")
    @Step("Change password with with only letters, invalid data")
    @Test(description = "negative test. Change password after first login with only letters")
    public void changePasswordAfterFirstLoginOnlyLettersTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 200);
        String response = new PostAdapters().post(setNewPassword("NumLcndvS"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, 400).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924633")
    @Step("Change password with with only numbers, invalid data")
    @Test(description = "negative test. Change password after first login with only numbers")
    public void changePasswordAfterFirstLoginOnlyNumbersTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 200);
        String response = new PostAdapters().post(setNewPassword("569102561"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, 400).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924634")
    @Step("Change password with with special characters, invalid data")
    @Test(description = "negative test. Change password after first login with special characters")
    public void changePasswordAfterFirstLoginWithSpecialCharactersTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 200);
        String response = new PostAdapters().post(setNewPassword("5691Lvd."),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, 400).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924635")
    @Step("Change password with with empty new password line, invalid data")
    @Test(description = "negative test. Change password after first login with empty new password line")
    public void changePasswordAfterFirstLoginWithEmptyNewPasswordLineTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 200);
        String response = new PostAdapters().post(setNewPassword(""),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, 400).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924636")
    @Step("Change password with with space new password line, invalid data")
    @Test(description = "negative test. Change password after first login with space new password line")
    public void changePasswordAfterFirstLoginWithSpaceNewPasswordLineTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 200);
        String response = new PostAdapters().post(setNewPassword("Number1 "),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, 400).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
        deleteUser();
    }

    @TestRails(id = "C5924636")
    @Step("Re-login when you cancel the password change at the first login, negative test")
    @Test(description = "negative test. Re-login when you cancel the password change at the first login")
    public void changePasswordAfterFirstLoginReLoginTest() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 200);
        String response = new PostAdapters().post(setSmsCode("1234"),
                API_HOST + API_SESSIONCODE, authTokenChangePassword, 308).asString();
        Assert.assertEquals(ParserJson.parser(response,"message()"), "Required to change password on first login");
        deleteUser();
    }
}

