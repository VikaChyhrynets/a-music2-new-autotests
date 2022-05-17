package by.andersen.amnbanking.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQUEST_SPECIFICATION;
import static io.restassured.RestAssured.given;

public class BaseRequest {

    static RequestSpecification reqSpec = new RequestSpecBuilder()
            .addRequestSpecification(REQUEST_SPECIFICATION).build();


    public static Header getAuthToken(String login, String password) {
        return given()
                .spec(reqSpec)
                .body("{\n" +
                        "\"login\": \"" + login + "\", \n" +
                        "\"password\":  \"" + password + "\"\n" +
                        "}")
                .post(API_HOST + API_LOGIN)
                .then()
                .extract()
                .headers().get("Authorization");
    }

    public static Response authorizeWithSessionCode(Header authKey) {
        return given()
                .spec(reqSpec)
                .header(authKey)
                .body("{\"smsCode\": \"1234\"\n}")
                .post(API_HOST + API_SESSIONCODE)
                .then()
                .extract().response();
    }
}
