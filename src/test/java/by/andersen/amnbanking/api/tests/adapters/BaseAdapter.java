package by.andersen.amnbanking.api.tests.adapters;

import by.andersen.amnbanking.utils.PropertyHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseAdapter {

   public static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri(PropertyHelper.getProperty("start.url"))
            .setContentType(ContentType.JSON)
            .setAccept("*/*")
            .build();
}