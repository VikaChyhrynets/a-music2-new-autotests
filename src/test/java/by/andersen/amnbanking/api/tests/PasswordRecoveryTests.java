package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.data.SmsVerificationData;
import by.andersen.amnbanking.data.UsersData;
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
import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setFilterType;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setIDForPassRecovery;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setPassportForRegistration;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.*;
import static org.testng.Assert.assertEquals;

@Story("UC 1.3 - Password recovery")
public class PasswordRecoveryTests extends BaseAPITest {
    @Override
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
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

    @TestRails(id = "C5911963")
    @Step("Send only letters in code confirmation for recovery password by passport, negative test")
    @Test(description = "negative test, only letters in code confirmation")
    public void enteringLettersInCodeConfirmationTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_SMALL_LETTERS.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @TestRails(id = "C5911967")
    @Step("Send special character in code confirmation / for recovery password by passport, negative test")
    @Test(description = "negative test, special character /")
    public void enteringSpecialCharacterInCodeConfirmationTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_SLASH_MIDDLE.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @TestRails(id = "C5911881")
    @Step("Send an empty field code confirmation for recovery password by passport, negative test")
    @Test(description = "negative test, empty field for code confirmation")
    public void enteringWithEmptyFieldInCodeConfirmationTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(""),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @TestRails(id = "C5911791")
    @Step("Send wrong code confirmation for recovery password by passport 1 time, negative test")
    @Test(description = "negative test, wrong code confirmation")
    public void enteringWrongCodeInCodeConfirmation1TimeTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(SmsVerificationData.SMS_INVALID.getValue(),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.INVALID_SMS.getValue());
        Assert.assertEquals(response.getFailsCount(), 1);
        new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_OK).as(Response.class);
    }

    @TestRails(id = "C5911796")
    @Step("Send wrong code confirmation for recovery password by passport 2 time, negative test")
    @Test(description = "negative test,send wrong code confirmation by passport 2 times")
    public void enteringWrongCodeInCodeConfirmation2TimesTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_INVALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Response response = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_INVALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.INVALID_SMS.getValue());
        Assert.assertEquals(response.getFailsCount(), 2);
        new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_OK).as(Response.class);
    }

    @TestRails(id = "C5924835")
    @Step("Send the correct code confirmation for recovery password by passport and change password, positive test")
    @Test(description = "positive test, change password by passport with valid data")
    public void changePasswordValidDateTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_OK);
        Response response = new PostAdapters().post(setNewPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, login, SC_OK).as(Response.class);
        Response response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        (LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                API_HOST + API_LOGIN, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.SUCCESSFUL_PASSWORD_CHANGED.getValue());
        Assert.assertEquals(response1.getMessage(), AlertAPI.LOGIN_SUCCESS.getValue());
    }

    @TestRails(id = "C5911960")
    @Step("Send wrong filter type for change password as type for change passport, negative test")
    @Test(description = "negative test, wrong filter type")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPassportTest() {
        String authToken = getAuthToken(UsersData.USER_MALEFICENT.getUser().getLogin(),
                UsersData.USER_MALEFICENT.getUser().getPassword());
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSPORT"),
                API_HOST + SMS_CODE, authToken, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.INVALID_SMS_FILTER.getValue());
    }

    @TestRails(id = "C5911959")
    @Step("Send correct filter type for change password as type for change password, positive test")
    @Test(description = "positive test, send correct filter type for change password by passport")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPasswordTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSWORD"),
                API_HOST + SMS_CODE, login, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.SMS_SENT_SUCCESSFULLY.getValue());
    }

    @TestRails(id = "C5908792")
    @Step("Sending valid passport")
    @Test(description = "Trying to send valid passport")
    public void sendValidPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_OK).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.PASSPORT_IS_VALID.getValue());
    }

    @TestRails(id = "C5909076")
    @Step("Sending valid, but unregistered passport")
    @Test(description = "Trying to send valid, but unregistered passport")
    public void sendUnregisteredPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "1111"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_NOT_FOUND).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.NOT_REGISTER_ID.getValue());
    }

    @TestRails(id = "C5908983")
    @Step("Sending empty passport")
    @Test(description = "Trying to send empty passport")
    public void sendEmptyPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(""),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS.getValue());
    }

    @TestRails(id = "C5909034")
    @Step("Sending passport longer then 30 symbols")
    @Test(description = "Trying to send passport longer then 30 symbols")
    public void sendPassportWithMoreThenThirtySymbols() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "324567898765434567865432456"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS.getValue());
    }

    @TestRails(id = "C5909068")
    @Step("Sending passport with special symbols, like \"*\"")
    @Test(description = "Trying to send passport with special symbols, like \"*\"")
    public void sendPassportWithSymbols() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "*"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS.getValue());
    }

    @TestRails(id = "C5909070")
    @Step("Sending passport with only lower case letters")
    @Test(description = "Trying to send passport with only lower case letters")
    public void sendPassportWithLowerCase() {
        Response resp = new PostAdapters().post(setIDForPassRecovery("kv24535756"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS.getValue());
    }

    @TestRails(id = "C5911652")
    @Step("Sending valid SMS")
    @Test(description = "Trying to send valid SMS")
    public void sendValidPassportConfirmedWithSms() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
    }

    @TestRails(id = "C5911654")
    @Step("Sending SMS shorter then 4 symbols")
    @Test(description = "Trying to send SMS shorter then 4 symbols")
    public void sendSmsWithLessThenFourDigits() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_3_SYMBOLS.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @TestRails(id = "C5911746")
    @Step("Sending SMS longer then 4 symbols")
    @Test(description = "Trying to send SMS longer then 4 symbols")
    public void sendSmsWithMoreThenFourDigits() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_5_SYMBOLS.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @TestRails(id = "C5923248")
    @Step("Sending verification SMS for another user through replacing cookie")
    @Test(description = "Trying to send SMS for another user through replacing cookie")
    public void sendSmsWithCookieReplacement() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, replacedCookie, SC_PRECONDITION_FAILED).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.USER_NOT_VERIFIED.getValue());
    }

    @TestRails(id = "C5923249")
    @Step("Setting password for another user through replacing cookie")
    @Test(description = "Trying to set password for another user through replacing cookie")
    public void changingPasswordWithCookieReplacement() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response checkSms = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, replacedCookie, SC_PRECONDITION_FAILED).as(Response.class);
        assertEquals(checkSms.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
        assertEquals(resp.getMessage(), AlertAPI.USER_NOT_VERIFIED.getValue());
    }

    @TestRails(id = "C5924638")
    @Step("Password recovery without changing the password at first login, positive test")
    @Test(description = "Password recovery without changing the password at first login, positive test")
    public void passwordRecoveryWithoutChangingPasswordFirstLoginTest() throws SQLException {
        try {
            createUser();
            Response response = new PostAdapters().post(setPassportForRegistration("PVS153215DSV"),
                    API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_PRECONDITION_FAILED).as(Response.class);
            assertEquals(response.getMessage(), AlertAPI.FAILED_CHANGE_PASSWORD_FROM_BANK.getValue());
        } finally {
            deleteUser();
        }
    }

    @TestRails(id = "C5924835")
    @Step("Setting a valid password")
    @Test(description = "Trying to set valid password")
    public void sendValidPassword() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_OK).as(Response.class);
        assertEquals(checkSms.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
        assertEquals(resp.getMessage(), AlertAPI.SUCCESSFUL_PASSWORD_CHANGED.getValue());
    }

    @TestRails(id = "C5924837")
    @Step("Setting a password shorter then 7 symbols")
    @Test(description = "Trying to set password shorter then 7 symbols")
    public void sendPasswordWithLessThen7() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8Rvjsi"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
        assertEquals(resp.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924838")
    @Step("Setting a password longer then 20 symbols")
    @Test(description = "Trying to set password longer then 20 symbols")
    public void sendPasswordWithMoreThen20() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8RvjsisKKKfhdskjf23546hkdsjf"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
        assertEquals(resp.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924842")
    @Step("Setting a password without numbers")
    @Test(description = "Trying to set password without numbers")
    public void sendPasswordWithOnlyLetters() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("KJGJuygggfFTYF"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
        assertEquals(resp.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924843")
    @Step("Setting a password with only numbers")
    @Test(description = "Trying to set password with only numbers")
    public void sendPasswordWithOnlyNumbers() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("82485694395"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
        assertEquals(resp.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924844")
    @Step("Setting a password with special symbols, like \"*\"")
    @Test(description = "Trying to set password with special symbols, like \"*\"")
    public void sendPasswordWithSymbols() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + "*"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
        assertEquals(resp.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924846")
    @Step("Setting an empty password during recovery")
    @Test(description = "Sending empty password during recovery")
    public void sendEmptyPassword() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(""),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
        assertEquals(resp.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924849")
    @Step("Sending passport with space")
    @Test(description = "Sending passport with space")
    public void sendPasswordWithSpace() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode("1234"),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + " "),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), AlertAPI.CHANGE_PASSWORD_CODE_CORRECT.getValue());
        assertEquals(resp.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSWORD.getValue());
    }

    @TestRails(id = "C5924850")
    @Step("Changing password without passport checking")
    @Test(description = "Changing password without passport checking")
    public void changePasswordWithoutPassportCheck() {
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, SC_PRECONDITION_FAILED).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.USER_NOT_VERIFIED.getValue());
    }
}
