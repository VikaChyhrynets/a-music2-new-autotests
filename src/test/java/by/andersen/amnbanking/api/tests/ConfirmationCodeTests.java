package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.data.SmsVerificationData;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import jsonBody.Response;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.testng.Assert.assertEquals;

@Story("UC 1.10 - Confirmation code")
public class ConfirmationCodeTests extends BaseAPITest {

    @Test(description = "Valid session code")
    @Step("Sending valid code")
    @TestRails(id = "C5888309")
    void sendValidSessionCode() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 200).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.SESSION_CODE_CORRECT.getValue());
    }

    @Test(description = "Three digit session code")
    @Step("Sending three digits code")
    @TestRails(id = "С5895560")
    void sendSessionCodeWithThreeDigits() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_3_SYMBOLS.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Five digits session code")
    @Step("Sending five digits code")
    @TestRails(id = "С5895561")
    void sendSessionCodeWithFiveDigits() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_5_SYMBOLS.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(),
                AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Blank session code")
    @Step("Sending blank code")
    @TestRails(id = "C5895562")
    void sendBlankSessionCode() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.EMPTY_SMS.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(), AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Code with only letters")
    @Step("Sending code with only letters")
    @TestRails(id = "С5895563")
    void sendSessionCodeWithLetters() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_SMALL_LETTERS.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(),
                AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Code with one letter")
    @Step("Sending code with one letter")
    @TestRails(id = "С5895563")
    void sendSessionCodeWithLetter() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_1_LETTER.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(),
                AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Code with only symbols")
    @Step("Sending code with only special symbols")
    @TestRails(id = "С5895564")
    void sendSessionCodeWithSymbols() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_ASTERISK_PLUSES.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(),
                AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Code with one symbol")
    @Step("Sending code with one special symbol")
    @TestRails(id = "С5895564")
    void sendSessionCodeWithSymbol() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_AMPERSAND.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(),
                AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Code with one space")
    @Step("Sending code with one space")
    @TestRails(id = "С5900178")
    void sendSessionCodeWithSpace() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_SPACE_END.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(),
                AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Code with spaces")
    @Step("Sending code with all spaces")
    @TestRails(id = "С5900284")
    void sendSessionCodeWithSpaces() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_4_SPACES.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(),
                AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Valid code with space")
    @Step("Sending valid code with space")
    @TestRails(id = "С5900296")
    void sendValidSessionCodeWithSpace() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_MIDDLE_SPACE.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(),
                AlertAPI.SMS_CODE_INVALID.getValue());
    }

    @Test(description = "Valid code with space in the end")
    @Step("Sending valid code with space in the end")
    @TestRails(id = "С5900325")
    void sendValidSessionCodeEndingWithSpace() {
        String authToken = getAuthToken(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_BEGIN_SPACE.getValue()),
                API_HOST + API_SESSIONCODE, authToken, 400).as(Response.class);
        assertEquals(resp.getMessage(),
                AlertAPI.SMS_CODE_INVALID.getValue());
    }
}
