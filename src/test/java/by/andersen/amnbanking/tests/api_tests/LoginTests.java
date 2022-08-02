package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.DataProviderForTests;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.BAN_USER;
import static by.andersen.amnbanking.data.AlertAPI.INVALID_USERNAME_OR_PASSWORD;
import static by.andersen.amnbanking.data.AlertAPI.NOT_REGISTERED_USER;
import static by.andersen.amnbanking.data.AuthToken.loginAndGetBearerToken;
import static by.andersen.amnbanking.data.DataUrls.API_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.NOT_REGISTERED_USER_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.USER_BAN_PASS;
import static by.andersen.amnbanking.data.DataUrls.USER_WRONG_PASS;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_VALID;
import static by.andersen.amnbanking.data.SuccessfulMessages.LOGIN_SUCCESS;
import static by.andersen.amnbanking.data.UsersData.USER_0NE;
import static by.andersen.amnbanking.data.WrongUserData.LOGIN_0R_PASSWORD_MORE_THAN_20_CHARACTERS;
import static by.andersen.amnbanking.data.WrongUserData.LOGIN_ONLY_NUMBERS_WITHOUT_111_AT_THE_BEGINNING;
import static by.andersen.amnbanking.data.WrongUserData.LOGIN_OR_PASSWORD_FIELD_IS_EMPTY;
import static by.andersen.amnbanking.data.WrongUserData.LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS;
import static by.andersen.amnbanking.data.WrongUserData.LOGIN_OR_PASSWORD_ONLY_LETTERS;
import static by.andersen.amnbanking.data.WrongUserData.LOGIN_WITHOUT_CAPITAL_LETTER;
import static by.andersen.amnbanking.data.WrongUserData.PASSWORD_ONLY_NUMBERS;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setJsonObjectForRegistrationAndLogin;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static by.andersen.amnbanking.utils.ParserJson.parser;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_LOCKED;
import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PERMANENT_REDIRECT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@Epic("E-1. Registration and authorization")
@Listeners(UserDeleteListener.class)
public class LoginTests extends BaseAPITest {

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5888309")
    @Test(description = "User Log In with valid data, positive test")
    public void loginPositive() throws SQLException {
        createUser();
        String authTokenChangePassword = loginAndGetBearerToken(USER_0NE.getUser().getLogin(),
                USER_0NE.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SMS_VALID.getSms()),
                API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PERMANENT_REDIRECT);
        USER_0NE.getUser().setPassword(CHANGE_PASSWORD_FIRST_ENTRY);
        new PostAdapters().post(setNewPassword(USER_0NE.getUser().getPassword()),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_OK);
        String response = new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(USER_0NE.getUser().getLogin(),
                        USER_0NE.getUser().getPassword()),
                        API_HOST + API_LOGIN, SC_OK).asString();
        assertEquals(parser(response, "message"), LOGIN_SUCCESS);
        assertNotEquals(parser(response, "phone"), null);
        deleteUser();
    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5893164")
    @Test(description = "Not Registered User Log In, negative test")
    public void loginWithNotRegisteredUser() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(NOT_REGISTERED_USER_LOGIN, PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_NOT_FOUND).asString(), "message"), NOT_REGISTERED_USER);
    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5894544")
    @Test(description = "Block User After Three Incorrect Password Entries, negative test")
    public void loginWithBanUser() throws SQLException {
        createUser();
        for (int i = 0; i < 3; i++) {
            new PostAdapters().post(setJsonObjectForRegistrationAndLogin(USER_0NE.getUser().getLogin(),
                    USER_WRONG_PASS),
                    API_HOST + API_LOGIN, SC_BAD_REQUEST);
        }
        assertEquals(parser(new PostAdapters().post(setJsonObjectForRegistrationAndLogin(USER_0NE.getUser().getLogin(),
                USER_BAN_PASS), API_HOST + API_LOGIN, SC_LOCKED).asString(), "message"), BAN_USER);
        deleteUser();
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895962")
    @Test(description = "User's Login Less Than 7 Characters, negative test")
    public void loginWithInvalidLoginLessThanSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS.getWrongData(), PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895963")
    @Test(description = "User's Login More Than 20 Characters, negative test")
    public void loginWithInvalidLoginMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_0R_PASSWORD_MORE_THAN_20_CHARACTERS.getWrongData(), PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }


    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Special Character, negative test",
            dataProvider = "Special Character", dataProviderClass = DataProviderForTests.class)
    public void testLoginProcedureWhenLoginContainSpecialCharacter(String specialCharacter) {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG + specialCharacter, PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Special Character, negative test",
            dataProvider = "Special Character", dataProviderClass = DataProviderForTests.class)
    public void testLoginProcedureWhenPasswordContainSpecialCharacter(String specialCharacter) {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG + specialCharacter),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5896429")
    @Test(description = "User's Login Only Numbers Without The First Three Numbers One, negative test")
    public void loginWithLoginOnlyNumbersWithoutTheFirstThreeNumbersOne() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_ONLY_NUMBERS_WITHOUT_111_AT_THE_BEGINNING.getWrongData(),
                                PASSWORD_WITH_PASSPORT_REG),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5896425")
    @Test(description = "User's Login Only Letters, negative test")
    public void loginWithLoginOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_OR_PASSWORD_ONLY_LETTERS.getWrongData(), PASSWORD_WITH_PASSPORT_REG),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5896434")
    @Test(description = "User's Login Without Capital Latin Letter And With Small Latin Letter, negative test")
    public void loginWithLoginWithoutCapitalLatinLetterAndWithSmallLatinLetter() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITHOUT_CAPITAL_LETTER.getWrongData(),
                                PASSWORD_WITH_PASSPORT_REG),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895966")
    @Test(description = "User's Login Is Empty, negative test")
    public void loginWithEmptyLogin() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_OR_PASSWORD_FIELD_IS_EMPTY.getWrongData(), PASSWORD_WITH_PASSPORT_REG),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895968")
    @Test(description = "User's Password Less Than 7 Characters, negative test")
    public void loginWithInvalidPasswordLessSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895969")
    @Test(description = "User's Password More Than 20 Characters, negative test")
    public void loginWithInvalidPasswordMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG,
                                LOGIN_0R_PASSWORD_MORE_THAN_20_CHARACTERS.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895972")
    @Test(description = "User's Password Is Empty, negative test")
    public void loginWithEmptyPassword() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, LOGIN_OR_PASSWORD_FIELD_IS_EMPTY.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895973")
    @Test(description = "User's Password Only Numbers, negative test")
    public void loginWithPasswordOnlyNumbers() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, PASSWORD_ONLY_NUMBERS.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD);
    }

    @Story("UC-1.2 Web application login (second and subsequent logins)")
    @TmsLink("5895974")
    @Test(description = "User's Password Only Letters, negative test")
    public void loginWithPasswordOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, LOGIN_OR_PASSWORD_ONLY_LETTERS.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD);
    }
}