package by.andersen.amnbanking.data;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.api.tests.objects.Login;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import io.restassured.response.Response;
import org.testng.Assert;

import static by.andersen.amnbanking.data.AlertAPI.INVALID_USERNAME_OR_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.USER_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.USER_PASS;

public class DoLogin {
    public static Response doLogin(String login, String password) {

        return new PostAdapters().post(
        JsonObjectHelper.setJsonObjectForRegistrationAndLogin(login, password), API_HOST + API_LOGIN);
    }
    public static void loginWithInvalidLoginWithSpecialCharacter(String specialCharacter) {
        Response response = doLogin(USER_LOGIN + specialCharacter, USER_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    public static void loginWithInvalidPasswordWithSpecialCharacter(String specialCharacter) {
        Response response = doLogin(USER_LOGIN, USER_PASS + specialCharacter);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }
}
