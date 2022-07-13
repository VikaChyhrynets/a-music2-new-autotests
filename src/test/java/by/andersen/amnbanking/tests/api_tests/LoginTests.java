package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.listener.UserDeleteListener;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.*;
import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidLoginWithSpecialCharacter;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidPasswordWithSpecialCharacter;
import static by.andersen.amnbanking.data.UserCreator.USER_0NE;
import static by.andersen.amnbanking.data.WrongUserData.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;
import static by.andersen.amnbanking.utils.ParserJson.parser;
import static org.apache.hc.core5.http.HttpStatus.*;
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
        String authTokenChangePassword = getAuthToken(USER_0NE.getUser().getLogin(),
                USER_0NE.getUser().getPassword());
        new PostAdapters().post(setSmsCode("1234"),
                API_HOST + API_SESSIONCODE, authTokenChangePassword, SC_PERMANENT_REDIRECT);
        USER_0NE.getUser().setPassword(CHANGE_PASSWORD_FIRST_ENTRY);
        new PostAdapters().post(setNewPassword(USER_0NE.getUser().getPassword()),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_OK);
        String response = new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(USER_0NE.getUser().getLogin(),
                        USER_0NE.getUser().getPassword()),
                        API_HOST + API_LOGIN, SC_OK).asString();
        assertEquals(parser(response, "message"), LOGIN_SUCCESS.getValue());
        assertNotEquals(parser(response, "phone"), null);
        deleteUser();
    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5893164")
    @Test(description = "Not Registered User Log In, negative test")
    public void loginWithNotRegisteredUser() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(NOT_REGISTERED_USER_LOGIN, PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_NOT_FOUND).asString(), "message"), NOT_REGISTERED_USER.getValue());
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
                USER_BAN_PASS), API_HOST + API_LOGIN, SC_LOCKED).asString(), "message"), BAN_USER.getValue());
        deleteUser();
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895962")
    @Test(description = "User's Login Less Than 7 Characters, negative test")
    public void loginWithInvalidLoginLessThanSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS.getWrongData(), PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895963")
    @Test(description = "User's Login More Than 20 Characters, negative test")
    public void loginWithInvalidLoginMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_0R_PASSWORD_MORE_THAN_20_CHARACTERS.getWrongData(), PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '!', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterExclamatory() {
        loginWithInvalidLoginWithSpecialCharacter("!");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '@', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterDog() {
        loginWithInvalidLoginWithSpecialCharacter("@");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '#', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterLattice() {
        loginWithInvalidLoginWithSpecialCharacter("#");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '$', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterDollar() {
        loginWithInvalidLoginWithSpecialCharacter("$");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '%', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterPercent() {
        loginWithInvalidLoginWithSpecialCharacter("%");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '^', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterLid() {
        loginWithInvalidLoginWithSpecialCharacter("^");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '&', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterAmpersand() {
        loginWithInvalidLoginWithSpecialCharacter("&");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '?', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterInterrogative() {
        loginWithInvalidLoginWithSpecialCharacter("?");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '*', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterStar() {
        loginWithInvalidLoginWithSpecialCharacter("*");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '\"', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterQuote() {
        loginWithInvalidLoginWithSpecialCharacter("\"");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '№', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterNumber() {
        loginWithInvalidLoginWithSpecialCharacter("№");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character ';', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterSemicolon() {
        loginWithInvalidLoginWithSpecialCharacter(";");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character ':', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterColon() {
        loginWithInvalidLoginWithSpecialCharacter(":");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character ', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterSingleQuote() {
        loginWithInvalidLoginWithSpecialCharacter("'");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '\\', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterBackslash() {
        loginWithInvalidLoginWithSpecialCharacter("\\");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '/', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterSlash() {
        loginWithInvalidLoginWithSpecialCharacter("/");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '(', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterOpenParenthesis() {
        loginWithInvalidLoginWithSpecialCharacter("(");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character ')', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterCloseParenthesis() {
        loginWithInvalidLoginWithSpecialCharacter(")");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '[', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterOpenSquareBracket() {
        loginWithInvalidLoginWithSpecialCharacter("[");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character ']', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterCloseSquareBracket() {
        loginWithInvalidLoginWithSpecialCharacter("]");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '{', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterOpenBrace() {
        loginWithInvalidLoginWithSpecialCharacter("{");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '}', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterCloseBrace() {
        loginWithInvalidLoginWithSpecialCharacter("}");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '<', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterLess() {
        loginWithInvalidLoginWithSpecialCharacter("<");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Test(description = "User's Login Contain Character '>', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterMore() {
        loginWithInvalidLoginWithSpecialCharacter(">");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895965")
    @Test(description = "User's Login Contain Character 'Я', negative test")
    public void loginWithInvalidLoginWithCyrillicCharacter() {
        loginWithInvalidLoginWithSpecialCharacter("Я");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5896429")
    @Test(description = "User's Login Only Numbers Without The First Three Numbers One, negative test")
    public void loginWithLoginOnlyNumbersWithoutTheFirstThreeNumbersOne() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_ONLY_NUMBERS_WITHOUT_111_AT_THE_BEGINNING.getWrongData(),
                                PASSWORD_WITH_PASSPORT_REG),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5896425")
    @Test(description = "User's Login Only Letters, negative test")
    public void loginWithLoginOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_OR_PASSWORD_ONLY_LETTERS.getWrongData(), PASSWORD_WITH_PASSPORT_REG),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5896434")
    @Test(description = "User's Login Without Capital Latin Letter And With Small Latin Letter, negative test")
    public void loginWithLoginWithoutCapitalLatinLetterAndWithSmallLatinLetter() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITHOUT_CAPITAL_LETTER.getWrongData(),
                                PASSWORD_WITH_PASSPORT_REG),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895966")
    @Test(description = "User's Login Is Empty, negative test")
    public void loginWithEmptyLogin() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_OR_PASSWORD_FIELD_IS_EMPTY.getWrongData(), PASSWORD_WITH_PASSPORT_REG),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895968")
    @Test(description = "User's Password Less Than 7 Characters, negative test")
    public void loginWithInvalidPasswordLessSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895969")
    @Test(description = "User's Password More Than 20 Characters, negative test")
    public void loginWithInvalidPasswordMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG,
                                LOGIN_0R_PASSWORD_MORE_THAN_20_CHARACTERS.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895972")
    @Test(description = "User's Password Is Empty, negative test")
    public void loginWithEmptyPassword() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, LOGIN_OR_PASSWORD_FIELD_IS_EMPTY.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895973")
    @Test(description = "User's Password Only Numbers, negative test")
    public void loginWithPasswordOnlyNumbers() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, PASSWORD_ONLY_NUMBERS.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895974")
    @Test(description = "User's Password Only Letters, negative test")
    public void loginWithPasswordOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                        .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, LOGIN_OR_PASSWORD_ONLY_LETTERS.getWrongData()),
                                API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '!', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterExclamatory() {
        loginWithInvalidPasswordWithSpecialCharacter("!");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '@', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterDog() {
        loginWithInvalidPasswordWithSpecialCharacter("@");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '#', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterLattice() {
        loginWithInvalidPasswordWithSpecialCharacter("#");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '$', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterDollar() {
        loginWithInvalidPasswordWithSpecialCharacter("$");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '%', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterPercent() {
        loginWithInvalidPasswordWithSpecialCharacter("%");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '^', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterLid() {
        loginWithInvalidPasswordWithSpecialCharacter("^");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '!', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterAmpersand() {
        loginWithInvalidPasswordWithSpecialCharacter("&");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '?', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterInterrogative() {
        loginWithInvalidPasswordWithSpecialCharacter("?");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '*', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterStar() {
        loginWithInvalidPasswordWithSpecialCharacter("*");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '\"', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterQuote() {
        loginWithInvalidPasswordWithSpecialCharacter("\"");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '№', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterNumber() {
        loginWithInvalidPasswordWithSpecialCharacter("№");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character ';', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterSemicolon() {
        loginWithInvalidPasswordWithSpecialCharacter(";");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character ':', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterColon() {
        loginWithInvalidPasswordWithSpecialCharacter(":");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character ', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterSingleQuote() {
        loginWithInvalidPasswordWithSpecialCharacter("'");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '\\', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterBackslash() {
        loginWithInvalidPasswordWithSpecialCharacter("\\");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '/', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterSlash() {
        loginWithInvalidPasswordWithSpecialCharacter("/");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '(', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterOpenParenthesis() {
        loginWithInvalidPasswordWithSpecialCharacter("(");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character ')', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterCloseParenthesis() {
        loginWithInvalidPasswordWithSpecialCharacter(")");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '[', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterOpenSquareBracket() {
        loginWithInvalidPasswordWithSpecialCharacter("[");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character ']', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterCloseSquareBracket() {
        loginWithInvalidPasswordWithSpecialCharacter("]");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '{', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterOpenBrace() {
        loginWithInvalidPasswordWithSpecialCharacter("{");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '}', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterCloseBrace() {
        loginWithInvalidPasswordWithSpecialCharacter("}");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '<', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterLess() {
        loginWithInvalidPasswordWithSpecialCharacter("<");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Test(description = "User's Password Contain Character '>', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterMore() {
        loginWithInvalidPasswordWithSpecialCharacter(">");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895970")
    @Test(description = "User's Password Contain Character 'Я', negative test")
    public void loginWithInvalidPasswordWithCyrillicCharacter() {
        loginWithInvalidPasswordWithSpecialCharacter("Я");
    }
}