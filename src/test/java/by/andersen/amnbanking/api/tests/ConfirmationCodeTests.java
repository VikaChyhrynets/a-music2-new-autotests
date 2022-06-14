package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Story;
import jsonBody.Response;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.testng.Assert.assertEquals;

@Story("UC 1.10 - Confirmation code")
public class ConfirmationCodeTests extends BaseTest {

    @Test(description = "Valid session code")
    @TestRails (id = "C5888309")
    void sendValidSessionCode() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
       Response resp = new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
       assertEquals(resp.getMessage(), "Session code is correct");
    }

    @Test(description = "Three digit session code")
    @TestRails (id = "С5895560")
    void sendSessionCodeWithThreeDigits() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("231"), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),"Sms code contains invalid characters");
    }

    @Test(description = "Five digits session code")
    @TestRails (id = "С5895561")
    void sendSessionCodeWithFiveDigits() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("12345"), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Blank session code")
    @TestRails (id = "C5895562")
    void sendBlankSessionCode() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode(""), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with only letters")
    @TestRails (id = "С5895563")
    void sendSessionCodeWithLetters() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("brab"), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with one letter")
    @TestRails (id = "С5895563")
    void sendSessionCodeWithLetter() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("123a"), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with only symbols")
    @TestRails (id = "С5895564")
    void sendSessionCodeWithSymbols() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("+++*"), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with one symbol")
    @TestRails (id = "С5895564")
    void sendSessionCodeWithSymbol() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("123&"), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with one space")
    @TestRails (id = "С5900178")
    void sendSessionCodeWithSpace() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("123 "), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with spaces")
    @TestRails (id = "С5900284")
    void sendSessionCodeWithSpaces() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("    "), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Valid code with space")
    @TestRails (id = "С5900296")
    void sendValidSessionCodeWithSpace() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("12 34"), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Valid code with space in the end")
    @TestRails (id = "С5900325")
    void sendValidSessionCodeEndingWithSpace() {
        String authToken = getAuthToken(USER_SESSION_CODE_LOGIN, USER_PASS);
        Response resp = new PostAdapters().post(setSmsCode("1234 "), API_HOST + API_SESSIONCODE, authToken).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }
}
