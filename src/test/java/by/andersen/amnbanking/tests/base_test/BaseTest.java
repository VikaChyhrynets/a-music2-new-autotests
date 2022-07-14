package by.andersen.amnbanking.tests.base_test;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import io.qameta.allure.Step;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;
import static by.andersen.amnbanking.data.UserCreator.USER_0NE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setPassportLoginPasswordForRegistration;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;

public abstract class BaseTest {
    @Step("Registration new user in Data Base")
    public void createUser() {
        new PostAdapters().post(setPassportLoginPasswordForRegistration
                        (USER_0NE.getUser().getLogin(),
                                USER_0NE.getUser().getPassword(),
                                USER_0NE.getUser().getPassport(),
                                USER_0NE.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_OK);
    }

    @Step("Deletes the created user after the test")
    public static void deleteUser() throws SQLException {
        new DBConnector().deleteUser(USER_0NE.getUser().getLogin());
    }
}