package by.andersen.amnbanking.data;

import io.restassured.http.Cookie;

import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQ_SPEC;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setPassportForRegistration;
import static io.restassured.RestAssured.given;

public class AuthToken {

    public static String getAuthToken(String login, String password) {
        return given()
                .spec(REQ_SPEC)
                .body("{\n" +
                        "\"login\": \"" + login + "\", \n" +
                        "\"password\":  \"" + password + "\"\n" +
                        "}")
                .post(API_HOST + API_LOGIN)
                .then()
                .extract()
                .header("Authorization");
    }

    public static String getAuthToken() {
        return given()
                .spec(REQ_SPEC)
                .body("{\n" +
                        "\"login\": \"" + USER_LOGIN + "\", \n" +
                        "\"password\":  \"" + USER_PASS + "\"\n" +
                        "}")
                .post(API_HOST + API_LOGIN)
                .then()
                .extract()
                .header("Authorization");
    }

    public static Cookie getAuthLogin(String passport) {
        return given()
                .spec(REQ_SPEC)
                .body(setPassportForRegistration(passport))
                .post(API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT)
                .then()
                .extract()
                .detailedCookie("Login");
    }
}
