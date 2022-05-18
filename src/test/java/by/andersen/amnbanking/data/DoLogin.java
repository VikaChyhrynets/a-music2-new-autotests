package by.andersen.amnbanking.data;

import by.andersen.amnbanking.api.tests.adapters_steps.LoginAdapter;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import io.restassured.response.Response;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGIN;

public class DoLogin {
    public static Response doLogin(String login, String password) {
        Response response = new LoginAdapter().postAsClass(API_HOST + API_LOGIN,
        JsonObjectHelper.setJsonObjectForLogin(login, password));

        return response;
    }
}
