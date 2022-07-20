package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import by.andersen.amnbanking.model.Response;
import io.qameta.allure.TmsLink;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.SMS_CODE;
import static by.andersen.amnbanking.data.DataUrls.WRONG_SMS_CODE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setFilterType;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.testng.Assert.assertEquals;

@Epic("E-1. Registration and authorization")
public class ConfirmationCodeTests extends BaseAPITest {

    @Test(description = "Valid session code")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5888309")
    void sendValidSessionCode() {
        assertEquals(authentication.sendSessionCode(
                getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                "1234", HttpStatus.SC_OK).getMessage(),
                AlertAPI.SESSION_CODE_CORRECT);
    }

    @Test(description = "Three digit session code")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5895560")
    void sendSessionCodeWithThreeDigits() {
        assertEquals(authentication.sendSessionCode(
                getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                "231", HttpStatus.SC_BAD_REQUEST).getMessage(), AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Five digits session code")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5895561")
    void sendSessionCodeWithFiveDigits() {
        assertEquals(authentication.sendSessionCode(
                getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                "12345", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Blank session code")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5895562")
    void sendBlankSessionCode() {
        assertEquals(authentication.sendSessionCode(
                getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                "", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Code with only letters")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5895563")
    void sendSessionCodeWithLetters() {
        assertEquals(authentication.sendSessionCode(
                        getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                        "brab", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Code with one letter")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5895563")
    void sendSessionCodeWithLetter() {
        assertEquals(authentication.sendSessionCode(
                        getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                        "123a", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Code with only symbols")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5895564")
    void sendSessionCodeWithSymbols() {
        assertEquals(authentication.sendSessionCode(
                        getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                        "+++*", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Code with one symbol")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5895564")
    void sendSessionCodeWithSymbol() {
        assertEquals(authentication.sendSessionCode(
                        getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                        "123&", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Code with one space")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5900178")
    void sendSessionCodeWithSpace() {
        assertEquals(authentication.sendSessionCode(
                        getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                        "123 ", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Code with spaces")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5900284")
    void sendSessionCodeWithSpaces() {
        assertEquals(authentication.sendSessionCode(
                        getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                        "    ", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Valid code with space")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5900296")
    void sendValidSessionCodeWithSpace() {
        assertEquals(authentication.sendSessionCode(
                        getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                        "12 34", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Valid code with space in the end")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5900325")
    void sendValidSessionCodeEndingWithSpace() {
        assertEquals(authentication.sendSessionCode(
                        getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                        "1234 ", HttpStatus.SC_BAD_REQUEST).getMessage(),
                AlertAPI.SMS_CODE_INVALID);
    }

    @Test(description = "Sending a code again to confirm the login when the ban has not expired, negative test")
    @Story("UC 1.10 - Confirmation code")
    @TmsLink("5937934")
    void sendSMSCodeAgainWhenTheBanHasNotExpired() throws SQLException {
        createUser();
        String authToken = getAuthToken("Eminem79", "111Gv5dvvf511");
        for (int i = 0; i < 3; i++) {
            new PostAdapters().post(setSmsCode(WRONG_SMS_CODE), API_HOST + API_SESSIONCODE, authToken, 400);
        }
        assertEquals(new PostAdapters().post(setSmsCode(WRONG_SMS_CODE), API_HOST + API_SESSIONCODE, authToken, 423).as(Response.class).getMessage(),
                "Ban time is not over yet...");
        assertEquals(new PostAdapters().post(setFilterType("SMS_FOR_SESSION"),
                API_HOST + SMS_CODE, authToken, 423).as(Response.class).getMessage(),
                "Ban time is not over yet...");
        deleteUser();
    }
}