package by.andersen.amnbanking.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static by.andersen.amnbanking.data.DataUrls.API_URL;

public class RequestAndResponseSpec {
    public static final RequestSpecification REQUEST_SPECIFICATION = new RequestSpecBuilder()
            .setBaseUri(API_URL)
            .setContentType(ContentType.JSON)
            .setAccept("*/*")
            .setRelaxedHTTPSValidation()
            .build();

    public static final ResponseSpecification RESPONSE_SPECIFICATION = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectStatusLine("HTTP/1.1 200 ")
            .expectContentType(ContentType.JSON)
            .build();
}
