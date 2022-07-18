package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import by.andersen.amnbanking.jsonBody.Response;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.SMS_CODE_INVALID;
import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.SMS_CODE;
import static by.andersen.amnbanking.data.DataUrls.WRONG_SMS_CODE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setFilterType;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.SmsVerificationData.*;
import static by.andersen.amnbanking.data.SuccessfulMessages.SESSION_CODE_CORRECT;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.testng.Assert.assertEquals;

@Story("UC 1.10 - Confirmation code")
public class ConfirmationCodeTests extends BaseAPITest {

    @Test(description = "Valid session code")
    @Step("Sending valid code")
    @TestRails(id = "C5888309")
    void sendValidSessionCode() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_VALID.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_OK).as(Response.class);
        assertEquals(resp.getMessage(), SESSION_CODE_CORRECT);
    }

    @Test(description = "Three digit session code")
    @Step("Sending three digits code")
    @TestRails(id = "С5895560")
    void sendSessionCodeWithThreeDigits() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_3_SYMBOLS.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Five digits session code")
    @Step("Sending five digits code")
    @TestRails(id = "С5895561")
    void sendSessionCodeWithFiveDigits() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_5_SYMBOLS.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Blank session code")
    @Step("Sending blank code")
    @TestRails(id = "C5895562")
    void sendBlankSessionCode() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(EMPTY_SMS.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Code with only letters")
    @Step("Sending code with only letters")
    @TestRails(id = "С5895563")
    void sendSessionCodeWithLetters() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_SMALL_LETTERS.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Code with one letter")
    @Step("Sending code with one letter")
    @TestRails(id = "С5895563")
    void sendSessionCodeWithLetter() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_1_LETTER.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Code with only symbols")
    @Step("Sending code with only special symbols")
    @TestRails(id = "С5895564")
    void sendSessionCodeWithSymbols() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_ASTERISK_PLUSES.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Code with one symbol")
    @Step("Sending code with one special symbol")
    @TestRails(id = "С5895564")
    void sendSessionCodeWithSymbol() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_AMPERSAND.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Code with one space")
    @Step("Sending code with one space")
    @TestRails(id = "С5900178")
    void sendSessionCodeWithSpace() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_SPACE_END.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Code with spaces")
    @Step("Sending code with all spaces")
    @TestRails(id = "С5900284")
    void sendSessionCodeWithSpaces() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_4_SPACES.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Valid code with space")
    @Step("Sending valid code with space")
    @TestRails(id = "С5900296")
    void sendValidSessionCodeWithSpace() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_MIDDLE_SPACE.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Valid code with space in the end")
    @Step("Sending valid code with space in the end")
    @TestRails(id = "С5900325")
    void sendValidSessionCodeEndingWithSpace() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SMS_BEGIN_SPACE.getValue()),
                API_HOST + API_SESSIONCODE, authToken, SC_BAD_REQUEST).as(Response.class);
        assertEquals(resp.getMessage(), SMS_CODE_INVALID);
    }

    @Test(description = "Sending a code again to confirm the login when the ban has not expired, negative test")
    @Step("Sending a code again to confirm the login when the ban has not expired, negative test")
    @TestRails(id = "C5937934")
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