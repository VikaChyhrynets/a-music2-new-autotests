package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.utils.TestRails;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static by.andersen.amnbanking.adapters.PostAdapters.authWithSessionCode;
import static by.andersen.amnbanking.data.DataUrls.USER_PASS;
import static by.andersen.amnbanking.data.DataUrls.USER_SESSION_CODE_LOGIN;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.RESP_SPEC;
import static org.testng.Assert.assertEquals;

public class AuthorizationAndVerificationTests extends BaseTest {

    @Test()
    @TestRails (id = "C5888309")
    void sendValidSessionCode() {
        RestAssured.responseSpecification = RESP_SPEC;
        authWithSessionCode("1234", USER_SESSION_CODE_LOGIN, USER_PASS);
    }

    @Test(priority = 1)
    @TestRails (id = "С5895560")
    void sendSessionCodeWithThreeDigits() {
        Response resp = authWithSessionCode("231", USER_SESSION_CODE_LOGIN, USER_PASS);
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test()
    @TestRails (id = "С5895561")
    void sendSessionCodeWithFiveDigits() {
        Response resp = authWithSessionCode("23231", USER_SESSION_CODE_LOGIN, USER_PASS);
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test()
    @TestRails (id = "C5895562")
    void sendBlankSessionCode() {
        Response resp = authWithSessionCode("", USER_SESSION_CODE_LOGIN, USER_PASS);
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test()
    @TestRails (id = "С5895563")
    void sendSessionCodeWithLetters() {
        Response resp = authWithSessionCode("brab", USER_SESSION_CODE_LOGIN, USER_PASS);
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test()
    @TestRails (id = "С5895564")
    void sendSessionCodeWithSymbols() {
        Response resp = authWithSessionCode("+++*", USER_SESSION_CODE_LOGIN, USER_PASS);
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

}
