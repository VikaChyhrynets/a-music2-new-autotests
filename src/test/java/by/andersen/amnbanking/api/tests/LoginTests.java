package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AlertAPI.*;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidLoginWithSpecialCharacter;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidPasswordWithSpecialCharacter;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setJsonObjectForRegistrationAndLogin;
import static by.andersen.amnbanking.utils.ParserJson.parser;
import static org.testng.Assert.assertEquals;

@Story("UC 1.2 - Web application login")
public class LoginTests {

    @TestRails(id = "C5888309")
    @Test
    public void loginPositive() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(USER_LOGIN, USER_PASS),
                        API_HOST + API_LOGIN, 200).asString(), "message"), LOGIN_SUCCESS.getValue());
    }

    @TestRails(id = "C5893164")
    @Test
    public void loginWithNotRegisteredUser() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(NOT_REGISTERED_USER_LOGIN, USER_WRONG_PASS),
                        API_HOST + API_LOGIN, 404).asString(), "message"), NOT_REGISTERED_USER.getValue());
    }

    @TestRails(id = "C5894544")
    @Test
    public void loginWithBanUser() {
        for (int i = 0; i < 3; i++) {
            new PostAdapters().post(setJsonObjectForRegistrationAndLogin(USER_BAN_LOGIN, USER_WRONG_PASS),
                    API_HOST + API_LOGIN, 423);
        }
        assertEquals(parser(new PostAdapters().post(setJsonObjectForRegistrationAndLogin(USER_BAN_LOGIN, USER_BAN_PASS),
                API_HOST + API_LOGIN, 423).asString(), "message"), BAN_USER.getValue());
    }

    @TestRails(id = "C5895962")
    @Test
    public void loginWithInvalidLoginLessThanSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("111", USER_PASS),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5895963")
    @Test
    public void loginWithInvalidLoginMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("111222333444777888999", USER_PASS),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5895964 !")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterExclamatory() {
        loginWithInvalidLoginWithSpecialCharacter("!");
    }

    @TestRails(id = "C5895964 @")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterDog() {
        loginWithInvalidLoginWithSpecialCharacter("@");
    }

    @TestRails(id = "C5895964 #")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterLattice() {
        loginWithInvalidLoginWithSpecialCharacter("#");
    }

    @TestRails(id = "C5895964 $")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterDollar() {
        loginWithInvalidLoginWithSpecialCharacter("$");
    }

    @TestRails(id = "C5895964 %")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterPercent() {
        loginWithInvalidLoginWithSpecialCharacter("%");
    }

    @TestRails(id = "C5895964 ^")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterLid() {
        loginWithInvalidLoginWithSpecialCharacter("^");
    }

    @TestRails(id = "C5895964 &")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterAmpersand() {
        loginWithInvalidLoginWithSpecialCharacter("&");
    }

    @TestRails(id = "C5895964 ?")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterInterrogative() {
        loginWithInvalidLoginWithSpecialCharacter("?");
    }

    @TestRails(id = "C5895964 *")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterStar() {
        loginWithInvalidLoginWithSpecialCharacter("*");
    }

    @TestRails(id = "C5895964 \"")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterQuote() {
        loginWithInvalidLoginWithSpecialCharacter("\"");
    }

    @TestRails(id = "C5895964 №")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterNumber() {
        loginWithInvalidLoginWithSpecialCharacter("№");
    }

    @TestRails(id = "C5895964 ;")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterSemicolon() {
        loginWithInvalidLoginWithSpecialCharacter(";");
    }

    @TestRails(id = "C5895964 :")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterColon() {
        loginWithInvalidLoginWithSpecialCharacter(":");
    }

    @TestRails(id = "C5895964 '")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterSingleQuote() {
        loginWithInvalidLoginWithSpecialCharacter("'");
    }

    @TestRails(id = "C5895964 \\")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterBackslash() {
        loginWithInvalidLoginWithSpecialCharacter("\\");
    }

    @TestRails(id = "C5895964 /")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterSlash() {
        loginWithInvalidLoginWithSpecialCharacter("/");
    }

    @TestRails(id = "C5895964 (")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterOpenParenthesis() {
        loginWithInvalidLoginWithSpecialCharacter("(");
    }

    @TestRails(id = "C5895964 )")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterCloseParenthesis() {
        loginWithInvalidLoginWithSpecialCharacter(")");
    }

    @TestRails(id = "C5895964 [")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterOpenSquareBracket() {
        loginWithInvalidLoginWithSpecialCharacter("[");
    }

    @TestRails(id = "C5895964 ]")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterCloseSquareBracket() {
        loginWithInvalidLoginWithSpecialCharacter("]");
    }

    @TestRails(id = "C5895964 {")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterOpenBrace() {
        loginWithInvalidLoginWithSpecialCharacter("{");
    }

    @TestRails(id = "C5895964 }")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterCloseBrace() {
        loginWithInvalidLoginWithSpecialCharacter("}");
    }

    @TestRails(id = "C5895964 <")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterLess() {
        loginWithInvalidLoginWithSpecialCharacter("<");
    }

    @TestRails(id = "C5895964 >")
    @Test
    public void loginWithInvalidLoginWithSpecialCharacterMore() {
        loginWithInvalidLoginWithSpecialCharacter(">");
    }

    @TestRails(id = "C5895965")
    @Test
    public void loginWithInvalidLoginWithCyrillicCharacter() {
        loginWithInvalidLoginWithSpecialCharacter("Я");
    }

    @TestRails(id = "C5896429")
    @Test
    public void loginWithLoginOnlyNumbersWithoutTheFirstThreeNumbersOne() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("115457821", USER_PASS),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5896425")
    @Test
    public void loginWithLoginOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("SomeLogin", USER_PASS),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5896434")
    @Test
    public void loginWithLoginWithoutCapitalLatinLetterAndWithSmallLatinLetter() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(USER_LOGIN + "f", USER_PASS),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5895966")
    @Test
    public void loginWithEmptyLogin() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("", USER_PASS),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5895968")
    @Test
    public void loginWithInvalidPasswordLessSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(USER_LOGIN, "Some1"),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5895969")
    @Test
    public void loginWithInvalidPasswordMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(USER_LOGIN, "SomePassWORd1SomePass"),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5895972")
    @Test
    public void loginWithEmptyPassword() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(USER_LOGIN, ""),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5895973")
    @Test
    public void loginWithPasswordOnlyNumbers() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(USER_LOGIN, "111234569"),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5895974")
    @Test
    public void loginWithPasswordOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(USER_LOGIN, "SomePassWORd"),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    @TestRails(id = "C5895967 !")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterExclamatory() {
        loginWithInvalidPasswordWithSpecialCharacter("!");
    }

    @TestRails(id = "C5895967 @")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterDog() {
        loginWithInvalidPasswordWithSpecialCharacter("@");
    }

    @TestRails(id = "C5895967 #")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterLattice() {
        loginWithInvalidPasswordWithSpecialCharacter("#");
    }

    @TestRails(id = "C5895967 $")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterDollar() {
        loginWithInvalidPasswordWithSpecialCharacter("$");
    }

    @TestRails(id = "C5895967 %")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterPercent() {
        loginWithInvalidPasswordWithSpecialCharacter("%");
    }

    @TestRails(id = "C5895967 ^")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterLid() {
        loginWithInvalidPasswordWithSpecialCharacter("^");
    }

    @TestRails(id = "C5895967 &")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterAmpersand() {
        loginWithInvalidPasswordWithSpecialCharacter("&");
    }

    @TestRails(id = "C5895967 ?")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterInterrogative() {
        loginWithInvalidPasswordWithSpecialCharacter("?");
    }

    @TestRails(id = "C5895967 *")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterStar() {
        loginWithInvalidPasswordWithSpecialCharacter("*");
    }

    @TestRails(id = "C5895967 \"")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterQuote() {
        loginWithInvalidPasswordWithSpecialCharacter("\"");
    }

    @TestRails(id = "C5895967 №")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterNumber() {
        loginWithInvalidPasswordWithSpecialCharacter("№");
    }

    @TestRails(id = "C5895967 ;")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterSemicolon() {
        loginWithInvalidPasswordWithSpecialCharacter(";");
    }

    @TestRails(id = "C5895967 :")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterColon() {
        loginWithInvalidPasswordWithSpecialCharacter(":");
    }

    @TestRails(id = "C5895967 '")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterSingleQuote() {
        loginWithInvalidPasswordWithSpecialCharacter("'");
    }

    @TestRails(id = "C5895967 \\")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterBackslash() {
        loginWithInvalidPasswordWithSpecialCharacter("\\");
    }

    @TestRails(id = "C5895967 /")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterSlash() {
        loginWithInvalidPasswordWithSpecialCharacter("/");
    }

    @TestRails(id = "C5895967 (")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterOpenParenthesis() {
        loginWithInvalidPasswordWithSpecialCharacter("(");
    }

    @TestRails(id = "C5895967 )")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterCloseParenthesis() {
        loginWithInvalidPasswordWithSpecialCharacter(")");
    }

    @TestRails(id = "C5895967 [")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterOpenSquareBracket() {
        loginWithInvalidPasswordWithSpecialCharacter("[");
    }

    @TestRails(id = "C5895967 ]")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterCloseSquareBracket() {
        loginWithInvalidPasswordWithSpecialCharacter("]");
    }

    @TestRails(id = "C5895967 {")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterOpenBrace() {
        loginWithInvalidPasswordWithSpecialCharacter("{");
    }

    @TestRails(id = "C5895967 }")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterCloseBrace() {
        loginWithInvalidPasswordWithSpecialCharacter("}");
    }

    @TestRails(id = "C5895967 <")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterLess() {
        loginWithInvalidPasswordWithSpecialCharacter("<");
    }

    @TestRails(id = "C5895967 >")
    @Test
    public void loginWithInvalidPasswordWithSpecialCharacterMore() {
        loginWithInvalidPasswordWithSpecialCharacter(">");
    }

    @TestRails(id = "C5895970")
    @Test
    public void loginWithInvalidPasswordWithCyrillicCharacter() {
        loginWithInvalidPasswordWithSpecialCharacter("Я");
    }
}