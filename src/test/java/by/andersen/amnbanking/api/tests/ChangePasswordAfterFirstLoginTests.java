package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.ParserJson;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PRECONDITION_FAILED;

@Epic("Epic 1: Registration and authorization")
public class ChangePasswordAfterFirstLoginTests extends BaseAPITest {
    @TmsLink("5923855")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "positive test. Change password after first login")
    public void changePasswordAfterFirstLoginValidDateTest() throws SQLException {
            createUser();
            String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
            new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED);
            String response = new PostAdapters().post(setNewPassword("Number1"),
                    API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_OK).asString();
            String response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin("Eminem79", "Number1"),
                    API_HOST + API_LOGIN, SC_OK).asString();
            Assert.assertEquals(ParserJson.parser(response, "message"), "Password changed successfully! Please login again");
            Assert.assertEquals(ParserJson.parser(response1, "message"), AlertAPI.LOGIN_SUCCESS.getValue());
            deleteUser();
    }

    @TmsLink("5924630")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "negative test. Change password after first login with less than 7 characters")
    public void changePasswordAfterFirstLoginLessThan7CharsTest() throws SQLException {
            createUser();
            String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
            new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED);
            String response = new PostAdapters().post(setNewPassword("Num1"),
                    API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_BAD_REQUEST).asString();
            Assert.assertEquals(ParserJson.parser(response,"message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
            deleteUser();
    }

    @TmsLink("5924631")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "negative test. Change password after first login with more than 20 characters")
    public void changePasswordAfterFirstLoginMoreThan20CharsTest() throws SQLException {
            createUser();
            String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
            new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED);
            String response = new PostAdapters().post(setNewPassword("NumLcnd78554C23569712D1"),
                    API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_BAD_REQUEST).asString();
            Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
            deleteUser();
    }

    @TmsLink("5924632")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "negative test. Change password after first login with only letters")
    public void changePasswordAfterFirstLoginOnlyLettersTest() throws SQLException {
            createUser();
            String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
            new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED);
            String response = new PostAdapters().post(setNewPassword("NumLcndvS"),
                    API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_BAD_REQUEST).asString();
            Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
            deleteUser();
    }

    @TmsLink("5924633")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "negative test. Change password after first login with only numbers")
    public void changePasswordAfterFirstLoginOnlyNumbersTest() throws SQLException {
            createUser();
            String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
            new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED);
            String response = new PostAdapters().post(setNewPassword("569102561"),
                    API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_BAD_REQUEST).asString();
            Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
            deleteUser();
    }

    @TmsLink("5924634")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "negative test. Change password after first login with special characters")
    public void changePasswordAfterFirstLoginWithSpecialCharactersTest() throws SQLException {
            createUser();
            String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
            new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED);
            String response = new PostAdapters().post(setNewPassword("5691Lvd."),
                    API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_BAD_REQUEST).asString();
            Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
            deleteUser();
    }

    @TmsLink("5924635")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "negative test. Change password after first login with empty new password line")
    public void changePasswordAfterFirstLoginWithEmptyNewPasswordLineTest() throws SQLException {
            createUser();
            String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
            new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED);
            String response = new PostAdapters().post(setNewPassword(""),
                    API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_BAD_REQUEST).asString();
            Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
            deleteUser();
    }

    @TmsLink("5924636")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "negative test. Change password after first login with space new password line")
    public void changePasswordAfterFirstLoginWithSpaceNewPasswordLineTest() throws SQLException {
            createUser();
            String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
            new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED);
            String response = new PostAdapters().post(setNewPassword("Number1 "),
                    API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_BAD_REQUEST).asString();
            Assert.assertEquals(ParserJson.parser(response, "message"), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
            deleteUser();
    }

    @TmsLink("5924637")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "negative test. Re-login when you cancel the password change at the first login")
    public void changePasswordAfterFirstLoginReLoginTest() throws SQLException {
            createUser();
            String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
            new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED);
            String response = new PostAdapters().post(setSmsCode("1234"),
                    API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PRECONDITION_FAILED).asString();
            Assert.assertEquals(ParserJson.parser(response, "message"), "Required to change password on first login");
            deleteUser();
    }
}

