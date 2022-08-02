package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.data.UsersData;
import by.andersen.amnbanking.model.Response;
import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.http.Cookie;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.BAN_USER;
import static by.andersen.amnbanking.data.AlertAPI.FAILED_CHANGE_PASSWORD_FROM_BANK;
import static by.andersen.amnbanking.data.AlertAPI.INVALID_SMS;
import static by.andersen.amnbanking.data.AlertAPI.REGISTRATION_FAILED_USER_PASSWORD;
import static by.andersen.amnbanking.data.AlertAPI.SMS_CODE_INVALID;
import static by.andersen.amnbanking.data.AlertAPI.USER_NOT_VERIFIED;
import static by.andersen.amnbanking.data.AuthToken.checkPassportAndGetCookie;
import static by.andersen.amnbanking.data.AuthToken.loginAndGetBearerToken;
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
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_LOCKED;
import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PERMANENT_REDIRECT;
import static org.apache.hc.core5.http.HttpStatus.SC_PRECONDITION_FAILED;
import static org.testng.Assert.assertEquals;


@Listeners(UserDeleteListener.class)
@Epic("E-1. Registration and authorization")
public class PasswordRecoveryTests extends BaseAPITest {

    @TmsLink("5911963")
    @Story("UC-1.3 Password recovery")
    @Test(description = "negative test, only letters in code confirmation")

    public void enteringLettersInCodeConfirmationTest() {
        Cookie login = checkPassportAndGetCookie(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(SMS_SMALL_LETTERS.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), SMS_CODE_INVALID);
    }

    @TmsLink("5911967")
    @Story("UC-1.3 Password recovery")
    @Test(description = "negative test, special character /")
    public void enteringSpecialCharacterInCodeConfirmationTest() {
        Cookie login = checkPassportAndGetCookie(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(SMS_SLASH_MIDDLE.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), SMS_CODE_INVALID);
    }

    @TmsLink("5911881")
    @Story("UC-1.3 Password recovery")
    @Test(description = "negative test, empty field for code confirmation")
    public void enteringWithEmptyFieldInCodeConfirmationTest() {
        Cookie login = checkPassportAndGetCookie(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(""),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), SMS_CODE_INVALID);
    }

    @TmsLink("5911791")
    @Story("UC-1.3 Password recovery")
    @Test(description = "negative test, wrong code confirmation")
    public void enteringWrongCodeInCodeConfirmation1TimeTest() {
        Cookie login = checkPassportAndGetCookie(PASSPORT_REG);
        Response response = new PostAdapters().post(setSmsCode(SMS_INVALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), INVALID_SMS);
        Assert.assertEquals(response.getFailsCount(), 1);
        new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_OK).as(Response.class);
    }

    @TmsLink("5911796")
    @Story("UC-1.3 Password recovery")
    @Test(description = "negative test,send wrong code confirmation by passport 2 times")
    public void enteringWrongCodeInCodeConfirmation2TimesTest() {
        Cookie login = checkPassportAndGetCookie(PASSPORT_REG);
        new PostAdapters().post(setSmsCode(SMS_INVALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Response response = new PostAdapters().post(setSmsCode(SMS_INVALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), INVALID_SMS);
        Assert.assertEquals(response.getFailsCount(), 2);
        new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_OK).as(Response.class);
    }

    @TmsLink("5924835")
    @Story("UC-1.3 Password recovery")
    @Test(description = "positive test, change password by passport with valid data")
    public void changePasswordValidDateTest() {
        Cookie login = checkPassportAndGetCookie(PASSPORT_REG);
        new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, login, SC_OK);
        Response response = new PostAdapters().post(setNewPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, login, SC_OK).as(Response.class);
        Response response1 = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        (LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                API_HOST + API_LOGIN, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), SUCCESSFUL_PASSWORD_CHANGED);
        Assert.assertEquals(response1.getMessage(), LOGIN_SUCCESS);
    }

    @TmsLink("5911960")
    @Story("UC-1.3 Password recovery")
    @Test(description = "negative test, wrong filter type")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPassportTest() {
        String authToken = loginAndGetBearerToken(UsersData.USER_MALEFICENT.getUser().getLogin(),
                UsersData.USER_MALEFICENT.getUser().getPassword());
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSPORT"),
                API_HOST + SMS_CODE, authToken, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.INVALID_SMS_FILTER);
    }

    @TmsLink("5911959")
    @Story("UC-1.3 Password recovery")
    @Test(description = "positive test, send correct filter type for change password by passport")
    public void sendConfirmationCodeAgainWithInvalidDataFilterPasswordTest() {
        Cookie login = checkPassportAndGetCookie(PASSPORT_REG);
        Response response = new PostAdapters().post(setFilterType("SMS_FOR_CHANGE_PASSWORD"),
                API_HOST + SMS_CODE, login, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), SMS_SENT_SUCCESSFULLY);
    }

    @TmsLink("5908792")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send valid passport")
    public void sendValidPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_OK).as(Response.class);
        assertEquals(resp.getMessage(), PASSPORT_IS_VALID);
    }

    @TmsLink("5909076")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send valid, but unregistered passport")
    public void sendUnregisteredPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "1111"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_NOT_FOUND).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.NOT_REGISTER_ID);
    }

    @TmsLink("5908983")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send empty passport")
    public void sendEmptyPassport() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(""),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS);
    }

    @TmsLink("5909034")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send passport longer then 30 symbols")
    public void sendPassportWithMoreThenThirtySymbols() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "324567898765434567865432456"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS);
    }

    @TmsLink("5909068")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send passport with special symbols, like \"*\"")
    public void sendPassportWithSymbols() {
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "*"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS);
    }

    @TmsLink("5909070")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send passport with only lower case letters")
    public void sendPassportWithLowerCase() {
        Response resp = new PostAdapters().post(setIDForPassRecovery("kv24535756"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.INVALID_CHARACTERS);
    }

    @TmsLink("5911652")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send valid SMS")
    public void sendValidPassportConfirmedWithSms() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        assertEquals(resp.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
    }

    @TmsLink("5911654")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send SMS shorter then 4 symbols")
    public void sendSmsWithLessThenFourDigits() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_3_SYMBOLS.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @TmsLink("5911746")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send SMS longer then 4 symbols")
    public void sendSmsWithMoreThenFourDigits() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_5_SYMBOLS.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @TmsLink("5923248")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send SMS for another user through replacing cookie")
    public void sendSmsWithCookieReplacement() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response resp = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, replacedCookie, SC_PRECONDITION_FAILED).as(Response.class);
        assertEquals(resp.getMessage(), USER_NOT_VERIFIED);
    }

    @TmsLink("5923249")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to set password for another user through replacing cookie")
    public void changingPasswordWithCookieReplacement() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, replacedCookie, SC_PRECONDITION_FAILED).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), USER_NOT_VERIFIED);
    }

    @TmsLink("5924638")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Password recovery without changing the password at first login, positive test")
    public void passwordRecoveryWithoutChangingPasswordFirstLoginTest() throws SQLException {
            createUser();
            Response response = new PostAdapters().post(setPassportForRegistration("ASD153215ASD"),
                    API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT, SC_PRECONDITION_FAILED).as(Response.class);
            assertEquals(response.getMessage(), FAILED_CHANGE_PASSWORD_FROM_BANK);
            deleteUser();
    }

    @TmsLink("5924835")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to set valid password")
    public void sendValidPassword() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_OK).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), SUCCESSFUL_PASSWORD_CHANGED);
    }

    @TmsLink("5924837")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to set password shorter then 7 symbols")
    public void sendPasswordWithLessThen7() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8Rvjsi"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TmsLink("5924838")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to set password longer then 20 symbols")
    public void sendPasswordWithMoreThen20() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("8RvjsisKKKfhdskjf23546hkdsjf"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TmsLink("5924842")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to set password without numbers")
    public void sendPasswordWithOnlyLetters() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("KJGJuygggfFTYF"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TmsLink("5924843")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to set password with only numbers")
    public void sendPasswordWithOnlyNumbers() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword("82485694395"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TmsLink("5924844")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to set password with special symbols, like \"*\"")
    public void sendPasswordWithSymbols() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + "*"),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TmsLink("5924846")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Sending empty password during recovery")
    public void sendEmptyPassword() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(""),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TmsLink("5924849")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Sending passport with space")
    public void sendPasswordWithSpace() {
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response checkSms = new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + CHANGE_PASSWORD + CHECK_SMS, loginAsCookie, SC_OK).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG + " "),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, loginAsCookie, SC_BAD_REQUEST).as(Response.class);
        assertEquals(checkSms.getMessage(), CHANGE_PASSWORD_CODE_CORRECT);
        assertEquals(resp.getMessage(), REGISTRATION_FAILED_USER_PASSWORD);
    }

    @TmsLink("5924850")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Changing password without passport checking")
    public void changePasswordWithoutPassportCheck() {
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG),
                API_HOST + CHANGE_PASSWORD + NEW_PASSWORD, SC_PRECONDITION_FAILED).as(Response.class);
        assertEquals(resp.getMessage(), USER_NOT_VERIFIED);
    }

    @TmsLink("5937939")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Sending password recovery code again when ban hasn't expired, negative test")
    public void sendPasswordRecoveryCodeAgainWhenBanHasNotExpired() throws SQLException {
        createUser();
        String authTokenChangePassword = loginAndGetBearerToken(USER_0NE.getUser().getLogin(), USER_0NE.getUser().getPassword());
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
        Cookie login = checkPassportAndGetCookie(USER_0NE.getUser().getPassport());
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