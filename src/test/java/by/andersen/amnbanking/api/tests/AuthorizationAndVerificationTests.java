package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.utils.TestRails;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AuthWithToken.authWithSessionCode;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.RESP_SPEC;
import static org.testng.Assert.assertEquals;

public class AuthorizationAndVerificationTests extends BaseTest {

    @Test
    @TestRails (id = "C5888309")
    void sendValidSessionCode() {
        RestAssured.responseSpecification = RESP_SPEC;
        authWithSessionCode("1234");
    }

    @Test
    @TestRails (id = "С5895560")
    void sendSessionCodeWithThreeDigits() {
        Response resp = authWithSessionCode("231");
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test
    @TestRails (id = "С5895561")
    void sendSessionCodeWithFiveDigits() {
        Response resp = authWithSessionCode("23231");
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test
    @TestRails (id = "C5895562")
    void sendBlankSessionCode() {
        Response resp = authWithSessionCode("");
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test
    @TestRails (id = "С5895563")
    void sendSessionCodeWithLetters() {
        Response resp = authWithSessionCode("brab");
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test
    @TestRails (id = "С5895564")
    void sendSessionCodeWithSymbols() {
        Response resp = authWithSessionCode("+++*");
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

}
