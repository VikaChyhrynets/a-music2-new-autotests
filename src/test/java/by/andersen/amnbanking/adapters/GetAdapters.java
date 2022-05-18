package by.andersen.amnbanking.adapters;

import io.restassured.response.Response;

import static by.andersen.amnbanking.adapters.PostAdapters.authKey;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;

public class GetAdapters {
    public Response get(String url) {
        return given()
                .spec(REQ_SPEC)
                .header("Authorization", "Bearer " + authKey)
                .when()
                .get(url)
                .then()
                .log().all()
                .extract().response();
    }
}
