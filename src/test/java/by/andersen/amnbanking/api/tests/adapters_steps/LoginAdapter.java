package by.andersen.amnbanking.api.tests.adapters_steps;

import io.restassured.response.Response;

import static by.andersen.amnbanking.data.RequestSpec.REQUEST_SPECIFICATION;
import static io.restassured.RestAssured.given;

public class LoginAdapter {
    public Response postAsClass(String url, String body) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .basePath(url)
                .body(body)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();
    }
}
