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
import io.restassured.http.Cookie;
import java.sql.SQLException;

import static by.andersen.amnbanking.data.AuthToken.checkPassportAndGetCookie;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;
import static org.testng.Assert.assertEquals;
import static by.andersen.amnbanking.api.tests.LogoutTests.authKey;

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

    @Test
    @TestRails(id = "C5908792")
    public void sendValidPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Passport is valid. Sms sent successfully");
    }

    @Test
    @TestRails(id = "C5909076")
    public void sendUnregisteredPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "1111"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "This ID number is not registered. Please check the entered data or contact the bank");
    }

    @Test
    @TestRails(id = "C5908983")
    public void sendEmptyPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(""), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "C5909034")
    public void sendPassportWithMoreThenThirtySymbols(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "324567898765434567865432456"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "C5909068")
    public void sendPassportWithSymbols(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "*"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "C5909070")
    public void sendPassportWithLowerCase(){
        Response resp = new PostAdapters().post(setIDForPassRecovery("kv24535756"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "C5911652")
    public void sendValidPassportConfirmedWithSms(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHECK_SMS, loginAsCookie).as(Response.class);
        assertEquals(resp.getMessage(), "Change password code is correct");
    }

    @Test
    @TestRails(id = "C5911654")
    public void sendSmsWithLessThenFourDigits(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("123"), API_HOST + CHECK_SMS, loginAsCookie).as(Response.class);
        assertEquals(resp.getMessage(), "Sms code contains invalid characters");
    }

    @Test
    @TestRails(id = "C5911746")
    public void sendSmsWithMoreThenFourDigits(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("12345"), API_HOST + CHECK_SMS, loginAsCookie).as(Response.class);
        assertEquals(resp.getMessage(), "Sms code contains invalid characters");
    }

    @Test
    @TestRails(id = "C5923248")
    public void sendSmsWithCookieReplacement(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response resp = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHECK_SMS, replacedCookie).as(Response.class);
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }

    @Test
    @TestRails(id = "C5923249")
    public void changingPasswordWithCookieReplacement(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG), API_HOST + NEW_PASSWORD,replacedCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }
}
