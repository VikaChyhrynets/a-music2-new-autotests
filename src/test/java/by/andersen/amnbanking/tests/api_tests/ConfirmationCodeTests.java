package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.PostAdapters;

import by.andersen.amnbanking.data.SmsVerificationData;
import by.andersen.amnbanking.listener.UserDeleteListener;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import by.andersen.amnbanking.model.Response;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.SMS_CODE_INVALID;
import static by.andersen.amnbanking.data.AlertAPI.BAN_USER;
import static by.andersen.amnbanking.data.AuthToken.loginAndGetBearerToken;
import static by.andersen.amnbanking.data.DataUrls.SMS_CODE;
import static by.andersen.amnbanking.data.DataUrls.WRONG_SMS_CODE;
import static by.andersen.amnbanking.data.UsersData.USER_0NE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setFilterType;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.SuccessfulMessages.SESSION_CODE_CORRECT;
import static org.apache.hc.core5.http.HttpStatus.SC_LOCKED;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static org.testng.Assert.assertEquals;


@Listeners(UserDeleteListener.class)
@Epic("E-1. Registration and authorization")
public class ConfirmationCodeTests extends BaseAPITest {

    @Test(description = "Valid session code")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5888309")
    void sendValidSessionCode() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_VALID.getSms(), SC_OK).getMessage(),
                SESSION_CODE_CORRECT);
    }

    @Test(description = "Three digit session code")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5895560")
    void sendSessionCodeWithThreeDigits() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_3_SYMBOLS.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Five digits session code")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5895561")
    void sendSessionCodeWithFiveDigits() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_5_SYMBOLS.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Blank session code")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5895562")
    void sendBlankSessionCode() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.EMPTY_SMS.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Code with only letters")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5895563")
    void sendSessionCodeWithLetters() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_SMALL_LETTERS.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Code with one letter")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5895563")
    void sendSessionCodeWithLetter() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_1_LETTER.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Code with only symbols")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5895564")
    void sendSessionCodeWithSymbols() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_ASTERISK_PLUSES.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Code with one symbol")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5895564")
    void sendSessionCodeWithSymbol() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_AMPERSAND.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Code with one space")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5900178")
    void sendSessionCodeWithSpace() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_SPACE_END.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Code with spaces")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5900284")
    void sendSessionCodeWithSpaces() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_4_SPACES.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Valid code with space")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5900296")
    void sendValidSessionCodeWithSpace() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_MIDDLE_SPACE.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Valid code with space in the end")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5900325")
    void sendValidSessionCodeEndingWithSpace() {
        assertEquals(authentication.sendSessionCode(
                loginAndGetBearerToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                SmsVerificationData.SMS_SPACE_END.getSms(), SC_BAD_REQUEST).getMessage(),
                SMS_CODE_INVALID);
    }

    @Test(description = "Sending a code again to confirm the login when the ban has not expired, negative test")
    @Story("UC-1.10 Confirmation code")
    @TmsLink("5937934")
    void sendSMSCodeAgainWhenTheBanHasNotExpired() throws SQLException {
        createUser();
        String authToken = loginAndGetBearerToken(USER_0NE.getUser().getLogin(), USER_0NE.getUser().getPassword());
        for (int i = 0; i < 3; i++) {
            authentication.sendSessionCode(authToken, WRONG_SMS_CODE, SC_BAD_REQUEST);
        }
        assertEquals(authentication.sendSessionCode(authToken, WRONG_SMS_CODE, SC_LOCKED).getMessage(), BAN_USER);
        assertEquals(new PostAdapters().post(setFilterType("SMS_FOR_SESSION"),
                API_HOST + SMS_CODE, authToken, SC_LOCKED).as(Response.class).getMessage(), BAN_USER);
        deleteUser();
    }
}