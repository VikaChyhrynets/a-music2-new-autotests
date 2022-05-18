package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.api.tests.objects.Login;
import by.andersen.amnbanking.utils.TestRails;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.DoLogin.doLogin;

public class LoginTests {

    @TestRails(id = "C5888309")
    @Test
    public void LoginPositive() {
        Response response = doLogin("","");
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), "Access granted");
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
