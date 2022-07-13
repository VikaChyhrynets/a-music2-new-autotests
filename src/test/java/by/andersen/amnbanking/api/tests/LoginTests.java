package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.UsersData;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.AlertAPI.*;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidLoginWithSpecialCharacter;
import static by.andersen.amnbanking.data.DoLogin.loginWithInvalidPasswordWithSpecialCharacter;
import static by.andersen.amnbanking.data.SuccessfulMessages.LOGIN_SUCCESS;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setJsonObjectForRegistrationAndLogin;
import static by.andersen.amnbanking.utils.ParserJson.parser;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@Story("UC 1.2 - Web application login")
public class LoginTests extends BaseAPITest {
    @Override
    @BeforeMethod
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser(UsersData.USER_EMINEM79.getUser().getLogin());
    }

    @Override
    public void createUser() {
        new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration(
                        UsersData.USER_EMINEM79.getUser().getLogin(),
                        UsersData.USER_EMINEM79.getUser().getPassword(),
                        UsersData.USER_EMINEM79.getUser().getPassport(),
                        UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK);
    }

    @TestRails(id = "C5888309")
    @Step("User Log In with valid data, positive test")
    @Test(description = "User Log In with valid data, positive test")
    public void loginPositive() {
        String response = new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, 200).asString();
        assertEquals(parser(response, "message"), LOGIN_SUCCESS);
        assertNotEquals(parser(response, "phone"), null);
    }

    @TestRails(id = "C5893164")
    @Step("Not Registered User Log In, negative test")
    @Test(description = "Not Registered User Log In, negative test")
    public void loginWithNotRegisteredUser() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(NOT_REGISTERED_USER_LOGIN, PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, 404).asString(), "message"), NOT_REGISTERED_USER);
    }

    @TestRails(id = "C5894544")
    @Step("Block User After Three Incorrect Password Entries, negative test")
    @Test(description = "Block User After Three Incorrect Password Entries, negative test")
    public void loginWithBanUser() {
        createUser();
        for (int i = 0; i < 3; i++) {
            new PostAdapters().post(setJsonObjectForRegistrationAndLogin(UsersData.USER_EMINEM79.getUser().getLogin(),
                            USER_WRONG_PASS),
                    API_HOST + API_LOGIN, 400);
        }
        assertEquals(parser(new PostAdapters().post(setJsonObjectForRegistrationAndLogin("Eminem79", USER_BAN_PASS),
                API_HOST + API_LOGIN, 423).asString(), "message"), BAN_USER);
    }

    @TestRails(id = "C5895962")
    @Step("User's Login Less Than 7 Characters, negative test")
    @Test(description = "User's Login Less Than 7 Characters, negative test")
    public void loginWithInvalidLoginLessThanSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("111", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5895963")
    @Step("User's Login More Than 20 Characters, negative test")
    @Test(description = "User's Login More Than 20 Characters, negative test")
    public void loginWithInvalidLoginMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("111222333444777888999", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5895964 !")
    @Step("User's Login Contain Character '!', negative test")
    @Test(description = "User's Login Contain Character '!', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterExclamatory() {
        loginWithInvalidLoginWithSpecialCharacter("!");
    }

    @TestRails(id = "C5895964 @")
    @Step("User's Login Contain Character '@', negative test")
    @Test(description = "User's Login Contain Character '@', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterDog() {
        loginWithInvalidLoginWithSpecialCharacter("@");
    }

    @TestRails(id = "C5895964 #")
    @Step("User's Login Contain Character '#', negative test")
    @Test(description = "User's Login Contain Character '#', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterLattice() {
        loginWithInvalidLoginWithSpecialCharacter("#");
    }

    @TestRails(id = "C5895964 $")
    @Step("User's Login Contain Character '$', negative test")
    @Test(description = "User's Login Contain Character '$', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterDollar() {
        loginWithInvalidLoginWithSpecialCharacter("$");
    }

    @TestRails(id = "C5895964 %")
    @Step("User's Login Contain Character '%', negative test")
    @Test(description = "User's Login Contain Character '%', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterPercent() {
        loginWithInvalidLoginWithSpecialCharacter("%");
    }

    @TestRails(id = "C5895964 ^")
    @Step("User's Login Contain Character '^', negative test")
    @Test(description = "User's Login Contain Character '^', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterLid() {
        loginWithInvalidLoginWithSpecialCharacter("^");
    }

    @TestRails(id = "C5895964 &")
    @Step("User's Login Contain Character '&', negative test")
    @Test(description = "User's Login Contain Character '&', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterAmpersand() {
        loginWithInvalidLoginWithSpecialCharacter("&");
    }

    @TestRails(id = "C5895964 ?")
    @Step("User's Login Contain Character '?', negative test")
    @Test(description = "User's Login Contain Character '?', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterInterrogative() {
        loginWithInvalidLoginWithSpecialCharacter("?");
    }

    @TestRails(id = "C5895964 *")
    @Step("User's Login Contain Character '*', negative test")
    @Test(description = "User's Login Contain Character '*', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterStar() {
        loginWithInvalidLoginWithSpecialCharacter("*");
    }

    @TestRails(id = "C5895964 \"")
    @Step("User's Login Contain Character '\"', negative test")
    @Test(description = "User's Login Contain Character '\"', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterQuote() {
        loginWithInvalidLoginWithSpecialCharacter("\"");
    }

    @TestRails(id = "C5895964 №")
    @Step("User's Login Contain Character '№', negative test")
    @Test(description = "User's Login Contain Character '№', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterNumber() {
        loginWithInvalidLoginWithSpecialCharacter("№");
    }

    @TestRails(id = "C5895964 ;")
    @Step("User's Login Contain Character ';', negative test")
    @Test(description = "User's Login Contain Character ';', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterSemicolon() {
        loginWithInvalidLoginWithSpecialCharacter(";");
    }

    @TestRails(id = "C5895964 :")
    @Step("User's Login Contain Character ':', negative test")
    @Test(description = "User's Login Contain Character ':', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterColon() {
        loginWithInvalidLoginWithSpecialCharacter(":");
    }

    @TestRails(id = "C5895964 '")
    @Step("User's Login Contain Character ', negative test")
    @Test(description = "User's Login Contain Character ', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterSingleQuote() {
        loginWithInvalidLoginWithSpecialCharacter("'");
    }

    @TestRails(id = "C5895964 \\")
    @Step("User's Login Contain Character '\\', negative test")
    @Test(description = "User's Login Contain Character '\\', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterBackslash() {
        loginWithInvalidLoginWithSpecialCharacter("\\");
    }

    @TestRails(id = "C5895964 /")
    @Step("User's Login Contain Character '/', negative test")
    @Test(description = "User's Login Contain Character '/', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterSlash() {
        loginWithInvalidLoginWithSpecialCharacter("/");
    }

    @TestRails(id = "C5895964 (")
    @Step("User's Login Contain Character '(', negative test")
    @Test(description = "User's Login Contain Character '(', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterOpenParenthesis() {
        loginWithInvalidLoginWithSpecialCharacter("(");
    }

    @TestRails(id = "C5895964 )")
    @Step("User's Login Contain Character ')', negative test")
    @Test(description = "User's Login Contain Character ')', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterCloseParenthesis() {
        loginWithInvalidLoginWithSpecialCharacter(")");
    }

    @TestRails(id = "C5895964 [")
    @Step("User's Login Contain Character '[', negative test")
    @Test(description = "User's Login Contain Character '[', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterOpenSquareBracket() {
        loginWithInvalidLoginWithSpecialCharacter("[");
    }

    @TestRails(id = "C5895964 ]")
    @Step("User's Login Contain Character ']', negative test")
    @Test(description = "User's Login Contain Character ']', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterCloseSquareBracket() {
        loginWithInvalidLoginWithSpecialCharacter("]");
    }

    @TestRails(id = "C5895964 {")
    @Step("User's Login Contain Character '{', negative test")
    @Test(description = "User's Login Contain Character '{', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterOpenBrace() {
        loginWithInvalidLoginWithSpecialCharacter("{");
    }

    @TestRails(id = "C5895964 }")
    @Step("User's Login Contain Character '}', negative test")
    @Test(description = "User's Login Contain Character '}', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterCloseBrace() {
        loginWithInvalidLoginWithSpecialCharacter("}");
    }

    @TestRails(id = "C5895964 <")
    @Step("User's Login Contain Character '<', negative test")
    @Test(description = "User's Login Contain Character '<', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterLess() {
        loginWithInvalidLoginWithSpecialCharacter("<");
    }

    @TestRails(id = "C5895964 >")
    @Step("User's Login Contain Character '>', negative test")
    @Test(description = "User's Login Contain Character '>', negative test")
    public void loginWithInvalidLoginWithSpecialCharacterMore() {
        loginWithInvalidLoginWithSpecialCharacter(">");
    }

    @TestRails(id = "C5895965")
    @Step("User's Login Contain Character 'Я', negative test")
    @Test(description = "User's Login Contain Character 'Я', negative test")
    public void loginWithInvalidLoginWithCyrillicCharacter() {
        loginWithInvalidLoginWithSpecialCharacter("Я");
    }

    @TestRails(id = "C5896429")
    @Step("User's Login Only Numbers Without The First Three Numbers One, negative test")
    @Test(description = "User's Login Only Numbers Without The First Three Numbers One, negative test")
    public void loginWithLoginOnlyNumbersWithoutTheFirstThreeNumbersOne() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("115457821", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5896425")
    @Step("User's Login Only Letters, negative test")
    @Test(description = "User's Login Only Letters, negative test")
    public void loginWithLoginOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("SomeLogin", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5896434")
    @Step("User's Login Without Capital Latin Letter And With Small Latin Letter, negative test")
    @Test(description = "User's Login Without Capital Latin Letter And With Small Latin Letter, negative test")
    public void loginWithLoginWithoutCapitalLatinLetterAndWithSmallLatinLetter() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("userfortest111", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5895966")
    @Step("User's Login Is Empty, negative test")
    @Test(description = "User's Login Is Empty, negative test")
    public void loginWithEmptyLogin() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin("", PASSWORD_WITH_PASSPORT_REG),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5895968")
    @Step("User's Password Less Than 7 Characters, negative test")
    @Test(description = "User's Password Less Than 7 Characters, negative test")
    public void loginWithInvalidPasswordLessSevenCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, "Some1"),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5895969")
    @Step("User's Password More Than 20 Characters, negative test")
    @Test(description = "User's Password More Than 20 Characters, negative test")
    public void loginWithInvalidPasswordMoreThanTwentyCharacters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, "SomePassWORd1SomePass"),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5895972")
    @Step("User's Password Is Empty, negative test")
    @Test(description = "User's Password Is Empty, negative test")
    public void loginWithEmptyPassword() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, ""),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5895973")
    @Step("User's Password Only Numbers, negative test")
    @Test(description = "User's Password Only Numbers, negative test")
    public void loginWithPasswordOnlyNumbers() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, "111234569"),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5895974")
    @Step("User's Password Only Letters, negative test")
    @Test(description = "User's Password Only Letters, negative test")
    public void loginWithPasswordOnlyLetters() {
        assertEquals(parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(LOGIN_WITH_PASSPORT_REG, "SomePassWORd"),
                        API_HOST + API_LOGIN, 400).asString(), "message"), INVALID_USERNAME_OR_PASSWORD);
    }

    @TestRails(id = "C5895967 !")
    @Step("User's Password Contain Character '!', negative test")
    @Test(description = "User's Password Contain Character '!', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterExclamatory() {
        loginWithInvalidPasswordWithSpecialCharacter("!");
    }

    @TestRails(id = "C5895967 @")
    @Step("User's Password Contain Character '@', negative test")
    @Test(description = "User's Password Contain Character '@', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterDog() {
        loginWithInvalidPasswordWithSpecialCharacter("@");
    }

    @TestRails(id = "C5895967 #")
    @Step("User's Password Contain Character '#', negative test")
    @Test(description = "User's Password Contain Character '#', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterLattice() {
        loginWithInvalidPasswordWithSpecialCharacter("#");
    }

    @TestRails(id = "C5895967 $")
    @Step("User's Password Contain Character '$', negative test")
    @Test(description = "User's Password Contain Character '$', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterDollar() {
        loginWithInvalidPasswordWithSpecialCharacter("$");
    }

    @TestRails(id = "C5895967 %")
    @Step("User's Password Contain Character '%', negative test")
    @Test(description = "User's Password Contain Character '%', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterPercent() {
        loginWithInvalidPasswordWithSpecialCharacter("%");
    }

    @TestRails(id = "C5895967 ^")
    @Step("User's Password Contain Character '^', negative test")
    @Test(description = "User's Password Contain Character '^', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterLid() {
        loginWithInvalidPasswordWithSpecialCharacter("^");
    }

    @TestRails(id = "C5895967 &")
    @Step("User's Password Contain Character '&', negative test")
    @Test(description = "User's Password Contain Character '!', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterAmpersand() {
        loginWithInvalidPasswordWithSpecialCharacter("&");
    }

    @TestRails(id = "C5895967 ?")
    @Step("User's Password Contain Character '?', negative test")
    @Test(description = "User's Password Contain Character '?', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterInterrogative() {
        loginWithInvalidPasswordWithSpecialCharacter("?");
    }

    @TestRails(id = "C5895967 *")
    @Step("User's Password Contain Character '*', negative test")
    @Test(description = "User's Password Contain Character '*', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterStar() {
        loginWithInvalidPasswordWithSpecialCharacter("*");
    }

    @TestRails(id = "C5895967 \"")
    @Step("User's Password Contain Character '\"', negative test")
    @Test(description = "User's Password Contain Character '\"', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterQuote() {
        loginWithInvalidPasswordWithSpecialCharacter("\"");
    }

    @TestRails(id = "C5895967 №")
    @Step("User's Password Contain Character '№', negative test")
    @Test(description = "User's Password Contain Character '№', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterNumber() {
        loginWithInvalidPasswordWithSpecialCharacter("№");
    }

    @TestRails(id = "C5895967 ;")
    @Step("User's Password Contain Character ';', negative test")
    @Test(description = "User's Password Contain Character ';', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterSemicolon() {
        loginWithInvalidPasswordWithSpecialCharacter(";");
    }

    @TestRails(id = "C5895967 :")
    @Step("User's Password Contain Character ':', negative test")
    @Test(description = "User's Password Contain Character ':', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterColon() {
        loginWithInvalidPasswordWithSpecialCharacter(":");
    }

    @TestRails(id = "C5895967 '")
    @Step("User's Password Contain Character ', negative test")
    @Test(description = "User's Password Contain Character ', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterSingleQuote() {
        loginWithInvalidPasswordWithSpecialCharacter("'");
    }

    @TestRails(id = "C5895967 \\")
    @Step("User's Password Contain Character '\\', negative test")
    @Test(description = "User's Password Contain Character '\\', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterBackslash() {
        loginWithInvalidPasswordWithSpecialCharacter("\\");
    }

    @TestRails(id = "C5895967 /")
    @Step("User's Password Contain Character '/', negative test")
    @Test(description = "User's Password Contain Character '/', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterSlash() {
        loginWithInvalidPasswordWithSpecialCharacter("/");
    }

    @TestRails(id = "C5895967 (")
    @Step("User's Password Contain Character '(', negative test")
    @Test(description = "User's Password Contain Character '(', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterOpenParenthesis() {
        loginWithInvalidPasswordWithSpecialCharacter("(");
    }

    @TestRails(id = "C5895967 )")
    @Step("User's Password Contain Character ')', negative test")
    @Test(description = "User's Password Contain Character ')', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterCloseParenthesis() {
        loginWithInvalidPasswordWithSpecialCharacter(")");
    }

    @TestRails(id = "C5895967 [")
    @Step("User's Password Contain Character '[', negative test")
    @Test(description = "User's Password Contain Character '[', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterOpenSquareBracket() {
        loginWithInvalidPasswordWithSpecialCharacter("[");
    }

    @TestRails(id = "C5895967 ]")
    @Step("User's Password Contain Character ']', negative test")
    @Test(description = "User's Password Contain Character ']', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterCloseSquareBracket() {
        loginWithInvalidPasswordWithSpecialCharacter("]");
    }

    @TestRails(id = "C5895967 {")
    @Step("User's Password Contain Character '{', negative test")
    @Test(description = "User's Password Contain Character '{', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterOpenBrace() {
        loginWithInvalidPasswordWithSpecialCharacter("{");
    }

    @TestRails(id = "C5895967 }")
    @Step("User's Password Contain Character '}', negative test")
    @Test(description = "User's Password Contain Character '}', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterCloseBrace() {
        loginWithInvalidPasswordWithSpecialCharacter("}");
    }

    @TestRails(id = "C5895967 <")
    @Step("User's Password Contain Character '<', negative test")
    @Test(description = "User's Password Contain Character '<', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterLess() {
        loginWithInvalidPasswordWithSpecialCharacter("<");
    }

    @TestRails(id = "C5895967 >")
    @Step("User's Password Contain Character '>', negative test")
    @Test(description = "User's Password Contain Character '>', negative test")
    public void loginWithInvalidPasswordWithSpecialCharacterMore() {
        loginWithInvalidPasswordWithSpecialCharacter(">");
    }

    @TestRails(id = "C5895970")
    @Step("User's Password Contain Character 'Я', negative test")
    @Test(description = "User's Password Contain Character 'Я', negative test")
    public void loginWithInvalidPasswordWithCyrillicCharacter() {
        loginWithInvalidPasswordWithSpecialCharacter("Я");
    }
}