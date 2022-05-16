package by.andersen.amnbanking.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static by.andersen.amnbanking.data.DataUrls.API_URL;

public class RequestSpec {
    public static final RequestSpecification REQUEST_SPECIFICATION = new RequestSpecBuilder()
            .setBaseUri(API_URL)
            .setContentType(ContentType.JSON)
            .setAccept("*/*")
            .build();
}
