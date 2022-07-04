package by.andersen.amnbanking.api.tests;


import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import io.qameta.allure.Step;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.*;

public class BaseAPITest {

    @Step("Registration new user in Data Base")
    public static void createUser() {
        new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem79", "111Gv5dvvf511", "PVS153215DSV", "+40796842639"),
                API_HOST + API_REGISTRATION, 200);
    }

    @Step("Deletes the created user after the test")
    public static void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
    }
}

