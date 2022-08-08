package by.andersen.amnbanking.apiControllers;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.User;
import by.andersen.amnbanking.model.Response;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import io.qameta.allure.Step;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;


public class Registration {

    @Step("Registering new user")
    public static Response registeringUser(User user, int expectedStatus) {
        return new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (user.getLogin(),
                                user.getPassword(),
                                user.getPassport(),
                                user.getPhone(),
                                user.getFirstName(),
                                user.getLastName()),
                API_HOST + API_REGISTRATION, expectedStatus).as(Response.class);
    }
}
