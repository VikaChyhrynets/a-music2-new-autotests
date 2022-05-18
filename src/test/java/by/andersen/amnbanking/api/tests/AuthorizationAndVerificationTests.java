package by.andersen.amnbanking.api.tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;

public class AuthorizationAndVerificationTests {

    static String authKey;

    @BeforeTest
    void beforeTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        authKey = getAuthToken();
        System.out.println(authKey);
    }
}
