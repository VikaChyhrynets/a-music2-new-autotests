package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.utils.TestRails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.BaseRequest.authorizeWithSessionCode;
import static by.andersen.amnbanking.data.BaseRequest.loginAndGetAuthKey;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.DataUrls.USER_PASS;
import static by.andersen.amnbanking.data.RequestSpec.REQUEST_SPECIFICATION;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertEquals;

public class AuthorizationAndVerificationTests {





    @Test
    public ResponseBody sendValidSessionCode() {
        String authKey = loginAndGetAuthKey();
        RequestSpecification reqSpec = new RequestSpecBuilder()
                .addRequestSpecification(REQUEST_SPECIFICATION)
                .setBasePath(API_HOST + API_SESSIONCODE)
                .addHeader("Authorization", authKey)
                .setBody("{\"smsCode\": \"1234\"}")
                .build();
        return given()
                .spec(reqSpec)
                .post()
                .then()
                .log().all()
                .extract().response();


    }

}
