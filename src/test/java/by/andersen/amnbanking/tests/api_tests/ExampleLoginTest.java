package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.data.User;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.utils.PathResolver.AUTH_BY_PHONE;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.is;

public class ExampleLoginTest extends BaseAPITest {
    @Test(description = "Login works")
    public void getInputWorksCorrectly() {
        User user = new User.UserBuilder()
                .withLogin("Vladivostok2000")
                .withPassword("Vladivostok2000")
                .withPassport("PVS153215DSV")
                .withPhone("+10000000000")
                .build();

        given().with().body(user)
                .when()
                .post(AUTH_BY_PHONE)
                .then()
                .log().everything()
                .statusCode(HTTP_OK)
                .body(is("some body..."));
    }
}
