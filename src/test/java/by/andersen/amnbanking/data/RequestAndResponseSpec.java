package by.andersen.amnbanking.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static by.andersen.amnbanking.data.DataUrls.API_URL;
import static io.restassured.http.ContentType.JSON;


public class RequestAndResponseSpec {
    public static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri(API_URL)
            .setContentType(JSON)
            .setAccept("*/*")
            .setRelaxedHTTPSValidation()
            .build();
}
