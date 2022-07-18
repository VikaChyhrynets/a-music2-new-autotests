package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.UsersData;
import by.andersen.amnbanking.jsonBody.Response;
import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.DataProviderTests;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.ALREADY_REG_LOGIN;
import static by.andersen.amnbanking.data.AlertAPI.ALREADY_REG_PHONE;
import static by.andersen.amnbanking.data.AlertAPI.PASSPORT_NULL;
import static by.andersen.amnbanking.data.AlertAPI.PHONE_NULL;
import static by.andersen.amnbanking.data.AlertAPI.REGISTRATION_FAILED_USER;
import static by.andersen.amnbanking.data.AlertAPI.REGISTRATION_FAILED_USER_PASSPORT;
import static by.andersen.amnbanking.data.AlertAPI.REGISTRATION_FAILED_USER_PHONE;
import static by.andersen.amnbanking.data.AlertAPI.USER_PASS_REGISTERED;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;
import static by.andersen.amnbanking.data.SuccessfulMessages.REGISTRATION_SUCCESS_USER;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;
import static org.testng.Assert.assertEquals;

@Epic("Epic 1: Registration and authorization")
@Listeners(UserDeleteListener.class)
public class RegistrationTests extends BaseAPITest {

    @TmsLink("5888304")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration user with passport, valid date, positive test")
    public void registrationWithValidDateTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.EM79_VALID_PASS.getUser().getPassword(),
                                UsersData.EM79_VALID_PASS.getUser().getPassport(),
                                UsersData.EM79_VALID_PASS.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_SUCCESS_USER);
        deleteUser();
    }

    @TmsLink("5901579")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, BIG letters and numbers in passport field, positive test")
    public void registrationValidDateWithPassportTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.EM79_VALID_PASS.getUser().getPassword(),
                                UsersData.EM79_VALID_PASS.getUser().getPassport(),
                                UsersData.EM79_VALID_PASS.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_SUCCESS_USER);
        deleteUser();
    }

    @TmsLink("5901827")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, only digits in passport field, positive test")
    public void registrationValidDateWithOnlyDigitsInPassportTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.EM79_VALID_PASS_DIGITS.getUser().getPassword(),
                                UsersData.EM79_VALID_PASS_DIGITS.getUser().getPassport(),
                                UsersData.EM79_VALID_PASS_DIGITS.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_SUCCESS_USER);
        deleteUser();
    }

    @TmsLink("5901829")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, only two BIG letters in passport field, positive test")
    public void registrationValidDateWithTwoLettersInPassportTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.EM79_VAL_PASS_2NUMBERS.getUser().getPassword(),
                                UsersData.EM79_VAL_PASS_2NUMBERS.getUser().getPassport(),
                                UsersData.EM79_VAL_PASS_2NUMBERS.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_SUCCESS_USER);
        deleteUser();
    }

    @TmsLink("5901830")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, 30 symbols in passport field, positive test")
    public void registrationValidDateWithThirtyValidSymbolsInPassportTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.EM79_MAX_SYM_PASS.getUser().getPassword(),
                                UsersData.EM79_MAX_SYM_PASS.getUser().getPassport(),
                                UsersData.EM79_MAX_SYM_PASS.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_SUCCESS_USER);
        deleteUser();
    }

    @TmsLink("5901579")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, 12 chars in phone field, positive test")
    public void registrationValidDateWithTwelveCharsPhoneTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.EM79_MIN_CHARS_PHONE.getUser().getPassword(),
                                UsersData.EM79_MIN_CHARS_PHONE.getUser().getPassport(),
                                UsersData.EM79_MIN_CHARS_PHONE.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_SUCCESS_USER);
        deleteUser();
    }

    @TmsLink("5901579")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, valid data, 16 chars in phone field, positive test")
    public void registrationValidDateWithSixteenCharsPhoneTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.EM79_MAX_CHARS_PHONE.getUser().getPassword(),
                                UsersData.EM79_MAX_CHARS_PHONE.getUser().getPassport(),
                                UsersData.EM79_MAX_CHARS_PHONE.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_OK).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_SUCCESS_USER);
        deleteUser();
    }

    @TmsLink("5901832")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, forbidden symbol . in passport field, negative test")
    public void registrationWithDuplicatePassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.USER_0NE.getUser().getPassword(),
                                "AM4567",
                                UsersData.USER_0NE.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_UNPROCESSABLE_ENTITY).as(Response.class);
        assertEquals(response.getMessage(), USER_PASS_REGISTERED);
    }

    @TmsLinks(value = {@TmsLink("5901876"), @TmsLink("5901831"), @TmsLink("5901848"),
            @TmsLink("5901849"), @TmsLink("5901851"), @TmsLink("5901853"), @TmsLink("5901860")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid passport field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid passport field, negative tests")
    public void registrationInvalidPassportDataTest(String passport) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.USER_0NE.getUser().getPassword(),
                                passport, UsersData.USER_0NE.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_FAILED_USER_PASSPORT);
    }

    @TmsLink("5895599")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration, duplicate login field, negative test")
    public void registrationWithDuplicateLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333",
                                UsersData.USER_0NE.getUser().getPassword(),
                                UsersData.USER_0NE.getUser().getPassport(),
                                UsersData.USER_0NE.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_UNPROCESSABLE_ENTITY).as(Response.class);
        assertEquals(response.getMessage(), ALREADY_REG_LOGIN);
    }

    @TmsLinks(value = {@TmsLink("5895603"), @TmsLink("5895605"), @TmsLink("5895606"),
            @TmsLink("5895612"), @TmsLink("5895614"), @TmsLink("5895629"), @TmsLink("5895638")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid login field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid login field, negative tests")
    public void registrationInvalidLoginDataTest(String login) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (login, UsersData.USER_0NE.getUser().getPassword(),
                                UsersData.USER_0NE.getUser().getPassport(),
                                UsersData.USER_0NE.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_FAILED_USER);
    }

    @TmsLinks(value = {@TmsLink("5895776"), @TmsLink("5895786"), @TmsLink("5895791"), @TmsLink("5895794"),
            @TmsLink("5895797"), @TmsLink("5895810")})
    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid password field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid password field, negative tests")
    public void registrationInvalidPasswordDataTest(String password) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(), password,
                                UsersData.USER_0NE.getUser().getPassport(),
                                UsersData.USER_0NE.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_FAILED_USER);
    }

    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration with duplicate phone number, negative test")
    public void firstRegWithDuplicatePhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.USER_0NE.getUser().getPassword(),
                                UsersData.USER_0NE.getUser().getPassport(),
                                UsersData.REG_PHONE_NUMBER.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_UNPROCESSABLE_ENTITY).as(Response.class);
        assertEquals(response.getMessage(), ALREADY_REG_PHONE);

    }

    @Story("UC-1.4 Registration (first login)")
    @Test(dataProvider = "User's registration with invalid phone field data",
            dataProviderClass = DataProviderTests.class,
            description = "Registration with invalid phone field, negative tests")
    public void registrationInvalidPhoneDataTest(String phone) {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.USER_0NE.getUser().getPassword(),
                                UsersData.USER_0NE.getUser().getPassport(), phone),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        assertEquals(response.getMessage(), REGISTRATION_FAILED_USER_PHONE);
    }

    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration with valid login, password and without passport, negative test")
    public void firstRegWithoutPassportAndPhoneTest() {
        Response response = new PostAdapters().post(new JsonObjectHelper().setJsonObjectForRegistrationWithPhone
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.USER_0NE.getUser().getPassword(),
                                UsersData.USER_0NE.getUser().getPhone()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        assertEquals(response.getMessage(), PASSPORT_NULL);

    }

    @Story("UC-1.4 Registration (first login)")
    @Test(description = "Registration with valid login, password and  passport without phone, negative test")
    public void firstRegWithoutPhoneAndPhoneTest() {
        Response response = new PostAdapters().post(new JsonObjectHelper().setJsonObjectForRegistrationWithPassport
                        (UsersData.USER_0NE.getUser().getLogin(),
                                UsersData.USER_0NE.getUser().getPassword(),
                                UsersData.USER_0NE.getUser().getPassport()),
                API_HOST + API_REGISTRATION, SC_BAD_REQUEST).as(Response.class);
        assertEquals(response.getMessage(), PHONE_NULL);
    }
}
