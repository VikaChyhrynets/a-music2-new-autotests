package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.DataProviderTests;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.sql.SQLException;

import static by.andersen.amnbanking.data.Alert.EMPTY_FIELDS;
import static by.andersen.amnbanking.data.Alert.ENTERED_INVALID_PASSWORD_FIRST_TIME;
import static by.andersen.amnbanking.data.Alert.ENTERED_INVALID_PASSWORD_SECOND_TIME;
import static by.andersen.amnbanking.data.Alert.ENTERED_INVALID_PASSWORD_THIRD_TIME;
import static by.andersen.amnbanking.data.Alert.FIELD_CONTAIN_LETTERS_NUMBER;
import static by.andersen.amnbanking.data.Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS;
import static by.andersen.amnbanking.data.Alert.LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS;
import static by.andersen.amnbanking.data.Alert.LOGIN_UNREGISTERED;
import static by.andersen.amnbanking.data.Alert.LOG_IN_WHILE_BANNED;
import static by.andersen.amnbanking.data.AuthToken.loginAndGetBearerToken;
import static by.andersen.amnbanking.data.Alert.LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS;
import static by.andersen.amnbanking.data.DataUrls.API_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.NOT_REGISTERED_USER_LOGIN;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_VALID;
import static by.andersen.amnbanking.data.SuccessfulMessages.RESET_PASSWORD_WINDOW;
import static by.andersen.amnbanking.data.UsersData.*;
import static by.andersen.amnbanking.data.WrongUserData.LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PERMANENT_REDIRECT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("E-1. Registration and authorization")
@Listeners(UserDeleteListener.class)
public class LoginTest extends BaseUITest {

    @Story("UC-1.2 Web application login")
    @TmsLink("5869665")
    @Test(description = "negative test")
    public void authWithInvalidLoginLessThan7SymbolsTest() {
        loginPage.inputLoginField("Gsvop4")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessageLogin(), LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS);
    }

    @Story("UC-1.2 Web application login")
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
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS);
    }

    @Story("UC-1.2 Web application login")
    @TmsLink("5869674")
    @Test(description = "Enter valid login and invalid password with forbidden symbols, negative test",
            dataProvider = "invalid password valid login validation", dataProviderClass = DataProviderTests.class)
    public void authAsteriskSymbolPasswordAndValidLoginTest(String password) {
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(password)
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), FIELD_CONTAIN_LETTERS_NUMBER);
    }

    @Story("UC-1.2 Web application login")
    @TmsLink("5900035")
    @Test(description = "Enter invalid login longer than 20 symbols, negative test")
    public void authWithTwentyOneSymbolLoginTest() {
        loginPage.inputLoginField(MORE_20_CHARS.getUser().getLogin())
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS);
    }

    @Story("UC-1.2 Web application login")
    @Test(description = "Enter password longer than 20 symbols, negative test")
    public void authWithTwentyOneSymbolPasswordTest() {
        loginPage.inputPasswordField(MORE_20_CHARS.getUser().getPassword())
                .clickLoginField();
        assertEquals(loginPage.getAlertMessagePassword(), LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS);
    }

    @Story("UC-1.2 Web application login")
    @TmsLink("5869669")
    @Test(description = "Enter valid password with blank login field, negative test")
    public void authEmptyLoginAndValidPasswordTest() {
        loginPage.inputLoginField(EMPTY_USER_FIELDS.getUser().getLogin())
                .inputPasswordField(USER_ONE.getUser().getPassword());
        loginPage.clickLoginButton();
        assertEquals(loginPage.getAlertMessageLogin(), EMPTY_FIELDS);
    }

    @Story("UC-1.2 Web application login")
    @TmsLink("5869673")
    @Test(description = "Enter valid login with blank password field, negative test")
    public void authEmptyPasswordAndValidLoginTest() {
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(EMPTY_USER_FIELDS.getUser().getPassword())
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), EMPTY_FIELDS);
    }

    @Story("UC-1.2 Web application login")
    @Test(description = "Show password button check, positive test")
    public void showPasswordIconTest() {
        assertEquals(loginPage.clickShowPasswordCheckbox(USER_ONE.getUser().getPassword(), "type"), "text");
    }

    @Story("UC-1.2 Web application login")
    @Test(description = "Hide password button check, positive test")
    public void hidePasswordIconTest() {
        assertEquals(loginPage.clickHidePasswordCheckbox(USER_ONE.getUser().getPassword(), "type"), "password");
    }

    /*
    The test bans user for 30 minutes after entering wrong password 3 times. Each attempt (successful or not) changes
    alert message so that the next run will not appropriate to the "expected" result
     */
    @Story("UC-1.2 Web application login, UC-1.4 Registration")
    @TmsLinks({@TmsLink("C5893442"), @TmsLink("C5974571"), @TmsLink("C5974572"), @TmsLink("C5974577"), @TmsLink("5880176")})
    @Test(description = "Authorization after entering the wrong password three times or unregistered password." +
            "Try to log in before ban expiration")
    public void testLoginProcedureWithWrongPasswordThreeTimes() {
        for (int attempt = 1; attempt < 5; attempt++) {
            loginPage.logInSystem();
            switch (attempt) {
                case (1):
                    assertEquals(loginPage.alertMessage(), ENTERED_INVALID_PASSWORD_FIRST_TIME);
                    break;
                case (2):
                    assertEquals(loginPage.alertMessage(), ENTERED_INVALID_PASSWORD_SECOND_TIME);
                    break;
                case (3):
                    assertEquals(loginPage.alertMessage(), ENTERED_INVALID_PASSWORD_THIRD_TIME);
                    break;
                case (4):
                    assertEquals(loginPage.alertMessage(), LOG_IN_WHILE_BANNED);
                    break;
            }
        }
    }

    @Story("UC-1.2 Web application login")
    @TmsLink("5898536")
    @Test(description = "Authorization with valid Login and invalid Password (less than 7 characters) fields, negative test")
    public void testLoginProcedureWithPasswordLessThanSevenCharacters() {
        loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG)
                .inputPasswordField(LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS.getWrongData())
                .clickLoginButton();
        assertEquals(loginPage.getTextFromLoginErrorMessage(), LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS);
    }

    @Story("UC-1.2 Web application login")
    @TmsLink("5869680")
    @Test(description = "Login in lower case, negative test")
    public void testLoginProcedureWithLoginInLowerCase() {
        loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG.toLowerCase())
                .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                .clickLoginButton();
        assertEquals(loginPage.getTextFromLoginErrorMessage(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS);
    }

    /*
    Check login change
     */
    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5880167")
    @Test(description = "First authorization with an unregistered login or password, negative test")
    public void testLoginProcedureWithIncorrectLoginAndValidPassword() {
        loginPage.inputLoginField(NOT_REGISTERED_USER_LOGIN)
                .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                .clickLoginButton();
        assertEquals(loginPage.alertMessage(), LOGIN_UNREGISTERED);
    }

    @Story("UC-1.2 Web application login")
    @TmsLink("5869618")
    @Test(description = "Login with valid data, positive test")
    public void testLoginProcedureWithValidData() throws SQLException {
        createUser();
        String authTokenChangePassword = loginAndGetBearerToken(USER_ONE.getUser().getLogin(),
                USER_ONE.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SMS_VALID.getSms()), API_HOST + API_SESSIONCODE,
                authTokenChangePassword, SC_PERMANENT_REDIRECT);
        USER_ONE.getUser().setPassword(CHANGE_PASSWORD_FIRST_ENTRY);
        new PostAdapters().post(setNewPassword(USER_ONE.getUser().getPassword()),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authTokenChangePassword, SC_OK);
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(USER_ONE.getUser().getPassword())
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
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(USER_ONE.getUser().getPassword())
                .clickLoginButton();
        assertTrue(confirmationCodeModalPage.confirmationCodeWindowIsOpen());
        assertTrue(confirmationCodeModalPage.confirmationCodeWindowIsOpen());
        confirmationCodeModalPage
                .inputConfirmSMSField(SMS_VALID.getSms())
                .clickOnConfirmButton();
        assertEquals(confirmationCodeModalPage.resetPasswordWindowCheck(), RESET_PASSWORD_WINDOW);
        deleteUser();
    }

    /*
    Should be removed to UC-1.5 "Change password on first login"
    Case: logged user haven't changed the password and tries to
     */
    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5880179")
    @Test(description = "Password recovery on first authorization, negative test")
    public void testPasswordRecoveryOnFirstAuthorization() {

    }

    /*
    Check the error title when entering login or password shorter than 7 symbols
     */
    @Story("UC-1.4 Registration (first login)")
    @TmsLink("5880189")
    @Test(description = "Click Login button after entering login or password with less than 7 symbols")
    public void testAuthorizationAfterEnteringTheWrongLoginOrPassword() {
        loginPage.wrongLoginReg();
        assertEquals(loginPage.getTextFromLoginErrorMessage(), LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS);
        loginPage.wrongPasswordReg();
        assertEquals(loginPage.getAlertMessagePassword(), LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS);

    }

}