package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.apiControllers.Authentication;
import by.andersen.amnbanking.tests.base_test.BaseTest;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import java.sql.SQLException;

public abstract class BaseAPITest extends BaseTest {

    protected Authentication authentication;

    @BeforeMethod
    public void createAuthentication() {
        authentication = new Authentication();
    }

    @Step("Get opening hours for individuals from DB")
    public static void getWorkingHoursForIndividuals() throws SQLException {
        new DBConnector().getIndividualWorkingHoursFromDB();
    }

    @Step("Get phone number for individuals from DB")
    public static void getPhoneNumberForIndividuals() throws SQLException {
        new DBConnector().getPhoneNumberForIndividualsFromDB();
    }

    @Step("Get opening hours for 'Card support' from DB")
    public static void getOpeningHoursForCardSupport() throws SQLException {
        new DBConnector().getOpeningHoursForCardSupportFromDB();
    }

    @Step("Get phone number for cards support from DB")
    public static void getPhoneNumberForCardSupport() throws SQLException {
        new DBConnector().getPhoneNumberForCardSupportFromDB();
    }

}