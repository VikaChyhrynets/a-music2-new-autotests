package by.andersen.amnbanking.utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static by.andersen.amnbanking.data.DataUrls.API_URL;
import static io.restassured.http.ContentType.JSON;

public final class RequestSpecificationUtils {

    private RequestSpecificationUtils() {
    }

    public static RequestSpecification requestSpecification = RestAssured.given().baseUri(API_URL).contentType(JSON);
}