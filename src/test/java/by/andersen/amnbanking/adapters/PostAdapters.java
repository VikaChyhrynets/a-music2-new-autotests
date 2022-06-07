package by.andersen.amnbanking.adapters;

import by.andersen.amnbanking.api.tests.BaseTest;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static by.andersen.amnbanking.data.AuthToken.getAuthLogin;
import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;

public class PostAdapters extends BaseTest {

    static Cookie authStaticLogin = getAuthLogin(PASSPORT_REG);

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

    public ResponseBody post(String body, String url, Cookie cookie) {
        return given()
                .spec(REQ_SPEC)
                .cookie(cookie)
                .body(body)
                .when()
                .post(url)
                .then()
                .log().all()
                .extract().response().body();
    }

    public Response postAuthWithSessionCode(String authToken,String body, String url) {

        return given()
                .spec(REQ_SPEC)
                .header("Authorization", "Bearer " + authToken)
                .body(body)
                .post(url)
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

    public Response postWithCookieLogin(String passport, String body, String url) {
        Cookie authLogin = getAuthLogin(passport);

        return given()
                .spec(REQ_SPEC)
                .cookie(authLogin)
                .body(body)
                .post(url)
                .then()
                .log().all()
                .extract().response();
    }

    public  Response postWithStaticCookieLogin(String body, String url) {

        return given()
                .spec(REQ_SPEC)
                .cookie(authStaticLogin)
                .body(body)
                .post(url)
                .then()
                .log().all()
                .extract().response();
    }
}