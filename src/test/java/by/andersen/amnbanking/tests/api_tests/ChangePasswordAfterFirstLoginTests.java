package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.DataProviderTests;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.ParserJson;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.REGISTRATION_FAILED_USER_PASSWORD;
import static by.andersen.amnbanking.data.AlertAPI.REQUIRED_PASSWORD;
import static by.andersen.amnbanking.data.AuthToken.loginAndGetBearerToken;
import static by.andersen.amnbanking.data.DataUrls.API_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_VALID;
import static by.andersen.amnbanking.data.SuccessfulMessages.LOGIN_SUCCESS;
import static by.andersen.amnbanking.data.SuccessfulMessages.SUCCESSFUL_PASSWORD_CHANGED;
import static by.andersen.amnbanking.data.UsersData.USER_ONE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PERMANENT_REDIRECT;

@Listeners(UserDeleteListener.class)
@Epic("E-1. Registration and authorization")
public class ChangePasswordAfterFirstLoginTests extends BaseAPITest {


    @TmsLink("5923855")
    @Story("UC-1.5 Changing password on first login")
    @Test(description = "positive test. Change password after first login")
    public void changePasswordAfterFirstLoginValidDateTest() throws SQLException {
        createUser();
        String authTokenChangePassword = loginAndGetBearerToken(USER_ONE.getUser().getLogin(),
                USER_ONE.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PERMANENT_REDIRECT);
        String response = new PostAdapters().post(setNewPassword(USER_ONE.getUser().getPassword()),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_OK).asString();
        String response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        (USER_ONE.getUser().getLogin(),
                                USER_ONE.getUser().getPassword()),
                API_HOST + API_LOGIN, SC_OK).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), SUCCESSFUL_PASSWORD_CHANGED);
        Assert.assertEquals(ParserJson.parser(response1, "message"), LOGIN_SUCCESS);
        deleteUser();
    }

    @TmsLinks(value = {@TmsLink("5924630"), @TmsLink("5924631"), @TmsLink("5924632"), @TmsLink("5924633"),
            @TmsLink("5924634"), @TmsLink("5924635"), @TmsLink("5924636")})
    @Story("UC-1.5 Changing password on first login")
    @Test(dataProvider = "ChangePasswordAfter1LoginInvalidPass", dataProviderClass = DataProviderTests.class,
            description = "Change password after first login with invalid password, negative test.")
    public void changePasswordAfterFirstLoginLessThan7CharsTest(String newPass) throws SQLException {
        createUser();
        String authTokenChangePassword = loginAndGetBearerToken(USER_ONE.getUser().getLogin(),
                USER_ONE.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SMS_VALID.getSms()), API_HOST + API_SESSIONCODE,
                authTokenChangePassword, SC_PERMANENT_REDIRECT);
        String response = new PostAdapters().post(setNewPassword(newPass),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_BAD_REQUEST).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), REGISTRATION_FAILED_USER_PASSWORD);
        deleteUser();
    }

    @TmsLink("5924637")
    @Story("UC-1.5 Changing password on first login")
    @Test(description = "negative test. Re-login when you cancel the password change at the first login")
    public void changePasswordAfterFirstLoginReLoginTest() throws SQLException {
        createUser();
        String authTokenChangePassword = loginAndGetBearerToken(USER_ONE.getUser().getLogin(),
                USER_ONE.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PERMANENT_REDIRECT);
        String response = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PERMANENT_REDIRECT).asString();
        Assert.assertEquals(ParserJson.parser(response, "message"), REQUIRED_PASSWORD);
        deleteUser();
    }
}

