package by.andersen.amnbanking.data;

import static by.andersen.amnbanking.data.RequestSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;

public class AuthToken {

    public static String getAuthToken(String login, String password) {
        return given()
                .spec(REQ_SPEC)
                .body("{\n" +
                        "\"login\": \"" + login + "\", \n" +
                        "\"password\":  \"" + password + "\"\n" +
                        "}")
                .post(DataUrls.API_HOST + DataUrls.API_LOGIN)
                .then()
                .extract()
                .header("Authorization");
    }

    public static String getAuthToken() {
        return given()
                .spec(REQ_SPEC)
                .body("{\n" +
                        "\"login\": \"" + DataUrls.USER_LOGIN + "\", \n" +
                        "\"password\":  \"" + DataUrls.USER_PASS + "\"\n" +
                        "}")
                .post(DataUrls.API_HOST + DataUrls.API_LOGIN)
                .then()
                .extract()
                .header("Authorization");
    }
}

