package by.andersen.amnbanking.api.tests.adapters;

import io.restassured.response.Response;

import static by.andersen.amnbanking.api.tests.adapters.BaseSpec.REQ_SPEC_WITH_TOKEN;
import static io.restassured.RestAssured.given;

public class GetAdapters {
    public Response get(String url) {
        return given()
                .spec(REQ_SPEC_WITH_TOKEN)
                .when()
                .get(url)
                .then()
                .extract().response();
    }
}
