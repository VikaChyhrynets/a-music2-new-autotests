package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.utils.TestRails;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.AuthWithToken.authWithSessionCode;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.*;
import static org.testng.Assert.assertEquals;

public class AuthorizationAndVerificationTests {

    static String authKey;

    @BeforeTest
    void beforeTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        authKey = getAuthToken();
        System.out.println(authKey);
    }

    @Test
    @TestRails (id = "5888309")
    void sendValidSessionCode() {
        RestAssured.responseSpecification = RESP_SPEC;
        authWithSessionCode(authKey, "1234");
    }

    @Test
    @TestRails (id = "5871538")
    void sendBlankSessionCode() {
        Response resp = authWithSessionCode(authKey, "");
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test
    @TestRails (id = "???")
    void sendInvalidSessionCode() {
        Response resp = authWithSessionCode(authKey, "2311");
        assertEquals((resp.getStatusCode()), 403);
//      assertEquals(resp.getBody().);
    }

}
