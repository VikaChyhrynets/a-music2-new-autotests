package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.utils.TestRails;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ResponseBody;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.BaseRequest.authorizeWithSessionCode;
import static by.andersen.amnbanking.data.BaseRequest.loginAndGetAuthKey;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.RESPONSE_SPECIFICATION;
import static io.restassured.RestAssured.given;

public class AuthorizationAndVerificationTests {

    static Header authKey;

    @BeforeTest
    void beforeTest() {
        RestAssured.responseSpecification = RESPONSE_SPECIFICATION;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        authKey = loginAndGetAuthKey(USER_LOGIN, USER_PASS);

    }

    @Test
    @TestRails (id = "5888309")
    public void sendValidSessionCode() {
        System.out.println(authKey);
//        authorizeWithSessionCode(authKey);



    }

}
