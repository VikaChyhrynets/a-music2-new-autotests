package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.http.Cookie;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.api.tests.LogoutTests.authKey;
import static by.andersen.amnbanking.data.AuthToken.getAuthLogin;
import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;
import static org.testng.Assert.assertEquals;

@Story("UC 1.3 - Password recovery")
public class PasswordRecoveryTests extends BaseTest {
    @TestRails(id = "C5911963")
    @Step("Send only letters in code confirmation for recovery password by passport, negative test")
    @Test(description = "negative test, only letters in code confirmation")
    public void enteringLettersInCodeConfirmationTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode("tugb"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5911967")
    @Step("Send special character in code confirmation / for recovery password by passport, negative test")
    @Test(description = "negative test, special character /")
    public void enteringSpecialCharacterInCodeConfirmationTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode("12/4"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5911881")
    @Step("Send an empty field code confirmation for recovery password by passport, negative test")
    @Test(description = "negative test, empty field for code confirmation")
    public void enteringWithEmptyFieldInCodeConfirmationTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(""),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5911791")
    @Step("Send wrong code confirmation for recovery password by passport 1 time, negative test")
    @Test(description = "negative test, wrong code confirmation")
    public void enteringWrongCodeInCodeConfirmation1TimeTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode("1235"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Invalid sms code");
        Assert.assertEquals(response.getFailsCount(), 1);
        new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 200).as(Response.class);
    }

    @TestRails(id = "C5911796")
    @Step("Send wrong code confirmation for recovery password by passport 2 time, negative test")
    @Test(description = "negative test,send wrong code confirmation by passport 2 times")
    public void enteringWrongCodeInCodeConfirmation2TimesTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        new PostAdapters().post(setSmsCode("1235"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 400).as(Response.class);
        Response response = new PostAdapters().post(setSmsCode("1235"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Invalid sms code");
        Assert.assertEquals(response.getFailsCount(), 2);
        new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 200).as(Response.class);
    }

    @TestRails(id = "C5924835")
    @Step("Send the correct code confirmation for recovery password by passport and change password, positive test")
    @Test(description = "positive test, change password by passport with valid data")
    public void changePasswordValidDateTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 200);
        Response response = new PostAdapters().post(setNewPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, login, 200).as(Response.class);
        Response response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                API_HOST + API_LOGIN, 200).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Password changed successfully! Please login again");
        Assert.assertEquals(response1.getMessage(), AlertAPI.LOGIN_SUCCESS.getValue());
    }

    @TestRails(id = "C5911960")
    @Step("Send wrong filter type for change password as type for change passport, negative test")
    @Test(description = "negative test, wrong filter type")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPassportTest() {
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSPORT"),
                API_HOST + SMS_CODE, authKey, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Invalid smsFilterType provided SMS_FOR_CHANGE_PASSPORT");
    }

    @TestRails(id = "C5911959")
    @Step("Send correct filter type for change password as type for change password, positive test")
    @Test(description = "positive test, send correct filter type for change password by passport")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPasswordTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSWORD"),
                API_HOST + SMS_CODE, login, 200).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Sms sent successfully");
    }

    @TestRails(id = "C5908792")
    @Step("Sending valid passport")
    @Test(description = "Trying to send valid passport")
    public void sendValidPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, 200).as(Response.class);
        assertEquals(resp.getMessage(), "Passport is valid. Sms sent successfully");
    }

    @TestRails(id = "C5909076")
    @Step("Sending valid, but unregistered passport")
    @Test(description = "Trying to send valid, but unregistered passport")
    public void sendUnregisteredPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "1111"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, 404).as(Response.class);
        assertEquals(resp.getMessage(), "This ID number is not registered. Please check the entered data or contact the bank");
    }

    @TestRails(id = "C5908983")
    @Step("Sending empty passport")
    @Test(description = "Trying to send empty passport")
    public void sendEmptyPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(""), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, 400).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @TestRails(id = "C5909034")
    @Step("Sending passport longer then 30 symbols")
    @Test(description = "Trying to send passport longer then 30 symbols")
    public void sendPassportWithMoreThenThirtySymbols() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "324567898765434567865432456"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, 400).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @TestRails(id = "C5909068")
    @Step("Sending passport with special symbols, like \"*\"")
    @Test(description = "Trying to send passport with special symbols, like \"*\"")
    public void sendPassportWithSymbols() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "*"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, 400).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @TestRails(id = "C5909070")
    @Step("Sending passport with only lower case letters")
    @Test(description = "Trying to send passport with only lower case letters")
    public void sendPassportWithLowerCase() {
        Response resp = new PostAdapters().post(setIDForPassRecovery("kv24535756"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, 400).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @TestRails(id = "C5911652")
    @Step("Sending valid SMS")
    @Test(description = "Trying to send valid SMS")
    public void sendValidPassportConfirmedWithSms() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        assertEquals(resp.getMessage(), "Change password code is correct");
    }

    @TestRails(id = "C5911654")
    @Step("Sending SMS shorter then 4 symbols")
    @Test(description = "Trying to send SMS shorter then 4 symbols")
    public void sendSmsWithLessThenFourDigits() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("123"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 400).as(Response.class);
        assertEquals(resp.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5911746")
    @Step("Sending SMS longer then 4 symbols")
    @Test(description = "Trying to send SMS longer then 4 symbols")
    public void sendSmsWithMoreThenFourDigits() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("12345"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 400).as(Response.class);
        assertEquals(resp.getMessage(), "Sms code contains invalid characters");
    }

    @TestRails(id = "C5923248")
    @Step("Sending verification SMS for another user through replacing cookie")
    @Test(description = "Trying to send SMS for another user through replacing cookie")
    public void sendSmsWithCookieReplacement() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response resp = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, replacedCookie, 412).as(Response.class);
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }

    @TestRails(id = "C5923249")
    @Step("Setting password for another user through replacing cookie")
    @Test(description = "Trying to set password for another user through replacing cookie")
    public void changingPasswordWithCookieReplacement() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, replacedCookie, 412).as(Response.class);
        assertEquals(checkSms.getMessage(), "Change password code is correct");
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }

    @TestRails(id = "C5924638")
    @Step("Password recovery without changing the password at first login, positive test")
    @Test(description = "Password recovery without changing the password at first login, positive test")
    public void passwordRecoveryWithoutChangingPasswordFirstLoginTest() throws SQLException {
        createUser();
        Response response = new PostAdapters().post(setPassportForRegistration("PVS153215DSV"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, 412).as(Response.class);
        assertEquals(response.getMessage(), "The one-time password cannot be changed via password recovery." +
                " Please contact the bank in case of problems with the one-time password");
        deleteUser();
    }

    @TestRails(id = "C5924835")
    @Step("Setting a valid password")
    @Test(description = "Trying to set valid password")
    public void sendValidPassword() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, 200).as(Response.class);
        assertEquals(checkSms.getMessage(), "Change password code is correct");
        assertEquals(resp.getMessage(), "Password changed successfully! Please login again");
    }

    @TestRails(id = "C5924837")
    @Step("Setting a password shorter then 7 symbols")
    @Test(description = "Trying to set password shorter then 7 symbols")
    public void sendPasswordWithLessThen7() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8Rvjsi"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, 400).as(Response.class);
        assertEquals(checkSms.getMessage(), "Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924838")
    @Step("Setting a password longer then 20 symbols")
    @Test(description = "Trying to set password longer then 20 symbols")
    public void sendPasswordWithMoreThen20() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8RvjsisKKKfhdskjf23546hkdsjf"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, 400).as(Response.class);
        assertEquals(checkSms.getMessage(), "Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924842")
    @Step("Setting a password without numbers")
    @Test(description = "Trying to set password without numbers")
    public void sendPasswordWithOnlyLetters() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("KJGJuygggfFTYF"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, 400).as(Response.class);
        assertEquals(checkSms.getMessage(), "Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924843")
    @Step("Setting a password with only numbers")
    @Test(description = "Trying to set password with only numbers")
    public void sendPasswordWithOnlyNumbers() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("82485694395"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, 400).as(Response.class);
        assertEquals(checkSms.getMessage(), "Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924844")
    @Step("Setting a password with special symbols, like \"*\"")
    @Test(description = "Trying to set password with special symbols, like \"*\"")
    public void sendPasswordWithSymbols() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + "*"), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, 400).as(Response.class);
        assertEquals(checkSms.getMessage(), "Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924846")
    @Step("Setting an empty password during recovery")
    @Test(description = "Sending empty password during recovery")
    public void sendEmptyPassword() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(""), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, 400).as(Response.class);
        assertEquals(checkSms.getMessage(), "Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924849")
    @Step("Sending passport with space")
    @Test(description = "Sending passport with space")
    public void sendPasswordWithSpace() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, 200).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + " "), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, 400).as(Response.class);
        assertEquals(checkSms.getMessage(), "Change password code is correct");
        assertEquals(resp.getMessage(), "Invalid characters in password");
    }

    @TestRails(id = "C5924850")
    @Step("Changing password without passport checking")
    @Test(description = "Changing password without passport checking")
    public void changePasswordWithoutPassportCheck() {
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG), API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, 412).as(Response.class);
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }

    @TestRails(id = "C593793")
    @Step("Sending password recovery code again when ban hasn't expired, negative test")
    @Test(description = "Sending password recovery code again when ban hasn't expired, negative test")
    public void sendPasswordRecoveryCodeAgainWhenBanHasNotExpired() throws SQLException {
        Cookie login = getAuthLogin(PASSPORT_REG);
        for (int i = 0; i < 3; i++) {
            new PostAdapters().post(setSmsCode(WRONG_SMS_CODE), API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 400);
        }
        Response response = new PostAdapters().post(setSmsCode(WRONG_SMS_CODE),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, 423).as(Response.class);
        assertEquals(response.getMessage(), "Ban time is not over yet...");
        assertEquals(new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSWORD"),
                API_HOST + SMS_CODE, login, 423).as(Response.class).getMessage(),
                "Ban time is not over yet...");
    }
}