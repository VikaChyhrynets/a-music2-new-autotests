package by.andersen.amnbanking.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

import static by.andersen.amnbanking.data.BaseRequest.reqSpec;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGIN;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQUEST_SPECIFICATION;
import static io.restassured.RestAssured.given;

public class AuthToken {
    private static final RequestSpecification reqSpec = new RequestSpecBuilder()
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
}
