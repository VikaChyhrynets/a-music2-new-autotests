package by.andersen.amnbanking.api.tests.adapters;

import io.restassured.response.ResponseBody;

import static by.andersen.amnbanking.api.tests.adapters.BaseAdapter.REQ_SPEC;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationAdapter {

        public ResponseBody post(String body, String url, String response) {
        return given()
                .spec(REQ_SPEC)
                .body(body)
                .when()
                .post(url)
                .then()
                .assertThat()
                .body(equalTo(response))
                .log().all()
                .extract().response().body();
    }
}