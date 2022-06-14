package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Story;
import jsonBody.Response;
import org.testng.annotations.Test;
import static by.andersen.amnbanking.data.DataUrls.USER_PASS;
import static by.andersen.amnbanking.data.DataUrls.USER_SESSION_CODE_LOGIN;
import static org.testng.Assert.assertEquals;

@Story("UC 1.10 - Confirmation code")
public class AuthorizationAndVerificationTests extends BaseTest {

    @Test(description = "Valid session code")
    @TestRails (id = "C5888309")
    void sendValidSessionCode() {
       Response resp = new PostAdapters().authWithSessionCode("1234", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
       assertEquals(resp.getMessage(), "Session code is correct");
    }

    @Test(description = "Three digit session code")
    @TestRails (id = "С5895560")
    void sendSessionCodeWithThreeDigits() {
        Response resp = new PostAdapters().authWithSessionCode("231", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),"Sms code contains invalid characters");
    }

    @Test(description = "Five digits session code")
    @TestRails (id = "С5895561")
    void sendSessionCodeWithFiveDigits() {
        Response resp = new PostAdapters().authWithSessionCode("23231", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Blank session code")
    @TestRails (id = "C5895562")
    void sendBlankSessionCode() {
        Response resp = new PostAdapters().authWithSessionCode("", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with only letters")
    @TestRails (id = "С5895563")
    void sendSessionCodeWithLetters() {
       Response resp = new PostAdapters().authWithSessionCode("brab", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with one letter")
    @TestRails (id = "С5895563")
    void sendSessionCodeWithLetter() {
        Response resp = new PostAdapters().authWithSessionCode("123a", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with only symbols")
    @TestRails (id = "С5895564")
    void sendSessionCodeWithSymbols() {
        Response resp = new PostAdapters().authWithSessionCode("+++*", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with one symbol")
    @TestRails (id = "С5895564")
    void sendSessionCodeWithSymbol() {
        Response resp = new PostAdapters().authWithSessionCode("123*", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with one space")
    @TestRails (id = "С5900178")
    void sendSessionCodeWithSpace() {
        Response resp = new PostAdapters().authWithSessionCode("123 ", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Code with spaces")
    @TestRails (id = "С5900284")
    void sendSessionCodeWithSpaces() {
        Response resp = new PostAdapters().authWithSessionCode("    ", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Valid code with space")
    @TestRails (id = "С5900296")
    void sendValidSessionCodeWithSpace() {
        Response resp = new PostAdapters().authWithSessionCode("12 34", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test(description = "Valid code with space in the end")
    @TestRails (id = "С5900325")
    void sendValidSessionCodeEndingWithSpace() {
        Response resp = new PostAdapters().authWithSessionCode("1234 ", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }
}
