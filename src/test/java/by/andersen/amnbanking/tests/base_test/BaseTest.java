package by.andersen.amnbanking.tests.base_test;

import by.andersen.amnbanking.DBConnector.DBConnector;
import io.qameta.allure.Step;
import java.sql.SQLException;
import static by.andersen.amnbanking.apiControllers.Registration.registeringUser;
import static by.andersen.amnbanking.data.UsersData.USER_ONE;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;

public abstract class BaseTest {
    public void createUser() {
        registeringUser(USER_ONE.getUser(), SC_OK);
    }

    @Step("Deletes the created user after the test")
    public static void deleteUser() throws SQLException {
        new DBConnector().deleteUser(USER_ONE.getUser().getLogin());
    }

}