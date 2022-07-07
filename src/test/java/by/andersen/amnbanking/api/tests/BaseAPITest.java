package by.andersen.amnbanking.api.tests;


import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.UsersData;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.*;

@Log4j2
public class BaseAPITest {

    @Step("Registration new user in Data Base")
    public void createUser() {
            new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration(
                    UsersData.USER_EMINEM79.getUser().getLogin(),
                    UsersData.USER_EMINEM79.getUser().getPassword(),
                    UsersData.USER_EMINEM79.getUser().getPassport(),
                    UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                    API_HOST + API_REGISTRATION, 200);
    }

    @Step("Deletes the created user after the test")
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
    }
}

