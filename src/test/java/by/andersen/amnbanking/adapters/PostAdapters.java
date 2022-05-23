package by.andersen.amnbanking.adapters;

import by.andersen.amnbanking.api.tests.BaseTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class PostAdapters extends BaseTest {

    static  String authKey = getAuthToken();

        public ResponseBody post(String body, String url) {
        return given()
                .spec(REQ_SPEC)
                .body(body)
                .when()
                .post(url)
                .then()
                .log().all()
                .extract().response().body();
    }


    public static Response postAuthWithSessionCode(String smsCode) {

        return given()
                .spec(REQ_SPEC)
                .header("Authorization", "Bearer " + authKey)
                .body("{\"smsCode\": \"" + smsCode + "\"\n}")
                .post(API_HOST + API_SESSIONCODE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response authWithSessionCode(String smsCode, String login, String password) {
        String authKey = getAuthToken(login, password);

        return given()
                .spec(REQ_SPEC)
                .log().all()
                .header("Authorization", "Bearer " + authKey)
                .body("{\"smsCode\": \"" + smsCode + "\"\n}")
                .log().all()
                .post(API_HOST + API_SESSIONCODE)
                .then()
                .log().all()
                .extract().response();
    }

}