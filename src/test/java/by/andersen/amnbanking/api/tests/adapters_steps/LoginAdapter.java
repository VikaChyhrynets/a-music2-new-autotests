package by.andersen.amnbanking.api.tests.adapters_steps;

import io.restassured.response.Response;

import static by.andersen.amnbanking.data.RequestSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;

public class LoginAdapter {
    public Response postAsClass(String url, String body) {
        return given()
                .spec(REQ_SPEC)
                .basePath(url)
                .body(body)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();
    }
}
