package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.data.UsersData;
import by.andersen.amnbanking.jsonBody.Response;
import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.http.Cookie;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.*;
import static by.andersen.amnbanking.data.AuthToken.getAuthLogin;
import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.API_LOGOUT;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.CHECK_PASSPORT;
import static by.andersen.amnbanking.data.DataUrls.CHECK_SMS;
import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.NEW_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.SMS_CODE;
import static by.andersen.amnbanking.data.DataUrls.USER_SESSION_CODE_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.WRONG_SMS_CODE;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_3_SYMBOLS;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_5_SYMBOLS;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_INVALID;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_SLASH_MIDDLE;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_SMALL_LETTERS;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_VALID;
import static by.andersen.amnbanking.data.SuccessfulMessages.CHANGE_PASSWORD_CODE_CORRECT;
import static by.andersen.amnbanking.data.SuccessfulMessages.LOGIN_SUCCESS;
import static by.andersen.amnbanking.data.SuccessfulMessages.PASSPORT_IS_VALID;
import static by.andersen.amnbanking.data.SuccessfulMessages.SMS_FOR_CHANGE_PASSWORD;
import static by.andersen.amnbanking.data.SuccessfulMessages.SMS_SENT_SUCCESSFULLY;
import static by.andersen.amnbanking.data.SuccessfulMessages.SUCCESSFUL_PASSWORD_CHANGED;
import static by.andersen.amnbanking.data.UsersData.USER_0NE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setFilterType;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setIDForPassRecovery;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setJsonObjectForRegistrationAndLogin;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setPassportForRegistration;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.*;
import static org.testng.Assert.assertEquals;

@Story("UC 1.3 - Password recovery")
@Listeners(UserDeleteListener.class)
public class PasswordRecoveryTests extends BaseAPITest {

    @TestRails(id = "C5911963")
    @Step("Send only letters in code confirmation for recovery password by passport, negative test")
    @Test(description = "negative test, only letters in code confirmation")

    public void enteringLettersInCodeConfirmationTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(SMS_SMALL_LETTERS.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), SMS_CODE_INVALID);
    }

    @TestRails(id = "C5911967")
    @Step("Send special character in code confirmation / for recovery password by passport, negative test")
    @Test(description = "negative test, special character /")
    public void enteringSpecialCharacterInCodeConfirmationTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(SMS_SLASH_MIDDLE.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), SMS_CODE_INVALID);
    }

    @TestRails(id = "C5911881")
    @Step("Send an empty field code confirmation for recovery password by passport, negative test")
    @Test(description = "negative test, empty field for code confirmation")
    public void enteringWithEmptyFieldInCodeConfirmationTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(""),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), SMS_CODE_INVALID);
    }

    @TestRails(id = "C5911791")
    @Step("Send wrong code confirmation for recovery password by passport 1 time, negative test")
    @Test(description = "negative test, wrong code confirmation")
    public void enteringWrongCodeInCodeConfirmation1TimeTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(SMS_INVALID.getValue(),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), INVALID_SMS);
        Assert.assertEquals(response.getFailsCount(), 1);
        new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_OK).as(Response.class);
    }

    @TestRails(id = "C5911796")
    @Step("Send wrong code confirmation for recovery password by passport 2 time, negative test")
    @Test(description = "negative test,send wrong code confirmation by passport 2 times")
    public void enteringWrongCodeInCodeConfirmation2TimesTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        new PostAdapters().post(setSmsCode(SMS_INVALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Response response = new PostAdapters().post(setSmsCode(SMS_INVALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), INVALID_SMS);
        Assert.assertEquals(response.getFailsCount(), 2);
        new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_OK).as(Response.class);
    }

    @TestRails(id = "C5924835")
    @Step("Send the correct code confirmation for recovery password by passport and change password, positive test")
    @Test(description = "positive test, change password by passport with valid data")
    public void changePasswordValidDateTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_OK);
        Response response = new PostAdapters().post(setNewPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, login, SC_OK).as(Response.class);
        Response response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        (LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                API_HOST + API_LOGIN, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), SUCCESSFUL_PASSWORD_CHANGED);
        Assert.assertEquals(response1.getMessage(), LOGIN_SUCCESS);
    }

    @TestRails(id = "C5911960")
    @Step("Send wrong filter type for change password as type for change passport, negative test")
    @Test(description = "negative test, wrong filter type")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPassportTest() {
        String authToken = getAuthToken(UsersData.USER_MALEFICENT.getUser().getLogin(),
                UsersData.USER_MALEFICENT.getUser().getPassword());
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSPORT"),
                API_HOST + SMS_CODE, authToken, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.INVALID_SMS_FILTER);
    }

    @TestRails(id = "C5911959")
    @Step("Send correct filter type for change password as type for change password, positive test")
    @Test(description = "positive test, send correct filter type for change password by passport")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPasswordTest() {
        Cookie login = getAuthLogin(PASSPORT_REG);
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSWORD"),
                API_HOST + SMS_CODE, login, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), SMS_SENT_SUCCESSFULLY);
    }

    @TestRails(id = "C5908792")
    @Step("Sending valid passport")
    @Test(description = "Trying to send valid passport")
    public void sendValidPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_OK).as(Response.class);
        assertEquals(resp.getMessage(), PASSPORT_IS_VALID);
    }

    @TestRails(id = "C5909076")
    @Step("Sending valid, but unregistered passport")
    @Test(description = "Trying to send valid, but unregistered passport")
    public void sendUnregisteredPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "1111"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_NOT_FOUND).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.NOT_REGISTER_ID);
    }

    @TestRails(id = "C5908983")
    @Step("Sending empty passport")
    @Test(description = "Trying to send empty passport")
    public void sendEmptyPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(""),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS);
    }

    @TestRails(id = "C5909034")
    @Step("Sending passport longer then 30 symbols")
    @Test(description = "Trying to send passport longer then 30 symbols")
    public void sendPassportWithMoreThenThirtySymbols() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "324567898765434567865432456"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS);
    }

    @TestRails(id = "C5909068")
    @Step("Sending passport with special symbols, like \"*\"")
    @Test(description = "Trying to send passport with special symbols, like \"*\"")
    public void sendPassportWithSymbols() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "*"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS);
    }

    @TestRails(id = "C5909070")
    @Step("Sending passport with only lower case letters")
    @Test(description = "Trying to send passport with only lower case letters")
    public void sendPassportWithLowerCase() {
        Response resp = new PostAdapters().post(setIDForPassRecovery("kv24535756"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS);
    }

    @TestRails(id = "C5911652")
    @Step("Sending valid SMS")
    @Test(description = "Trying to send valid SMS")
    public void sendValidPassportConfirmedWithSms() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        assertEquals(resp.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
    }

    @TestRails(id = "C5911654")
    @Step("Sending SMS shorter then 4 symbols")
    @Test(description = "Trying to send SMS shorter then 4 symbols")
    public void sendSmsWithLessThenFourDigits() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_3_SYMBOLS.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @TestRails(id = "C5911746")
    @Step("Sending SMS longer then 4 symbols")
    @Test(description = "Trying to send SMS longer then 4 symbols")
    public void sendSmsWithMoreThenFourDigits() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_5_SYMBOLS.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @TestRails(id = "C5923248")
    @Step("Sending verification SMS for another user through replacing cookie")
    @Test(description = "Trying to send SMS for another user through replacing cookie")
    public void sendSmsWithCookieReplacement() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response resp = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, replacedCookie, SC_PRECONDITION_FAILED).as(Response.class);
        assertEquals(resp.getMessage(), USER_NOT_VERIFIED);
    }

    @TestRails(id = "C5923249")
    @Step("Setting password for another user through replacing cookie")
    @Test(description = "Trying to set password for another user through replacing cookie")
    public void changingPasswordWithCookieReplacement() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, replacedCookie, SC_PRECONDITION_FAILED).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), USER_NOT_VERIFIED);
    }

    @TestRails(id = "C5924638")
    @Step("Password recovery without changing the password at first login, positive test")
    @Test(description = "Password recovery without changing the password at first login, positive test")
    public void passwordRecoveryWithoutChangingPasswordFirstLoginTest() throws SQLException {
            createUser();
            Response response = new PostAdapters().post(setPassportForRegistration("PVS153215DSV"),
                    API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_PRECONDITION_FAILED).as(Response.class);
            assertEquals(response.getMessage(), FAILED_CHANGE_PASSWORD_FROM_BANK);
            deleteUser();
    }

    @TestRails(id = "C5924835")
    @Step("Setting a valid password")
    @Test(description = "Trying to set valid password")
    public void sendValidPassword() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_OK).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), SUCCESSFUL_PASSWORD_CHANGED);
    }

    @TestRails(id = "C5924837")
    @Step("Setting a password shorter then 7 symbols")
    @Test(description = "Trying to set password shorter then 7 symbols")
    public void sendPasswordWithLessThen7() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8Rvjsi"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TestRails(id = "C5924838")
    @Step("Setting a password longer then 20 symbols")
    @Test(description = "Trying to set password longer then 20 symbols")
    public void sendPasswordWithMoreThen20() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8RvjsisKKKfhdskjf23546hkdsjf"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TestRails(id = "C5924842")
    @Step("Setting a password without numbers")
    @Test(description = "Trying to set password without numbers")
    public void sendPasswordWithOnlyLetters() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("KJGJuygggfFTYF"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TestRails(id = "C5924843")
    @Step("Setting a password with only numbers")
    @Test(description = "Trying to set password with only numbers")
    public void sendPasswordWithOnlyNumbers() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("82485694395"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TestRails(id = "C5924844")
    @Step("Setting a password with special symbols, like \"*\"")
    @Test(description = "Trying to set password with special symbols, like \"*\"")
    public void sendPasswordWithSymbols() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + "*"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TestRails(id = "C5924846")
    @Step("Setting an empty password during recovery")
    @Test(description = "Sending empty password during recovery")
    public void sendEmptyPassword() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(""),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TestRails(id = "C5924849")
    @Step("Sending passport with space")
    @Test(description = "Sending passport with space")
    public void sendPasswordWithSpace() {
        Cookie loginAsCookie = getAuthLogin(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + " "),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TestRails(id = "C5924850")
    @Step("Changing password without passport checking")
    @Test(description = "Changing password without passport checking")
    public void changePasswordWithoutPassportCheck() {
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, SC_PRECONDITION_FAILED).as(Response.class);
        assertEquals(resp.getMessage(), USER_NOT_VERIFIED);
    }

    @Story("UC-1.3 Password recovery")
    @TmsLink("5937939")
    @Test(description = "Sending password recovery code again when ban hasn't expired, negative test")
    public void sendPasswordRecoveryCodeAgainWhenBanHasNotExpired() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken(USER_0NE.getUser().getLogin(), USER_0NE.getUser().getPassword());
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword,
                SC_PERMANENT_REDIRECT);
        USER_0NE.getUser().setPassword(CHANGE_PASSWORD_FIRST_ENTRY);
        new PostAdapters().post(setNewPassword(USER_0NE.getUser().getPassword()),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_OK);
        new PostAdapters().post(setJsonObjectForRegistrationAndLogin(USER_0NE.getUser().getLogin(),
                        USER_0NE.getUser().getPassword()),
                API_HOST + API_LOGIN, SC_OK);
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_OK);
        new GetAdapters().get(API_HOST + API_LOGOUT, authTokenChangePassword, SC_OK);
        Cookie login = getAuthLogin(USER_0NE.getUser().getPassport());
        for (int i = 0; i < 3; i++) {
            new PostAdapters().post(setSmsCode(WRONG_SMS_CODE), API_HOST + CHANGE_PASSWORD + CHECK_SMS, login,
                    SC_BAD_REQUEST);
        }
        Response response = new PostAdapters().post(setSmsCode(WRONG_SMS_CODE),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_LOCKED).as(Response.class);
        assertEquals(response.getMessage(), BAN_USER);
        assertEquals(new PostAdapters().post(setFilterType(SMS_FOR_CHANGE_PASSWORD),
                        API_HOST + SMS_CODE, login, SC_LOCKED).as(Response.class).getMessage(),
                BAN_USER);
        deleteUser();
    }
}