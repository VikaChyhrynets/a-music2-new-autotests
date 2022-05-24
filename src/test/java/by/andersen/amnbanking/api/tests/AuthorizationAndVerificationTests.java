package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.TestRails;
import jsonBody.Response;
import org.testng.annotations.Test;
import static by.andersen.amnbanking.data.DataUrls.USER_PASS;
import static by.andersen.amnbanking.data.DataUrls.USER_SESSION_CODE_LOGIN;
import static org.testng.Assert.assertEquals;

public class AuthorizationAndVerificationTests extends BaseTest {

    @Test()
    @TestRails (id = "C5888309")
    void sendValidSessionCode() {
       Response resp = new PostAdapters().authWithSessionCode("1234", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
       assertEquals(resp.getMessage(), "Session code is correct");
    }

    @Test(priority = 1)
    @TestRails (id = "С5895560")
    void sendSessionCodeWithThreeDigits() {
        Response resp = new PostAdapters().authWithSessionCode("231", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),"Sms code contains invalid characters");
    }

    @Test()
    @TestRails (id = "С5895561")
    void sendSessionCodeWithFiveDigits() {
        Response resp = new PostAdapters().authWithSessionCode("23231", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test()
    @TestRails (id = "C5895562")
    void sendBlankSessionCode() {
        Response resp = new PostAdapters().authWithSessionCode("", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test()
    @TestRails (id = "С5895563")
    void sendSessionCodeWithLetters() {
       Response resp = new PostAdapters().authWithSessionCode("brab", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test()
    @TestRails (id = "С5895563")
    void sendSessionCodeWithLetter() {
        Response resp = new PostAdapters().authWithSessionCode("123a", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test()
    @TestRails (id = "С5895564")
    void sendSessionCodeWithSymbols() {
        Response resp = new PostAdapters().authWithSessionCode("+++*", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }

    @Test()
    @TestRails (id = "С5895564")
    void sendSessionCodeWithSymbol() {
        Response resp = new PostAdapters().authWithSessionCode("123*", USER_SESSION_CODE_LOGIN, USER_PASS).as(Response.class);
        assertEquals(resp.getMessage(),
                "Sms code contains invalid characters");
    }
}
