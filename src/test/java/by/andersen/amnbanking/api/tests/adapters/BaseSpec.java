package by.andersen.amnbanking.api.tests.adapters;

import by.andersen.amnbanking.utils.PropertyHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseSpec {
static String token = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTI3OTA3MjgsImV4cCI6MTY1Mjg3NzEyOH0.2mFlhiv6b_LUihzD-98R8CHkCQi9OCSO-WlKZpgNPeMwc_XAtkwfj8eM6-yZuIbeKufsJ_NSSFExJYCLoraBHA; Path=/; HttpOnly";
   public static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri(PropertyHelper.getProperty("start.url"))
            .setContentType(ContentType.JSON)
            .setAccept("*/*")
            .build();

   public static final RequestSpecification REQ_SPEC_WITH_TOKEN = new RequestSpecBuilder()
           .setBaseUri(PropertyHelper.getProperty("start.url"))
           .setContentType(ContentType.JSON)
           .addHeader("Authorization", "Bearer " + token)
           .setAccept("*/*")
           .build();
}