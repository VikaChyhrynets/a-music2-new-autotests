package by.andersen.amnbanking.data;

import io.qameta.allure.Step;
import io.restassured.http.Cookie;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.CHECK_PASSPORT;
import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQ_SPEC;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setPassportForRegistration;
import static io.restassured.RestAssured.given;

public class AuthToken {

    @Step("Authorize user and saving token in Bearer")
    public static String loginAndGetBearerToken(String login, String password) {
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

    @Step("Authorize user and saving token in Cookie")
    public static Cookie checkPassportAndGetCookie(String passport) {
        return given()
                .spec(REQ_SPEC)
                .body(setPassportForRegistration(passport))
                .post(API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT)
                .then()
                .extract()
                .detailedCookie("Login");
    }
}
