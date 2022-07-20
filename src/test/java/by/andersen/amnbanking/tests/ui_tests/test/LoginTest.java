package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.DataProviderTests;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.Alert.EMPTY_LOGIN_OR_PASSWORD_FIELDS;
import static by.andersen.amnbanking.data.Alert.FIELD_CONTAIN_LETTERS_NUMBER;
import static by.andersen.amnbanking.data.Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS;
import static by.andersen.amnbanking.data.Alert.LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS;
import static by.andersen.amnbanking.data.Alert.LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS;
import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.NOT_REGISTERED_USER_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.USER_WRONG_PASS;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_VALID;
import static by.andersen.amnbanking.data.UsersData.*;
import static by.andersen.amnbanking.data.WrongUserData.LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static com.codeborne.selenide.Selenide.refresh;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PERMANENT_REDIRECT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("E-1. Registration and authorization")
@Listeners(UserDeleteListener.class)
public class LoginTest extends BaseUITest {

    @TmsLink("5869665")
    @Test(description = "negative test")
    public void authWithInvalidLoginLessThan7SymbolsTest() {
        loginPage.inputLoginField("Gsvop4")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessageLogin(), LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS.getMessage());
    }

    @TmsLinks(value = {@TmsLink("5869679"), @TmsLink("5900176"), @TmsLink("5900198"), @TmsLink("5900200"),
    @TmsLink("5900201"), @TmsLink("5900204"), @TmsLink("5900206"), @TmsLink("5900210"), @TmsLink("5900241"),
    @TmsLink("5900242"), @TmsLink("5900244"), @TmsLink("5900246"), @TmsLink("5900247"), @TmsLink("5900249"),
    @TmsLink("5900250"), @TmsLink("5900283"), @TmsLink("5900285"), @TmsLink("5900295"), @TmsLink("5900468"),
    @TmsLink("5900469"), @TmsLink("5900470"), @TmsLink("5900471"), @TmsLink("5900473"), @TmsLink("5900751"),
    @TmsLink("5900752"), @TmsLink("5900753")})
    @Test(description = "negative test", dataProvider = "invalid login validation",
            dataProviderClass = DataProviderTests.class)
    public void authWithForbiddenSymbolLoginTest(String login) {
        loginPage.inputLoginField(login)
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getMessage());
    }

    @TmsLink("5869674")
    @Step("Enter valid login and invalid password with forbidden symbols")
    @Test(description = "negative test", dataProvider = "invalid password valid login validation",
            dataProviderClass = DataProviderTests.class)
    public void authAsteriskSymbolPasswordAndValidLoginTest(String password) {
        loginPage.inputLoginField(USER_0NE.getUser().getLogin())
                .inputPasswordField(password)
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), FIELD_CONTAIN_LETTERS_NUMBER.getMessage());
    }

    @TestRails(id = "C5900035")
    @Step("Enter invalid login longer than 20 symbols")
    @Test(description = "negative test")
    public void authWithTwentyOneSymbolLoginTest() {
        loginPage.inputLoginField(MORE_20_CHARS.getUser().getLogin())
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS.getMessage());
    }

    @Test(description = "Enter password longer than 20 symbols, negative test")
    public void authWithTwentyOneSymbolPasswordTest() {
        loginPage.inputPasswordField(MORE_20_CHARS.getUser().getPassword())
                .clickLoginField();
        assertEquals(loginPage.getAlertMessagePassword(), LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS.getMessage());
    }

    @TestRails(id = "C5869669")
    @Step("Enter valid password with blank login field")
    @Test(description = "negative test")
    public void authEmptyLoginAndValidPasswordTest() {
        loginPage.inputLoginField(EMPTY_FIELDS.getUser().getLogin())
                .inputPasswordField(USER_0NE.getUser().getPassword())
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessageLogin(), EMPTY_LOGIN_OR_PASSWORD_FIELDS.getMessage());
    }

    @TestRails(id = "C5869673")
    @Step("Enter valid login with blank password field")
    @Test(description = "negative test")
    public void authEmptyPasswordAndValidLoginTest() {
        loginPage.inputLoginField(USER_0NE.getUser().getLogin())
                .inputPasswordField(EMPTY_FIELDS.getUser().getPassword())
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), EMPTY_LOGIN_OR_PASSWORD_FIELDS.getMessage());
    }

    @Step("Show password button check")
    @Test(description = "negative test")
    public void showPasswordIconTest() {
        assertEquals(loginPage.clickShowPasswordCheckbox("Drn1f7sC", "type"), "text");
    }

    @Step("Hide password button check")
    @Test(description = "negative test")
    public void hidePasswordIconTest() {
        assertEquals(loginPage.clickHidePasswordCheckbox("Drn1f7sC", "type"), "password");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5893442")
    @Test(description = "Authorization after entering the wrong password three times, negative test", enabled = false)
    public void testLoginProcedureWithWrongPasswordThreeTimes() {
        for (int i = 0; i < 3; i++) {
            loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG)
                    .inputPasswordField(USER_WRONG_PASS)
                    .clickLoginButton();
//        assertEquals(loginPage.someMethod(), "Login or password are entered incorrectly.”);
            refresh();
        }
        loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG)
                .inputPasswordField(USER_WRONG_PASS)
                .clickLoginButton();
//        assertEquals(loginPage.someMethod(),  "You have entered an incorrect password or login three times, you can try to log in again in 30 minutes");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5869765")
    @Test(description = "Authorization after entering the wrong login three times, negative test", enabled = false)
    public void testLoginProcedureWithWrongLoginThreeTimes() {
        for (int i = 0; i < 3; i++) {
            loginPage.inputLoginField(NOT_REGISTERED_USER_LOGIN)
                    .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                    .clickLoginButton();
//        assertEquals(loginPage.someMethod(), "Login or password are entered incorrectly.”);
            refresh();
        }
        loginPage.inputLoginField(NOT_REGISTERED_USER_LOGIN)
                .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                .clickLoginButton();
//        assertEquals(loginPage.someMethod(),  "You have entered an incorrect password or login three times, you can try to log in again in 30 minutes");
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5898536")
    @Test(description = "Authorization with valid Login and invalid Password (less than 7 characters) fields, negative test")
    public void testLoginProcedureWithPasswordLessThanSevenCharacters() {
        loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG)
                .inputPasswordField(LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS.getWrongData())
                .clickLoginButton();
        assertEquals(loginPage.getTextFromLoginErrorMessage(), LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS.getMessage());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5869680")
    @Test(description = "Login in lower case, negative test")
    public void testLoginProcedureWithLoginInLowerCase() {
        loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG.toLowerCase())
                .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                .clickLoginButton();
        assertEquals(loginPage.getTextFromLoginErrorMessage(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getMessage());
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5869677")
    @Test(description = "Login from bank (user changed login) and valid password, negative test")
    public void testLoginProcedureWithIncorrectLoginAndValidPassword() {
        loginPage.inputLoginField(NOT_REGISTERED_USER_LOGIN)
                .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                .clickLoginButton();
//        assertEquals(в loginPage должен быть метод, который найдет надпись, “Login or password are entered incorrectly.”,
//        но пока нету xpath, по которому эту надпись искать, соответственно нету и метода);
    }

    @Story("UC 1.2 - Web application login")
    @TmsLink("5869618")
    @Test(description = "Login with valid data, positive test")
    public void testLoginProcedureWithValidData() throws SQLException {
        createUser();
        String authTokenChangePassword = getAuthToken(USER_0NE.getUser().getLogin(),
                USER_0NE.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SMS_VALID.getSms()), API_HOST + API_SESSIONCODE,
                authTokenChangePassword, SC_PERMANENT_REDIRECT);
        USER_0NE.getUser().setPassword(CHANGE_PASSWORD_FIRST_ENTRY);
        new PostAdapters().post(setNewPassword(USER_0NE.getUser().getPassword()),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_OK);
        loginPage.inputLoginField(USER_0NE.getUser().getLogin())
                .inputPasswordField(USER_0NE.getUser().getPassword())
                .clickLoginButton();
        assertTrue(confirmationCodeModalPage.confirmationCodeWindowIsOpen());
        confirmationCodeModalPage
                .inputConfirmSMSField(SMS_VALID.getSms())
                .clickOnConfirmButton();
        assertTrue(confirmationCodeModalPage.isLoginSuccess());
        deleteUser();
    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5880157")
    @Test(description = "First authorization with valid data, positive test")
    public void testFirstAuthorizationWithValidData() throws SQLException {
        createUser();
        loginPage.inputLoginField(USER_0NE.getUser().getLogin())
                .inputPasswordField(USER_0NE.getUser().getPassword())
                .clickLoginButton();
        assertTrue(confirmationCodeModalPage.confirmationCodeWindowIsOpen());
        confirmationCodeModalPage
                .inputConfirmSMSField(SMS_VALID.getSms())
                .clickOnConfirmButton();
//        assertEquals(после ввода правильного смс-кода должны быть перенапрвлены на страницу, где будет предложено
//        изменить логин/пароль при первом входе);
        deleteUser();
    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5880167")
    @Test(description = "First authorization with an unregistered login, negative test")
    public void testFirstAuthorizationWithAnUnregisteredLogin() {

    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5880176")
    @Test(description = "First authorization with an unregistered password, negative test")
    public void testFirstAuthorizationWithAnUnregisteredPassword() {

    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5880179")
    @Test(description = "Password recovery on first authorization, negative test")
    public void testPasswordRecoveryOnFirstAuthorization() {

    }

    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5880189")
    @Test(description = "First authorization after entering the wrong login or password three times , negative test")
    public void testFirstAuthorizationAfterEnteringTheWrongLoginOrPasswordThreeTimes() {

    }
}