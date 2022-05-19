package by.andersen.amnbanking.data;

import io.restassured.response.Response;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.RequestSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;

public class AuthWithToken {

    public static Response authWithSessionCode(String smsCode) {
        String authKey = getAuthToken();

        return given()
                .spec(REQ_SPEC)
                .header("Authorization", "Bearer " + authKey)
                .body("{\"smsCode\": \"" + smsCode + "\"\n}")
                .post(API_HOST + API_SESSIONCODE)
                .then()
                .extract().response();
    }
}

