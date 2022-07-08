package by.andersen.amnbanking.api.tests;

import io.qameta.allure.Step;


import java.sql.SQLException;

public interface CreateDeleteUser {

    @Step("Deletes the created user after the test")
     void deleteUser() throws SQLException;

    @Step("Registration new user in Data Base")
     void createUser();
}
