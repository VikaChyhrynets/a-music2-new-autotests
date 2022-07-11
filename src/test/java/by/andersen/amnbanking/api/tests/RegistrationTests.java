package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.utils.DataProviderTests;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;

@Epic("Epic 1: Registration and authorization")
public class RegistrationTests extends BaseAPITest {

    @Override
    @BeforeMethod
    @Step("Deletes the created user after the test")
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
    }

    @TmsLinks(value = {@TmsLink("5888304"), @TmsLink("5901579"), @TmsLink("5901827"), @TmsLink("5901829"),
            @TmsLink("5901830"), @TmsLink("5901579")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with valid data", dataProviderClass = DataProviderTests.class,
            description = "valid registration date tests, positive test")
    public void registrationWithValidDateTest(String login, String password, String passport, String phone) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (login, password, passport, phone),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @TmsLinks(value = {@TmsLink("5901876"), @TmsLink("5901831"), @TmsLink("5901832"), @TmsLink("5901848"),
            @TmsLink("5901849"), @TmsLink("5901851"), @TmsLink("5901853"), @TmsLink("5901860")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid passport field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid passport field, negative tests")
    public void registrationInvalidPassportDataTest(String login, String password, String passport, String phone,
                                                    Integer checkStatusCode, String checkResponse) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (login, password, passport, phone),
                API_HOST + API_REGISTRATION, checkStatusCode).as(Response.class);
        Assert.assertEquals(response.getMessage(), checkResponse);
    }


    @TmsLinks(value = {@TmsLink("5895599"), @TmsLink("5895603"), @TmsLink("5895605"), @TmsLink("5895606"),
            @TmsLink("5895612"), @TmsLink("5895614"), @TmsLink("5895629"), @TmsLink("5895638")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid login field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid login field, negative tests")
    public void registrationInvalidLoginDataTest(String login, String password, String passport, String phone,
                                                 Integer checkStatusCode, String checkResponse) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (login, password, passport, phone),
                API_HOST + API_REGISTRATION, checkStatusCode).as(Response.class);
        Assert.assertEquals(response.getMessage(), checkResponse);
    }

    @TmsLinks(value = {@TmsLink("5895776"), @TmsLink("5895786"), @TmsLink("5895791"), @TmsLink("5895794"),
            @TmsLink("5895797"), @TmsLink("5895810")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid password field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid password field, negative tests")
    public void registrationInvalidPasswordDataTest(String login, String password, String passport, String phone,
                                                    Integer checkStatusCode, String checkResponse) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (login, password, passport, phone),
                API_HOST + API_REGISTRATION, checkStatusCode).as(Response.class);
        Assert.assertEquals(response.getMessage(), checkResponse);
    }

    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid phone field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid phone field, negative tests")
    public void registrationInvalidPhoneDataTest(String login, String password, String passport, String phone,
                                                 Integer checkStatusCode, String checkResponse) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (login, password, passport, phone),
                API_HOST + API_REGISTRATION, checkStatusCode).as(Response.class);
        Assert.assertEquals(response.getMessage(), checkResponse);
    }
}
