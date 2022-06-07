package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.api.tests.LogoutTests.authKey;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;

public class PasswordRecoveryTests {

    @BeforeMethod
    public void createUser() {
        new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem79", "111Gv5dvvf511", "PVS153215DSV"),
                API_HOST + API_REGISTRATION);
    }


    @AfterMethod
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
    }

    @TestRails(id = "C5911963")
    @Test
    public void enteringLettersInCodeConfirmationTest() {
        Response response = new PostAdapters().postWithCookieLogin(PASSPORT_REG, setSmsCode("tugb"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5911967")
    @Test
    public void enteringSpecialCharactersInCodeConfirmationTest() {
        Response response = new PostAdapters().postWithCookieLogin(PASSPORT_REG, setSmsCode("12/4"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5911881")
    @Test
    public void enteringWithEmptyFieldInCodeConfirmationTest() {
        Response response = new PostAdapters().postWithCookieLogin(PASSPORT_REG, setSmsCode(""),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5911791")
    @Test
    public void enteringWrongCodeInCodeConfirmation1TimeTest() {
        Response response = new PostAdapters().postWithCookieLogin("PVS153215DSV", setSmsCode("1235"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Invalid sms code");
        Assert.assertEquals(response.getFailsCount(), 1);
    }

    @TestRails(id = "C5911796")
    @Test
    public void enteringWrongCodeInCodeConfirmation2TimesTest() {
        new PostAdapters().postWithStaticCookieLogin(setSmsCode("1235"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS).as(Response.class);
        Response response = new PostAdapters().postWithStaticCookieLogin(setSmsCode("1235"),
                    API_HOST + CHANGE_PASSWORD + CHECK_SMS).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Invalid sms code");
        Assert.assertEquals(response.getFailsCount(), 2);
        new PostAdapters().postWithStaticCookieLogin(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS).as(Response.class);
    }

    @Test
    public void changePasswordValidDateTest() {
        new PostAdapters().postWithStaticCookieLogin(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS);
        Response response = new PostAdapters().postWithStaticCookieLogin(setNewPassword("8Lnj5c1235s"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Password changed successfully! Please login again");
    }

    @Test(description = "draft")
    public void sendValidSmsCodeWithoutCookieLoginTest() {
        Response response = new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS).as(Response.class);
        Assert.assertEquals(response.getMessage(), "User has not been verified yet");
    }

    @TestRails(id = "C5911960")
    @Test
    public void sendConfirmationCodeAgainWithInvalidDataFilterPassportTest() {
        Response response = new PostAdapters().postAuthWithSessionCode(authKey, setFilterType("SMS_FOR_CHANGE_PASSPORT"),
                 API_HOST + SMS_CODE).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Invalid smsFilterType provided SMS_FOR_CHANGE_PASSPORT");
    }

    @TestRails(id = "C5911959")
    @Test
    public void sendConfirmationCodeAgainWithInvalidDataFilterPasswordTest() {
        Response response = new PostAdapters().postWithStaticCookieLogin(setFilterType("SMS_FOR_CHANGE_PASSWORD"),
                API_HOST + SMS_CODE).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms sent successfully");
    }
}