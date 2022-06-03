package by.andersen.amnbanking.adapters;

import by.andersen.amnbanking.api.tests.BaseTest;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.CHECK_PASSPORT;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;

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

    public Response authWithSessionCode(String smsCode, String login, String password) {
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

    public static Cookie checkPassportAndGetCookie(String passport) {

        return given()
                .spec(REQ_SPEC)
                .body("{\"passport\": \"" + passport + "\"\n}")
                .post(API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT)
                .then()
                .log()
                .all()
                .extract().detailedCookie("Login");
    }
}