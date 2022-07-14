package by.andersen.amnbanking.utils;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.http.Method.GET;
import static io.restassured.http.Method.POST;

public final class RequestUtils {

    private RequestUtils() {
    }

    public static Response getRequest(String uri, RequestSpecification requestSpecification, int statusCode) {
        return request(uri, requestSpecification, GET, statusCode);
    }

    public static Response postRequest(String uri, RequestSpecification requestSpecification, int statusCode) {
        return request(uri, requestSpecification, POST, statusCode);
    }

    private static Response request(String uri, RequestSpecification requestSpecification, Method method, int statusCode) {
        return given()
                .spec(requestSpecification)
                .when()
                .request(method, uri)
                .then()
                .statusCode(statusCode)
                .log().ifValidationFails()
                .extract()
                .response();
    }
}