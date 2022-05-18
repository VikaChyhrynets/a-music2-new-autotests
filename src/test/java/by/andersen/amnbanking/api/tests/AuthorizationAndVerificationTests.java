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

public class AuthorizationAndVerificationTests extends BaseTest {

    @Test
    @TestRails (id = "C5888309")
    void sendValidSessionCode() {
        RestAssured.responseSpecification = RESP_SPEC;
        authWithSessionCode("1234");
    }

    @Test
    @TestRails (id = "C5871538")
    void sendBlankSessionCode() {
        Response resp = authWithSessionCode("");
        assertEquals((resp.getStatusCode()), 400);
        assertEquals(resp.body().asString(),
                "{\"message\":\"Sms code contains invalid characters\"}");
    }

    @Test
    @TestRails (id = "???")
    void sendInvalidSessionCode() {
        Response resp = authWithSessionCode("2311");
        assertEquals((resp.getStatusCode()), 403);
//      assertEquals(resp.getBody().);
    }

}
