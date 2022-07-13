package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.data.SmsVerificationData;
import by.andersen.amnbanking.data.UsersData;
import by.andersen.amnbanking.utils.DataProviderTests;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.ParserJson;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

import io.qameta.allure.TmsLinks;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PERMANENT_REDIRECT;

@Epic("Epic 1: Registration and authorization")
public class ChangePasswordAfterFirstLoginTests extends BaseAPITest {
    @Override
    @BeforeMethod
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser(UsersData.USER_EMINEM79.getUser().getLogin());
    }

    @Override
    public void createUser() {
        new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration(
                        UsersData.USER_EMINEM79.getUser().getLogin(),
                        UsersData.USER_EMINEM79.getUser().getPassword(),
                        UsersData.USER_EMINEM79.getUser().getPassport(),
                        UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK);
    }

    @TmsLink("5923855")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "positive test. Change password after first login")
    public void changePasswordAfterFirstLoginValidDateTest() {
        createUser();
        String authTokenChangePassword = getAuthToken(UsersData.USER_EMINEM79.getUser().getLogin(),
                UsersData.USER_EMINEM79.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()), API_HOST + API_SESSIONCODE,
                authTokenChangePassword, SC_PERMANENT_REDIRECT);
        String response = new PostAdapters().post(setNewPassword(UsersData.USER_EM79_NEW_PASS.getUser().getPassword()),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_OK).asString();
        String response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        (UsersData.USER_EMINEM79.getUser().getLogin(),
                                UsersData.USER_EM79_NEW_PASS.getUser().getPassword()),
                API_HOST + API_LOGIN, SC_OK).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"),
                AlertAPI.SUCCESSFUL_PASSWORD_CHANGED.getValue());
        Assert.assertEquals(ParserJson.parser(response1, "message"), AlertAPI.LOGIN_SUCCESS.getValue());
    }

    @TmsLinks(value = {@TmsLink("5924630"), @TmsLink("5924631"), @TmsLink("5924632"), @TmsLink("5924633"),
            @TmsLink("5924634"), @TmsLink("5924635"), @TmsLink("5924636")})
    @Story("UC 1.5 - Changing password on first login")
    @Test(dataProvider = "ChangePasswordAfter1LoginInvalidPass", dataProviderClass = DataProviderTests.class,
            description = "Change password after first login with invalid password, negative test.")
    public void changePasswordAfterFirstLoginLessThan7CharsTest(String newPass) {
        createUser();
        String authTokenChangePassword = getAuthToken(UsersData.USER_EMINEM79.getUser().getLogin(),
                UsersData.USER_EMINEM79.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()), API_HOST + API_SESSIONCODE,
                authTokenChangePassword, SC_PERMANENT_REDIRECT);
        String response = new PostAdapters().post(setNewPassword(newPass),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_BAD_REQUEST).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"),
                AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TmsLink("5924637")
    @Story("UC 1.5 - Changing password on first login")
    @Test(description = "negative test. Re-login when you cancel the password change at the first login")
    public void changePasswordAfterFirstLoginReLoginTest() {
        createUser();
        String authTokenChangePassword = getAuthToken(UsersData.USER_EMINEM79.getUser().getLogin(),
                UsersData.USER_EMINEM79.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PERMANENT_REDIRECT);
        String response = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PERMANENT_REDIRECT).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"),
                AlertAPI.REQUIRED_PASSWORD.getValue());
    }
}

