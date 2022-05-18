package by.andersen.amnbanking.api.tests.adapters;

import io.restassured.response.Response;

import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;

public class GetAdapters {
    public Response get(String url) {
        return given()
                .spec(REQ_SPEC)
                .when()
                .get(url)
                .then()
                .extract().response();
    }
}
