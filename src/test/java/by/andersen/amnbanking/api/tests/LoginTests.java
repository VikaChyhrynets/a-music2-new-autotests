package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.*;
import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidLoginWithSpecialCharacter;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidPasswordWithSpecialCharacter;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;
import static by.andersen.amnbanking.utils.ParserJson.parser;
import static org.apache.http.HttpStatus.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@Epic("E-1. Registration and authorization")
public class LoginTests extends BaseAPITest {

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5888309")
    @Step("User Log In with valid data, positive test")
    @Test(description = "User Log In with valid data, positive test")
    public void loginPositive() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken("Eminem79", "111Gv5dvvf511");
        new PostAdapters().post(setSmsCode("1234"), API_HOST + API_SESSIONCODE, authTokenChangePassword, 308);
        new PostAdapters().post(setNewPassword("Number12"),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_OK);
        try {
            String response = new PostAdapters()
                    .post(setJsonObjectForRegistrationAndLogin("Eminem79", "Number12"),
                            API_HOST + API_LOGIN, SC_OK).asString();
            assertEquals(parser(response, "message"), LOGIN_SUCCESS.getValue());
            assertNotEquals(parser(response, "phone"), null);
        } finally {
            deleteUser();
        }
    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5893164")
    @Step("Not Registered User Log In, negative test")
    @Test(description = "Not Registered User Log In, negative test")
    public void loginWithNotRegisteredUser() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(NOT_REGISTERED_USER_LOGIN, PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_NOT_FOUND).asString(), "message"), NOT_REGISTERED_USER.getValue());
    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5894544")
    @Step("Block User After Three Incorrect Password Entries, negative test")
    @Test(description = "Block User After Three Incorrect Password Entries, negative test")
    public void loginWithBanUser() throws SQLException {
        try {
            createUser();
            for (int i = 0; i < 3; i++) {
                new PostAdapters().post(setJsonObjectForRegistrationAndLogin("Eminem79", USER_WRONG_PASS),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST);
            }
            assertEquals(parser(new PostAdapters().post(setJsonObjectForRegistrationAndLogin("Eminem79", USER_BAN_PASS),
                    API_HOST + API_LOGIN, SC_LOCKED).asString(), "message"), BAN_USER.getValue());
        } finally {
            deleteUser();
        }
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895962")
    @Step("User's Login Less Than 7 Characters, negative test")
    @Test(description = "User's Login Less Than 7 Characters, negative test")
    public void loginWithInvalidLoginLessThanSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("111", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895963")
    @Step("User's Login More Than 20 Characters, negative test")
    @Test(description = "User's Login More Than 20 Characters, negative test")
    public void loginWithInvalidLoginMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("111222333444777888999", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '!', negative test")
    @Test(description = "User's Login Contain Character '!', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterExclamatory() {
        loginWithInvalidLoginWithSpecialCharacter("!");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '@', negative test")
    @Test(description = "User's Login Contain Character '@', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterDog() {
        loginWithInvalidLoginWithSpecialCharacter("@");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '#', negative test")
    @Test(description = "User's Login Contain Character '#', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterLattice() {
        loginWithInvalidLoginWithSpecialCharacter("#");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '$', negative test")
    @Test(description = "User's Login Contain Character '$', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterDollar() {
        loginWithInvalidLoginWithSpecialCharacter("$");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '%', negative test")
    @Test(description = "User's Login Contain Character '%', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterPercent() {
        loginWithInvalidLoginWithSpecialCharacter("%");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '^', negative test")
    @Test(description = "User's Login Contain Character '^', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterLid() {
        loginWithInvalidLoginWithSpecialCharacter("^");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '&', negative test")
    @Test(description = "User's Login Contain Character '&', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterAmpersand() {
        loginWithInvalidLoginWithSpecialCharacter("&");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '?', negative test")
    @Test(description = "User's Login Contain Character '?', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterInterrogative() {
        loginWithInvalidLoginWithSpecialCharacter("?");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '*', negative test")
    @Test(description = "User's Login Contain Character '*', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterStar() {
        loginWithInvalidLoginWithSpecialCharacter("*");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '\"', negative test")
    @Test(description = "User's Login Contain Character '\"', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterQuote() {
        loginWithInvalidLoginWithSpecialCharacter("\"");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '№', negative test")
    @Test(description = "User's Login Contain Character '№', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterNumber() {
        loginWithInvalidLoginWithSpecialCharacter("№");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character ';', negative test")
    @Test(description = "User's Login Contain Character ';', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterSemicolon() {
        loginWithInvalidLoginWithSpecialCharacter(";");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character ':', negative test")
    @Test(description = "User's Login Contain Character ':', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterColon() {
        loginWithInvalidLoginWithSpecialCharacter(":");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character ', negative test")
    @Test(description = "User's Login Contain Character ', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterSingleQuote() {
        loginWithInvalidLoginWithSpecialCharacter("'");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '\\', negative test")
    @Test(description = "User's Login Contain Character '\\', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterBackslash() {
        loginWithInvalidLoginWithSpecialCharacter("\\");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '/', negative test")
    @Test(description = "User's Login Contain Character '/', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterSlash() {
        loginWithInvalidLoginWithSpecialCharacter("/");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '(', negative test")
    @Test(description = "User's Login Contain Character '(', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterOpenParenthesis() {
        loginWithInvalidLoginWithSpecialCharacter("(");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character ')', negative test")
    @Test(description = "User's Login Contain Character ')', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterCloseParenthesis() {
        loginWithInvalidLoginWithSpecialCharacter(")");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '[', negative test")
    @Test(description = "User's Login Contain Character '[', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterOpenSquareBracket() {
        loginWithInvalidLoginWithSpecialCharacter("[");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character ']', negative test")
    @Test(description = "User's Login Contain Character ']', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterCloseSquareBracket() {
        loginWithInvalidLoginWithSpecialCharacter("]");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '{', negative test")
    @Test(description = "User's Login Contain Character '{', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterOpenBrace() {
        loginWithInvalidLoginWithSpecialCharacter("{");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '}', negative test")
    @Test(description = "User's Login Contain Character '}', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterCloseBrace() {
        loginWithInvalidLoginWithSpecialCharacter("}");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '<', negative test")
    @Test(description = "User's Login Contain Character '<', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterLess() {
        loginWithInvalidLoginWithSpecialCharacter("<");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895964")
    @Step("User's Login Contain Character '>', negative test")
    @Test(description = "User's Login Contain Character '>', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterMore() {
        loginWithInvalidLoginWithSpecialCharacter(">");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895965")
    @Step("User's Login Contain Character 'Я', negative test")
    @Test(description = "User's Login Contain Character 'Я', negative test")
    public void loginWithInvalidLoginWithCyrillicCharacter() {
        loginWithInvalidLoginWithSpecialCharacter("Я");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5896429")
    @Step("User's Login Only Numbers Without The First Three Numbers One, negative test")
    @Test(description = "User's Login Only Numbers Without The First Three Numbers One, negative test")
    public void loginWithLoginOnlyNumbersWithoutTheFirstThreeNumbersOne() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("115457821", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5896425")
    @Step("User's Login Only Letters, negative test")
    @Test(description = "User's Login Only Letters, negative test")
    public void loginWithLoginOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("SomeLogin", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5896434")
    @Step("User's Login Without Capital Latin Letter And With Small Latin Letter, negative test")
    @Test(description = "User's Login Without Capital Latin Letter And With Small Latin Letter, negative test")
    public void loginWithLoginWithoutCapitalLatinLetterAndWithSmallLatinLetter() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("userfortest111", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895966")
    @Step("User's Login Is Empty, negative test")
    @Test(description = "User's Login Is Empty, negative test")
    public void loginWithEmptyLogin() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895968")
    @Step("User's Password Less Than 7 Characters, negative test")
    @Test(description = "User's Password Less Than 7 Characters, negative test")
    public void loginWithInvalidPasswordLessSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, "Some1"),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895969")
    @Step("User's Password More Than 20 Characters, negative test")
    @Test(description = "User's Password More Than 20 Characters, negative test")
    public void loginWithInvalidPasswordMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, "SomePassWORd1SomePass"),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895972")
    @Step("User's Password Is Empty, negative test")
    @Test(description = "User's Password Is Empty, negative test")
    public void loginWithEmptyPassword() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, ""),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895973")
    @Step("User's Password Only Numbers, negative test")
    @Test(description = "User's Password Only Numbers, negative test")
    public void loginWithPasswordOnlyNumbers() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, "111234569"),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895974")
    @Step("User's Password Only Letters, negative test")
    @Test(description = "User's Password Only Letters, negative test")
    public void loginWithPasswordOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, "SomePassWORd"),
                        API_HOST + API_LOGIN, SC_BAD_REQUEST).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '!', negative test")
    @Test(description = "User's Password Contain Character '!', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterExclamatory() {
        loginWithInvalidPasswordWithSpecialCharacter("!");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '@', negative test")
    @Test(description = "User's Password Contain Character '@', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterDog() {
        loginWithInvalidPasswordWithSpecialCharacter("@");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '#', negative test")
    @Test(description = "User's Password Contain Character '#', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterLattice() {
        loginWithInvalidPasswordWithSpecialCharacter("#");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '$', negative test")
    @Test(description = "User's Password Contain Character '$', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterDollar() {
        loginWithInvalidPasswordWithSpecialCharacter("$");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '%', negative test")
    @Test(description = "User's Password Contain Character '%', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterPercent() {
        loginWithInvalidPasswordWithSpecialCharacter("%");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '^', negative test")
    @Test(description = "User's Password Contain Character '^', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterLid() {
        loginWithInvalidPasswordWithSpecialCharacter("^");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '&', negative test")
    @Test(description = "User's Password Contain Character '!', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterAmpersand() {
        loginWithInvalidPasswordWithSpecialCharacter("&");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '?', negative test")
    @Test(description = "User's Password Contain Character '?', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterInterrogative() {
        loginWithInvalidPasswordWithSpecialCharacter("?");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '*', negative test")
    @Test(description = "User's Password Contain Character '*', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterStar() {
        loginWithInvalidPasswordWithSpecialCharacter("*");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '\"', negative test")
    @Test(description = "User's Password Contain Character '\"', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterQuote() {
        loginWithInvalidPasswordWithSpecialCharacter("\"");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '№', negative test")
    @Test(description = "User's Password Contain Character '№', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterNumber() {
        loginWithInvalidPasswordWithSpecialCharacter("№");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character ';', negative test")
    @Test(description = "User's Password Contain Character ';', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterSemicolon() {
        loginWithInvalidPasswordWithSpecialCharacter(";");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character ':', negative test")
    @Test(description = "User's Password Contain Character ':', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterColon() {
        loginWithInvalidPasswordWithSpecialCharacter(":");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character ', negative test")
    @Test(description = "User's Password Contain Character ', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterSingleQuote() {
        loginWithInvalidPasswordWithSpecialCharacter("'");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '\\', negative test")
    @Test(description = "User's Password Contain Character '\\', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterBackslash() {
        loginWithInvalidPasswordWithSpecialCharacter("\\");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '/', negative test")
    @Test(description = "User's Password Contain Character '/', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterSlash() {
        loginWithInvalidPasswordWithSpecialCharacter("/");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '(', negative test")
    @Test(description = "User's Password Contain Character '(', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterOpenParenthesis() {
        loginWithInvalidPasswordWithSpecialCharacter("(");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character ')', negative test")
    @Test(description = "User's Password Contain Character ')', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterCloseParenthesis() {
        loginWithInvalidPasswordWithSpecialCharacter(")");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '[', negative test")
    @Test(description = "User's Password Contain Character '[', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterOpenSquareBracket() {
        loginWithInvalidPasswordWithSpecialCharacter("[");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character ']', negative test")
    @Test(description = "User's Password Contain Character ']', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterCloseSquareBracket() {
        loginWithInvalidPasswordWithSpecialCharacter("]");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '{', negative test")
    @Test(description = "User's Password Contain Character '{', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterOpenBrace() {
        loginWithInvalidPasswordWithSpecialCharacter("{");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '}', negative test")
    @Test(description = "User's Password Contain Character '}', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterCloseBrace() {
        loginWithInvalidPasswordWithSpecialCharacter("}");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '<', negative test")
    @Test(description = "User's Password Contain Character '<', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterLess() {
        loginWithInvalidPasswordWithSpecialCharacter("<");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895967")
    @Step("User's Password Contain Character '>', negative test")
    @Test(description = "User's Password Contain Character '>', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterMore() {
        loginWithInvalidPasswordWithSpecialCharacter(">");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5895970")
    @Step("User's Password Contain Character 'Я', negative test")
    @Test(description = "User's Password Contain Character 'Я', negative test")
    public void loginWithInvalidPasswordWithCyrillicCharacter() {
        loginWithInvalidPasswordWithSpecialCharacter("Я");
    }
}