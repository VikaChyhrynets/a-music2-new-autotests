package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.data.UsersData;
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
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;

@Epic("Epic 1: Registration and authorization")
public class RegistrationTests extends BaseAPITest {

    @Override
    @BeforeMethod
    @Step("Deletes the created user after the test")
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
    }

    @TmsLink("5888304")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration user with passport, valid date, positive test")
    public void registrationWithValidDateTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_EMINEM79.getUser().getLogin(),
                                UsersData.USER_EMINEM79.getUser().getPassword(),
                                UsersData.USER_EMINEM79.getUser().getPassport(),
                                UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @TmsLink("5901579")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, BIG letters and numbers in passport field, positive test")
    public void registrationValidDateWithPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.EM79_VALID_PASS.getUser().getLogin(),
                                UsersData.EM79_VALID_PASS.getUser().getPassword(),
                                UsersData.EM79_VALID_PASS.getUser().getPassport(),
                                UsersData.EM79_VALID_PASS.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @TmsLink("5901827")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, only digits in passport field, positive test")
    public void registrationValidDateWithOnlyDigitsInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.EM79_VALID_PASS_DIGITS.getUser().getLogin(),
                                UsersData.EM79_VALID_PASS_DIGITS.getUser().getPassword(),
                                UsersData.EM79_VALID_PASS_DIGITS.getUser().getPassport(),
                                UsersData.EM79_VALID_PASS_DIGITS.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @TmsLink("5901829")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, only two BIG letters in passport field, positive test")
    public void registrationValidDateWithTwoLettersInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.EM79_VAL_PASS_2NUMBERS.getUser().getLogin(),
                                UsersData.EM79_VAL_PASS_2NUMBERS.getUser().getPassword(),
                                UsersData.EM79_VAL_PASS_2NUMBERS.getUser().getPassport(),
                                UsersData.EM79_VAL_PASS_2NUMBERS.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @TmsLink("5901830")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, 30 symbols in passport field, positive test")
    public void registrationValidDateWithThirtyValidSymbolsInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.EM79_MAX_SYM_PASS.getUser().getLogin(),
                                UsersData.EM79_MAX_SYM_PASS.getUser().getPassword(),
                                UsersData.EM79_MAX_SYM_PASS.getUser().getPassport(),
                                UsersData.EM79_MAX_SYM_PASS.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @TmsLink("5901579")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, 12 chars in phone field, positive test")
    public void registrationValidDateWithTwelveCharsPhoneTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.EM79_MIN_CHARS_PHONE.getUser().getLogin(),
                                UsersData.EM79_MIN_CHARS_PHONE.getUser().getPassword(),
                                UsersData.EM79_MIN_CHARS_PHONE.getUser().getPassport(),
                                UsersData.EM79_MIN_CHARS_PHONE.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @TmsLink("5901579")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, 16 chars in phone field, positive test")
    public void registrationValidDateWithSixteenCharsPhoneTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.EM79_MAX_CHARS_PHONE.getUser().getLogin(),
                                UsersData.EM79_MAX_CHARS_PHONE.getUser().getPassword(),
                                UsersData.EM79_MAX_CHARS_PHONE.getUser().getPassport(),
                                UsersData.EM79_MAX_CHARS_PHONE.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @TmsLink("5901832")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, forbidden symbol . in passport field, negative test")
    public void registrationWithDuplicatePassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_EMINEM79.getUser().getLogin(),
                                UsersData.USER_EMINEM79.getUser().getPassword(),
                                "AM4567",
                                UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_UNPROCESSABLE_ENTITY).as(Response.class);
        Assert.assertEquals(response.getMessage(), "User with passport = AM4567 already registered");
    }

    @TmsLinks(value = {@TmsLink("5901876"), @TmsLink("5901831"), @TmsLink("5901848"),
            @TmsLink("5901849"), @TmsLink("5901851"), @TmsLink("5901853"), @TmsLink("5901860")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid passport field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid passport field, negative tests")
    public void registrationInvalidPassportDataTest(String passport) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_EMINEM79.getUser().getLogin(),
                                UsersData.USER_EMINEM79.getUser().getPassword(),
                                passport, UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TmsLink("5895599")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, duplicate login field, negative test")
    public void registrationWithDuplicateLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333",
                                UsersData.USER_EMINEM79.getUser().getPassword(),
                                UsersData.USER_EMINEM79.getUser().getPassport(),
                                UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_UNPROCESSABLE_ENTITY).as(Response.class);
        Assert.assertEquals(response.getMessage(),
                "User with login = 11122222222222233333 already registered");
    }

    @TmsLinks(value = {@TmsLink("5895603"), @TmsLink("5895605"), @TmsLink("5895606"),
            @TmsLink("5895612"), @TmsLink("5895614"), @TmsLink("5895629"), @TmsLink("5895638")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid login field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid login field, negative tests")
    public void registrationInvalidLoginDataTest(String login) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (login, UsersData.USER_EMINEM79.getUser().getPassword(),
                                UsersData.USER_EMINEM79.getUser().getPassport(),
                                UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TmsLinks(value = {@TmsLink("5895776"), @TmsLink("5895786"), @TmsLink("5895791"), @TmsLink("5895794"),
            @TmsLink("5895797"), @TmsLink("5895810")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid password field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid password field, negative tests")
    public void registrationInvalidPasswordDataTest(String password) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_EMINEM79.getUser().getLogin(), password,
                                UsersData.USER_EMINEM79.getUser().getPassport(),
                                UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration with duplicate phone number, negative test")
    public void firstRegWithDuplicatePhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_EMINEM79.getUser().getLogin(),
                                UsersData.USER_EMINEM79.getUser().getPassword(),
                                UsersData.USER_EMINEM79.getUser().getPassport(),
                                "+375555555555"),
                API_HOST + API_REGISTRATION, SC_UNPROCESSABLE_ENTITY).as(Response.class);
        Assert.assertEquals(response.getMessage(), "User with phone = +375555555555 already registered");

    }

    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid phone field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid phone field, negative tests")
    public void registrationInvalidPhoneDataTest(String phone) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_EMINEM79.getUser().getLogin(),
                                UsersData.USER_EMINEM79.getUser().getPassword(),
                                UsersData.USER_EMINEM79.getUser().getPassport(), phone),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());
    }

    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration with valid login, password and without passport and phone, negative test")
    public void firstRegWithoutPassportAndPhoneTest() {
        Response response = new PostAdapters().post(new JsonObjectHelper().setJsonObjectForRegistrationWithPhone
                        (UsersData.USER_EMINEM79.getUser().getLogin(),
                                UsersData.USER_EMINEM79.getUser().getPassword(),
                                UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Passport cannot be null");

    }

    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration with valid login, password and  passport without phone, negative test")
    public void firstRegWithoutPhoneAndPhoneTest() {
        Response response = new PostAdapters().post(new JsonObjectHelper().setJsonObjectForRegistrationWithPassport
                        (UsersData.USER_EMINEM79.getUser().getLogin(),
                                UsersData.USER_EMINEM79.getUser().getPassword(),
                                UsersData.USER_EMINEM79.getUser().getPassport()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Phone cannot be null");
    }
}
