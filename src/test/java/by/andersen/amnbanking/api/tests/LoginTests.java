package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.api.tests.objects.Login;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AlertAPI.BAN_USER;
import static by.andersen.amnbanking.data.AlertAPI.INVALID_USERNAME_OR_PASSWORD;
import static by.andersen.amnbanking.data.AlertAPI.LOGIN_SUCCESS;
import static by.andersen.amnbanking.data.AlertAPI.NOT_REGISTERED_USER;
import static by.andersen.amnbanking.data.DataUrls.NOT_REGISTERED_USER_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.USER_BAN_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.USER_BAN_PASS;
import static by.andersen.amnbanking.data.DataUrls.USER_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.USER_PASS;
import static by.andersen.amnbanking.data.DataUrls.USER_WRONG_PASS;
import static by.andersen.amnbanking.data.DoLogin.doLogin;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidLoginWithSpecialCharacter;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidPasswordWithSpecialCharacter;

@Story("UC 1.2 - Web application login")
public class LoginTests {

    @TestRails(id = "C5888309")
    @Test
    public void loginPositive() {
        Response response = doLogin(USER_LOGIN, USER_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), LOGIN_SUCCESS.getValue());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @TestRails(id = "C5893164")
    @Test
    public void loginWithNotRegisteredUser() {
        Response response = doLogin(NOT_REGISTERED_USER_LOGIN, USER_WRONG_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), NOT_REGISTERED_USER.getValue());
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @TestRails(id = "C5894544")
    @Test
    public void loginWithBanUser() {
        Response response = null;
        for(int i = 0; i < 3; i++) {
            response = doLogin(USER_BAN_LOGIN, USER_WRONG_PASS);
        }
        response = doLogin(USER_BAN_LOGIN, USER_BAN_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), BAN_USER.getValue());
        Assert.assertEquals(response.getStatusCode(), 423);
    }

    @TestRails(id = "C5895962")
    @Test
    public void loginWithInvalidLoginLessThanSevenCharacters() {
        Response response = doLogin("111", USER_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @TestRails(id = "C5895963")
    @Test
    public void loginWithInvalidLoginMoreThanTwentyCharacters() {
        Response response = doLogin("111222333444777888999", USER_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
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
        Response response = doLogin("115457821", USER_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @TestRails(id = "C5896425")
    @Test
    public void loginWithLoginOnlyLetters() {
        Response response = doLogin("SomeLogin", USER_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @TestRails(id = "C5896434")
    @Test
    public void loginWithLoginWithoutCapitalLatinLetterAndWithSmallLatinLetter() {
        Response response = doLogin(USER_LOGIN + "f", USER_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @TestRails(id = "C5895966")
    @Test
    public void loginWithEmptyLogin() {
        Response response = doLogin("", USER_PASS);
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @TestRails(id = "C5895968")
    @Test
    public void loginWithInvalidPasswordLessSevenCharacters() {
        Response response = doLogin(USER_LOGIN,"Some1");
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @TestRails(id = "C5895969")
    @Test
    public void loginWithInvalidPasswordMoreThanTwentyCharacters() {
        Response response = doLogin(USER_LOGIN,"SomePassWORd1SomePass");
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @TestRails(id = "C5895970")
    @Test
    public void loginWithInvalidPasswordWithCyrillicCharacter() {
        loginWithInvalidPasswordWithSpecialCharacter("Я");
    }

    @TestRails(id = "C5895972")
    @Test
    public void loginWithEmptyPassword() {
        Response response = doLogin(USER_LOGIN, "");
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @TestRails(id = "C5895973")
    @Test
    public void loginWithPasswordOnlyNumbers() {
        Response response = doLogin(USER_LOGIN, "111234569");
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @TestRails(id = "C5895974")
    @Test
    public void loginWithPasswordOnlyLetters() {
        Response response = doLogin(USER_LOGIN, "SomePassWORd");
        Login login = response.as(Login.class);
        Assert.assertEquals(login.getMessage(), INVALID_USERNAME_OR_PASSWORD.getValue());
        Assert.assertEquals(response.getStatusCode(), 400);
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
}
