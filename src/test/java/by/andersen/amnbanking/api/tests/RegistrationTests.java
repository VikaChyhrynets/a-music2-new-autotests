package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;

public class RegistrationTests {

    @TestRails(id = "C5888304")
    @Test(description = "positive test")
    public void registrationWithValidDateTest() throws SQLException {
        new DBConnector().deleteUser();
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("7qqUqJm00LANA", "tPvpXJGRqAtbWN8I"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @Test(description = "positive test")
    public void registrationWithPassportTest() throws SQLException{
        new DBConnector().deleteUser();
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                ("Elena779", "8Rvjsio7c", "KB7891235"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
    }

    @TestRails(id = "C5895599")
    @Test(description = "negative test")
    public void registrationWithDuplicateLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(),
                "ERROR: duplicate key value violates unique constraint \"users_login_key\"\n" +
                        "  Detail: Key (login)=(11122222222222233333) already exists.");
    }

    @TestRails(id = "C5895603")
    @Test(description = "negative test")
    public void firstRegistrationWithInvalidNumbersLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("22233333333333333333", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(),AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895605")
    @Test(description = "negative test")
    public void firstRegistrationWithInvalidCharLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("1113333333333333333a", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895606")
    @Test(description = "negative test")
    public void firstRegistrationWithInvalidCyrillicLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("111впрТЫ", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895612")
    @Test(description = "negative test")
    public void firstRegistrationWithInvalidLess7CharLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("111111", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895614")
    @Test(description = "negative test")
    public void firstRegWithInvalidMore20CharLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("111111111111111111111", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895629")
    @Test(description = "negative test")
    public void firstRegWithInvalidLoginSpecialSymbolTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        (" 111*?/)! ;", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895638")
    @Test(description = "negative test")
    public void firstRegWithEmptyLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("", "111111111122222222AA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895776")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordSpecialCharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "11122222222222233333"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895786")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordCyrillicCharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "1111111111222222ваТЫ"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895791")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordLess7CharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "111111"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895794")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordMore20CharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "111111111111111111111"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895797")
    @Test(description = "negative test")
    public void firstRegWithEmptyPasswordTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", ""),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895802")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordOnlyNumbersTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "11111111111111111111"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895810")
    @Test(description = "negative test")
    public void firstRegWithInvalidPasswordOnlyLettersTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setJsonObjectForRegistrationAndLogin
                        ("11122222222222233333", "aaaaaaaaAA"),
                API_HOST + API_REGISTRATION).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }
}