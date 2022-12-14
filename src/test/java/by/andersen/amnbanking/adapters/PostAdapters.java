package by.andersen.amnbanking.adapters;

import by.andersen.amnbanking.tests.api_tests.BaseAPITest;
import io.qameta.allure.Step;
import io.restassured.http.Cookie;
import io.restassured.response.Response;

import static by.andersen.amnbanking.data.RequestAndResponseSpec.REQ_SPEC;
import static io.restassured.RestAssured.given;

public class PostAdapters extends BaseAPITest {

    @Step("Basic POST request with changeable JSON body")
    public Response post(String body, String url, int statusCode) {

        return given()
                .spec(REQ_SPEC)
                .body(body)
                .when()
                .post(url)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract().response();
    }

    @Step("Basic POST request with changeable JSON body and authorization via Cookie")
    public Response post(String body, String url, Cookie cookie, int statusCode) {

        return given()
                .spec(REQ_SPEC)
                .cookie(cookie)
                .body(body)
                .when()
                .post(url)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract().response();
    }

    @Step("POST request with changeable JSON body and authorization via Bearer token")
    public Response post(String body, String url, String authToken, int statusCode) {

        return given()
                .spec(REQ_SPEC)
                .header("Authorization", "Bearer " + authToken)
                .body(body)
                .post(url)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract().response();
    }
}