package by.andersen.amnbanking.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.RequestSpec.REQUEST_SPECIFICATION;
import static io.restassured.RestAssured.given;

public class BaseRequest {

    public static String loginAndGetAuthKey() {
        RequestSpecification reqSpec = new RequestSpecBuilder()
                .addRequestSpecification(REQUEST_SPECIFICATION)
                .setBasePath(API_HOST + API_LOGIN).build();
        return given()
                .spec(reqSpec)
                .body("{\"login\": \"" + USER_LOGIN + "\", \n" +
                        "\"password\":  \"" + USER_PASS + "\"\n}")
                .post()
                .then()
                //.log()
                //.body()
                .statusCode(200)
                .extract().header("Authorization");
    }

    public static String authorizeWithSessionCode(String authKey) {
        RequestSpecification reqSpec = new RequestSpecBuilder()
                .addRequestSpecification(REQUEST_SPECIFICATION)
                .setBasePath(API_HOST + API_SESSIONCODE).build();
        return given()
                .spec(reqSpec)
                .header("Authorization", authKey)
                .body("{\"smsCode\": \"1234\"\n}")
                .post()
                .then()
                .extract().response().body().asString();
    }
}
