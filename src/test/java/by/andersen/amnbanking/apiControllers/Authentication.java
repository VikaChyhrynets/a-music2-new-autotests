package by.andersen.amnbanking.apiControllers;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.UsersData;
import by.andersen.amnbanking.model.Response;
import io.qameta.allure.Step;

import static by.andersen.amnbanking.data.AuthToken.loginAndGetBearerToken;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;

public class Authentication {

    @Step("Send session code for authorization")
    public Response sendSessionCode(String bearerToken, String smsCode, int statusCode) {
        return new PostAdapters()
                .post(setSmsCode(smsCode), API_HOST + API_SESSIONCODE, bearerToken, statusCode).as(Response.class);
    }

    @Step("Send session code for authorization")
    public Response sendSessionCode(UsersData user, String smsCode, int statusCode) {
        return new PostAdapters()
                .post(setSmsCode(smsCode), API_HOST + API_SESSIONCODE,
                        loginAndGetBearerToken(user.getUser().getLogin(), user.getUser().getPassword()), statusCode)
                .as(Response.class);
    }
}
