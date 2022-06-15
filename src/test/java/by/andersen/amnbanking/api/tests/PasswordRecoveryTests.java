package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Cookie;
import java.sql.SQLException;

import static by.andersen.amnbanking.data.AuthToken.getAuthLogin;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;
import static org.testng.Assert.assertEquals;
import static by.andersen.amnbanking.api.tests.LogoutTests.authKey;

@Story("UC 1.3 - Password recovery")
public class PasswordRecoveryTests extends BaseTest{
    @TestRails(id = "C5911963")
    @Step("Send only letters in code confirmation for recovery password by passport, negative test")
    @Test(description = "negative test, only letters in code confirmation")
    public void enteringLettersInCodeConfirmationTest() throws SQLException {
        createUser();
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode("tugb"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms code contains invalid characters");
        deleteUser();
    }

    @TestRails(id = "C5911967")
    @Step("Send special character in code confirmation / for recovery password by passport, negative test")
    @Test(description = "negative test, special character /")
    public void enteringSpecialCharacterInCodeConfirmationTest() throws SQLException {
        createUser();
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode("12/4"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms code contains invalid characters");
        deleteUser();
    }

    @TestRails(id = "C5911881")
    @Step("Send an empty field code confirmation for recovery password by passport, negative test")
    @Test(description = "negative test, empty field for code confirmation")
    public void enteringWithEmptyFieldInCodeConfirmationTest() throws SQLException {
        createUser();
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(""),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms code contains invalid characters");
        deleteUser();
    }

    @TestRails(id = "C5911791")
    @Step("Send wrong code confirmation for recovery password by passport 1 time, negative test")
    @Test(description = "negative test, wrong code confirmation")
    public void enteringWrongCodeInCodeConfirmation1TimeTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode("1235"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Invalid sms code");
        Assert.assertEquals(response.getFailsCount(), 1);
        new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login).as(Response.class);
    }

    @TestRails(id = "C5911796")
    @Step("Send wrong code confirmation for recovery password by passport 2 time, negative test")
    @Test(description = "negative test,send wrong code confirmation by passport 2 times")
    public void enteringWrongCodeInCodeConfirmation2TimesTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        new PostAdapters().post(setSmsCode("1235"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS,login).as(Response.class);
        Response response = new PostAdapters().post(setSmsCode("1235"),
                    API_HOST + CHANGE_PASSWORD + CHECK_SMS, login).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Invalid sms code");
        Assert.assertEquals(response.getFailsCount(), 2);
        new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login).as(Response.class);
    }

    @TestRails(id="C5924835")
    @Step("Send the correct code confirmation for recovery password by passport and change password, positive test")
    @Test(description = "positive test, change password by passport with valid data")
    public void changePasswordValidDateTest() throws SQLException {
        createUser();
        Cookie login = getAuthLogin(PASSPORT_REG);
        new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login);
        Response response = new PostAdapters().post(setNewPassword("Number1"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, login).as(Response.class);
        Response response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin("Celine715", "Number1"),
                API_HOST + API_LOGIN).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Password changed successfully! Please login again");
        Assert.assertEquals(response1.getMessage(), AlertAPI.LOGIN_SUCCESS.getValue());
        deleteUser();
    }

    @TestRails(id = "C5911960")
    @Step("Send wrong filter type for change password as type for change passport, negative test")
    @Test(description = "negative test, wrong filter type")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPassportTest() throws SQLException {
        createUser();
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSPORT"),
                 API_HOST + SMS_CODE, authKey).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Invalid smsFilterType provided SMS_FOR_CHANGE_PASSPORT");
        deleteUser();
    }

    @TestRails(id = "C5911959")
    @Step("Send correct filter type for change password as type for change password, positive test")
    @Test(description = "positive test, send correct filter type for change password by passport")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPasswordTest() throws SQLException {
        createUser();
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSWORD"),
                API_HOST + SMS_CODE, login).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms sent successfully");
        deleteUser();
    }

    @TestRails(id = "C5908792")
    @Test(description = "Trying to send valid passport")
    public void sendValidPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Passport is valid. Sms sent successfully");
    }

    @TestRails(id = "C5909076")
    @Test(description = "Trying to send valid, but unregistered passport")
    public void sendUnregisteredPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "1111"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "This ID number is not registered. Please check the entered data or contact the bank");
    }

    @TestRails(id = "C5908983")
    @Test(description = "Trying to send empty passport")
    public void sendEmptyPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(""), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @TestRails(id = "C5909034")
    @Test(description = "Trying to send passport longer then 30 symbols")
    public void sendPassportWithMoreThenThirtySymbols(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "324567898765434567865432456"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @TestRails(id = "C5909068")
    @Test(description = "Trying to send passport with special symbols, like \"*\"")
    public void sendPassportWithSymbols(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "*"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @TestRails(id = "C5909070")
    @Test(description = "Trying to send passport with only lower case letters")
    public void sendPassportWithLowerCase(){
        Response resp = new PostAdapters().post(setIDForPassRecovery("kv24535756"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @TestRails(id = "C5911652")
    @Test(description = "Trying to send valid SMS")
    public void sendValidPassportConfirmedWithSms(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        assertEquals(resp.getMessage(), "Change password code is correct");
    }

    @TestRails(id = "C5911654")
    @Test(description = "Trying to send SMS shorter then 4 symbols")
    public void sendSmsWithLessThenFourDigits(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("123"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        assertEquals(resp.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5911746")
    @Test(description = "Trying to send SMS longer then 4 symbols")
    public void sendSmsWithMoreThenFourDigits(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("12345"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        assertEquals(resp.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5923248")
    @Test(description = "Trying to send SMS for another user through replacing cookie")
    public void sendSmsWithCookieReplacement(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response resp = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, replacedCookie).as(Response.class);
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }

    @TestRails(id = "C5923249")
    @Test(description = "Trying to set password for another user through replacing cookie")
    public void changingPasswordWithCookieReplacement(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD,replacedCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }

    @TestRails(id = "C5924835")
    @Test(description = "Trying to set valid password")
    public void sendValidPassword(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD,loginAsCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "Password changed successfully! Please login again");
    }

    @TestRails(id = "C5924837")
    @Test(description = "Trying to set password shorter then 7 symbols")
    public void sendPasswordWithLessThen7(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8Rvjsi"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD,loginAsCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924838")
    @Test(description = "Trying to set password longer then 20 symbols")
    public void sendPasswordWithMoreThen20(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8RvjsisKKKfhdskjf23546hkdsjf"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD,loginAsCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924842")
    @Test(description = "Trying to set password without numbers")
    public void sendPasswordWithOnlyLetters(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("KJGJuygggfFTYF"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD,loginAsCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924843")
    @Test(description = "Trying to set password with only numbers")
    public void sendPasswordWithOnlyNumbers(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("82485694395"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD,loginAsCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924844")
    @Test(description = "Trying to set password with special symbols, like \"*\"")
    public void sendPasswordWithSymbols(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + "*"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD,loginAsCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924846")
    @Test(description = "Sending empty password during recovery")
    public void sendEmptyPassword(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + "*"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD,loginAsCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924849")
    @Test(description = "Sending passport with space")
    public void sendPasswordWithSpace(){
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + " "), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD,loginAsCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924850")
    @Test(description = "Changing password without passport checking")
    public void changePasswordWithoutPassportCheck(){
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD).as(Response.class);
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }
}
