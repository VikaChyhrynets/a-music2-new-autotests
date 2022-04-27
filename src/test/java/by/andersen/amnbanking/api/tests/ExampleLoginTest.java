package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.data.User;
import by.andersen.amnbanking.utils.TestRails;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.utils.PathResolver.AUTH_BY_PHONE;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.is;

public class ExampleLoginTest extends BaseTest {
    @TestRails(id = "123")
    @Test(description = "Login works")
    public void getInputWorksCorrectly() {
        User user = new User.UserBuilder().withPhone("73287083").withPassword("adkhadvk").build();

        given().with().body(user)
                .when()
                .post(AUTH_BY_PHONE)
                .then()
                .log().everything()
                .statusCode(HTTP_OK)
                .body(is("some body..."));
    }
}
