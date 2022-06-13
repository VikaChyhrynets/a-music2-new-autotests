package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.*;

public class RegistrationTests {

    @Step("Deletes the created user after the test")
    public void deleteUserFromDB() throws SQLException {
        new DBConnector().deleteUser("Eminem78");
    }

    @TestRails(id = "C5888304")
    @Step("Registration new user with valid data, positive test")
    @Test(description = "positive test")
    public void registrationWithValidDateTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "tPvpXJGRqAtbWN8I", "125649846"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        deleteUserFromDB();
    }

    @TestRails(id="C5901579")
    @Step("Registration new user with valid data and BIG letters and numbers in passport field, positive test")
    @Test(description = "positive test")
    public void registrationValidDateWithPassportTest() throws SQLException{
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                ("Eminem78", "8Rvjsio457c", "NX4536489235"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        deleteUserFromDB();
    }

    @TestRails(id="C5901827")
    @Step("Registration new user with valid data and BIG letters and digits in passport field, positive test")
    @Test(description = "positive test")
    public void registrationValidDateWithOnlyDigitsInPassportTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "2Loc4567E", "112364486235"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        deleteUserFromDB();
    }

    @TestRails(id="C5901829")
    @Step("Registration new user with valid data and only two BIG letters in passport field, positive test")
    @Test(description = "positive test")
    public void registrationValidDateWithTwoLettersInPassportTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "2Loc4567E", "LK"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(),AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        deleteUserFromDB();
    }

    @TestRails(id="C5901830")
    @Step("Registration new user with valid data and thirty symbols in passport field, positive test")
    @Test(description = "positive test")
    public void registrationValidDateWithThirtyValidSymbolsInPassportTest() throws SQLException {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "2Loc4567E", "11236489235FR456871230L78D9632"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(),AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        deleteUserFromDB();
    }

    @TestRails(id="C5901876")
    @Step("Registration new user with forbidden symbol . in passport field, negative test")
    @Test(description = "negative test")
    public void registrationValidDateWithForbiddenSymbolInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "2Loc4567E", "11236489235FR456871232."),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(),AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id="C5901831")
    @Step("Registration new user with small russian letters in passport field, negative test")
    @Test(description = "negative")
    public void  registrationWithSmallRussianLettersInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                ("Eminem78", "8Scnjfvs56", "мн88954232156"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id="C5901832")
    @Step("Registration new user with forbidden symbol . in passport field, negative test")
    @Test(description = "negative")
    public void  registrationWithDuplicatePassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78","8Scnjfvs56", "4BGMQ8A3J214"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), "User with passport = 4BGMQ8A3J214 already registered");
    }

    @TestRails(id="C5901848")
    @Step("Registration new user with big russian letters in passport field, negative test")
    @Test(description = "negative")
    public void  registrationWithBigRussianLettersInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "КВ88954232156"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id="C5901849")
    @Step("Registration new user with space in passport field, negative test")
    @Test(description = "negative")
    public void  registrationWithSpaceInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "MN88954232156 "),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id="C5901851")
    @Step("Registration new user with small letters in passport field, negative test")
    @Test(description = "negative")
    public void  registrationWithSmallLettersInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "mn88954232156"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id="C5901853")
    @Step("Registration new user with one big letter in passport field, negative test")
    @Test(description = "negative")
    public void  registrationWithOneLetterInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "P"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id="C5901860")
    @Step("Registration new user with thirty-one symbols in passport field, negative test")
    @Test(description = "negative")
    public void  registrationWithThirtyOneSymbolsInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "PL45873698710254D78519F12547862"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id = "C5895599")
    @Step("Registration new user with duplicate login field, negative test")
    @Test(description = "negative test")
    public void registrationWithDuplicateLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333", "111111111122222222AA", "LP565482130"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(),
                "User with login = 11122222222222233333 already registered");
    }

    @TestRails(id = "C5895603")
    @Step("Registration new user with only numbers login field, negative test")
    @Test(description = "negative test")
    public void firstRegistrationWithOnlyNumbersLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("22233333333333333333", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(),AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895605")
    @Step("Registration new user with invalid char in login field, negative test")
    @Test(description = "negative test")
    public void firstRegistrationWithInvalidCharLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("1113333333333333333a", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895606")
    @Step("Registration new user with invalid cyrillic char login field, negative test")
    @Test(description = "negative test")
    public void firstRegistrationWithInvalidCyrillicLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("111впрТЫ", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895612")
    @Step("Registration new user with less than 7 characters in login field, negative test")
    @Test(description = "negative test")
    public void firstRegistrationWithInvalidLess7CharLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("111111", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895614")
    @Step("Registration new user with more than 20 characters in login field, negative test")
    @Test(description = "negative test")
    public void firstRegWithInvalidMore20CharLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("111111111111111111111", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895629")
    @Step("Registration new user with special symbol in login field, negative test")
    @Test(description = "negative test")
    public void firstRegWithInvalidLoginSpecialSymbolTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        (" 111*?/)! ;", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895638")
    @Step("Registration new user with empty login field, negative test")
    @Test(description = "negative test")
    public void firstRegWithEmptyLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895776")
    @Step("Registration new user with only numbers in password field, negative test")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordSpecialCharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "11122222222222233333"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895786")
    @Step("Registration new user with cyrillic chars in password field, negative test")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordCyrillicCharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "1111111111222222ваТЫ"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895791")
    @Step("Registration new user with less than 7 characters in password field, negative test")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordLess7CharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "111111"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895794")
    @Step("Registration new user with more than 20 characters in password field, negative test")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordMore20CharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "111111111111111111111"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895797")
    @Step("Registration new user with empty password field, negative test")
    @Test(description = "negative test")
    public void firstRegWithEmptyPasswordTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", ""),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895810")
    @Step("Registration new user with only letters in password field, negative test")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordOnlyLettersTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "aaaaaaaaAA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }
}