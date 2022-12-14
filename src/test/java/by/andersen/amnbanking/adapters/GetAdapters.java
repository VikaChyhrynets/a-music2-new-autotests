package by.andersen.amnbanking.adapters;

import io.restassured.response.Response;

import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;

public class GetAdapters {
    public Response get(String url, String authKey, int statusCode) {
        return given()
                .spec(REQ_SPEC)
                .header("Authorization", "Bearer " + authKey)
                .when()
                .get(url)
                .then()
                .statusCode(statusCode)
                .log().all()
                .extract().response();
    }

    public Response get(String url, int statusCode) {
        return given()
                .spec(REQ_SPEC)
                .when()
                .get(url)
                .then()
                .statusCode(statusCode)
                .log().all()
                .extract().response();
    }
}