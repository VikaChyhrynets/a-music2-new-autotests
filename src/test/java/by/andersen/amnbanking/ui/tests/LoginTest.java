package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.Alert;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS;
import static by.andersen.amnbanking.data.Alert.LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setPassportLoginPasswordForRegistration;
import static com.codeborne.selenide.Selenide.refresh;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @TestRails(id = "C5869665")
    @Step("Enter invalid login shorter than 7 symbols")
    @Test(description = "negative test")
    public void authWithInvalidLoginLessThan7SymbolsTest() {
        loginPage.inputLoginField("Gsvop4")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessageLogin(), LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869679")
    @Step("Enter invalid symbols into login field")
    @Test(description = "negative test")
    public void authWithForbiddenSymbolLoginTest() {
        loginPage.inputLoginField("Fklid7*@")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900035")
    @Step("Enter invalid login longer than 20 symbols")
    @Test(description = "negative test")
    public void authWithTwentyOneSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbjgldrkmk8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), Alert.LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS.getValue());
    }

    @TestRails(id = "C5900176")
    @Step("Enter invalid login with ~ symbol")
    @Test(description = "negative test")
    public void authTildaSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnb~mk8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900198")
    @Step("Enter invalid login with @ symbol")
    @Test(description = "negative test")
    public void authAtSymbolLoginTest() {
        loginPage.inputLoginField("@5Dvkfefnbmk8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900200")
    @Step("Enter invalid login with - symbol")
    @Test(description = "negative test")
    public void authMinusSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkf-efnbmk8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900201")
    @Step("Enter invalid login with / symbol")
    @Test(description = "negative test")
    public void authSlashSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbmk8ftt/")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900204")
    @Step("Enter invalid login with + symbol")
    @Test(description = "negative test")
    public void authPlusSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbmk8+ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900206")
    @Step("Enter invalid login with ^ symbol")
    @Test(description = "negative test")
    public void authCircumflexSymbolLoginTest() {
        loginPage.inputLoginField("5Dv^kfefnbmk8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900210")
    @Step("Enter invalid login with « symbol")
    @Test(description = "negative test")
    public void authQuotationSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnb«mkftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900241")
    @Step("Enter invalid login with * symbol")
    @Test(description = "negative test")
    public void authAsteriskSymbolLoginTest() {
        loginPage.inputLoginField("5*Dvkfefnbmk8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900242")
    @Step("Enter invalid login with . symbol")
    @Test(description = "negative test")
    public void authDotSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm.k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900244")
    @Step("Enter invalid login with > symbol")
    @Test(description = "negative test")
    public void authTagSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm>k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900246")
    @Step("Enter SQL query to login field")
    @Test(description = "negative test")
    public void authSQLSymbolLoginTest() {
        loginPage.inputLoginField("SELECT*FROM users")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900247")
    @Step("Enter invalid login with ? symbol")
    @Test(description = "negative test")
    public void authQuestionSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm?k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900249")
    @Step("Enter invalid login with & symbol")
    @Test(description = "negative test")
    public void authAmpersandSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm&k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900250")
    @Step("Enter invalid login with # symbol")
    @Test(description = "negative test")
    public void authHashtagSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm#k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900283")
    @Step("Enter invalid login with ] symbol")
    @Test(description = "negative test")
    public void authNumberSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm]k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900285")
    @Step("Enter invalid login with space")
    @Test(description = "negative test")
    public void authWhitespaceLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900295")
    @Step("Enter invalid login with ❞ symbol")
    @Test(description = "negative test")
    public void authDoubleUpperCommaLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm❞k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900468")
    @Step("Enter invalid login with $ symbol")
    @Test(description = "negative test")
    public void authDollarSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm$k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900469")
    @Step("Enter invalid login with _ symbol")
    @Test(description = "negative test")
    public void authUnderliningSymbolLoginTest() {
        loginPage.inputLoginField("_5Dvkfefnbmk8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900470")
    @Step("Enter invalid login with ' symbol")
    @Test(description = "negative test")
    public void authApostropheSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm'k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900471")
    @Step("Enter invalid login with = symbol")
    @Test(description = "negative test")
    public void authEquallySymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm=k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900473")
    @Step("Enter invalid login with : symbol")
    @Test(description = "negative test")
    public void authColonSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefn:k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900751")
    @Step("Enter invalid login with ; symbol")
    @Test(description = "negative test")
    public void authSemicolonSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefn;k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900752")
    @Step("Enter invalid login with ! symbol")
    @Test(description = "negative test")
    public void authExclamationMarkLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm!k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900753")
    @Step("Enter invalid login with ← symbol")
    @Test(description = "negative test")
    public void authWithArrowLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm←k8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Step("Enter login with cyrillic letters")
    @Test(description = "negative test")
    public void authRussianSymbolLoginTest() {
        loginPage.inputLoginField("А5Dvkfefbmk8ftt")
                .clickPasswordField();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Step("Enter password longer than 20 symbols")
    @Test(description = "negative test")
    public void authWithTwentyOneSymbolPasswordTest() {
        loginPage.inputPasswordField("584841Cd2154ddvnvddvb")
                .clickLoginField();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS.getValue());
    }

    @TestRails(id = "")
    @Step("Enter invalid password with » symbol")
    @Test(description = "negative test")
    public void authWithQuotationSymbolPasswordTest() {
        loginPage.inputPasswordField("584841Cd»nvddvb")
                .clickLoginField();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter invalid password with ❝ symbol")
    @Test(description = "negative test")
    public void authWithDoubleUpperCommaSymbolPasswordTest() {
        loginPage.inputPasswordField("584841Cd❝nvddvb")
                .clickLoginField();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "C5869669")
    @Step("Enter valid password with blank login field")
    @Test(description = "negative test")
    public void authEmptyLoginAndValidPasswordTest() {
        loginPage.inputLoginField("")
                .inputPasswordField("84dcB565d")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessageLogin(), Alert.EMPTY_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869673")
    @Step("Enter valid login with blank password field")
    @Test(description = "negative test")
    public void authEmptyPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.EMPTY_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869681")
    @Step("Enter all capital letters login and valid password")
    @Test(description = "negative test")
    public void authValidPasswordAndOnlyCapitalLetterInLoginTest() {
        loginPage.inputLoginField("RTNZKCVF")
                .inputPasswordField("84dDc565d")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessageLogin(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869674")
    @Step("Enter valid login and invalid password with * symbol")
    @Test(description = "negative test")
    public void authAsteriskSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("*12Ty354785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ! symbol")
    @Test(description = "negative test")
    public void authExclamationSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("!1235Xc4785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with - symbol")
    @Test(description = "negative test")
    public void authBashSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("-1Vc2354785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with @ symbol")
    @Test(description = "negative test")
    public void authAtSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("@2354Lk785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with + symbol")
    @Test(description = "negative test")
    public void authPlusSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("+23547Mn85")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and password with cyrillic letters")
    @Test(description = "negative test")
    public void authRussianSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Кsc8kvmx")
                .inputPasswordField("З23Rb54785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with - symbol")
    @Test(description = "negative test")
    public void authMinusSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("-235Xn4785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with space")
    @Test(description = "negative test")
    public void authWhitespacePasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField(" 23Dv54785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with / symbol")
    @Test(description = "negative test")
    public void authSlashPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("/23Dv54785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with , symbol")
    @Test(description = "negative test")
    public void authCommaPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("2354,78Dc5")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with < symbol")
    @Test(description = "negative test")
    public void authTagsPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("<2354785Fv")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ∑ symbol")
    @Test(description = "negative test")
    public void authMathematicsSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("X∑235n4785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter SQL query in password input field")
    @Test(description = "negative test")
    public void authSQLInjectionPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("SELECT*FROM users1")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ? symbol")
    @Test(description = "negative test")
    public void authQuestionPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("235478Sa5?")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with & symbol")
    @Test(description = "negative test")
    public void authAmpersandPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4&7z85")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with . symbol")
    @Test(description = "negative test")
    public void authDotPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4.c785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with : symbol")
    @Test(description = "negative test")
    public void authWithColonPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4:c785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ; symbol")
    @Test(description = "negative test")
    public void authSemicolonPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4;c785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with % symbol")
    @Test(description = "negative test")
    public void authPercentPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4c785%")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with $ symbol")
    @Test(description = "negative test")
    public void authDollarPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4c785$")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with # symbol")
    @Test(description = "negative test")
    public void authHashtagPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("#23Q4c785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with № symbol")
    @Test(description = "negative test")
    public void authNumberPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("№23Q4c785")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ~ symbol")
    @Test(description = "negative test")
    public void authTildePasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q~4c785d")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ➝ symbol")
    @Test(description = "negative test")
    public void authWithArrowPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q➝4c785d")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ¼ symbol")
    @Test(description = "negative test")
    public void authWithFractionPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q¼4c785d")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ^ symbol")
    @Test(description = "negative test")
    public void authWithCircumflexPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q^4c785d")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ' symbol")
    @Test(description = "negative test")
    public void authWithApostrophePasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("I'l4584c785d")
                .clickLoginButton();
        assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Show password button check")
    @Test(description = "negative test")
    public void showPasswordIconTest() {
        assertEquals(loginPage.clickShowPasswordCheckbox("Drn1f7sC", "type"), "text");
    }

    @TestRails(id = "")
    @Step("Hide password button check")
    @Test(description = "negative test")
    public void hidePasswordIconTest() {
        assertEquals(loginPage.clickHidePasswordCheckbox("Drn1f7sC", "type"), "password");
    }

    @TestRails(id = "C5893442")
    @Step("Authorization after entering the wrong password three times, negative test")
    @Test(description = "Authorization after entering the wrong password three times, negative test")
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

    @TestRails(id = "C5869765")
    @Step("Authorization after entering the wrong login three times, negative test")
    @Test(description = "Authorization after entering the wrong login three times, negative test")
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

    @TestRails(id = "C5898536")
    @Step("Authorization with valid Login and invalid Password (less than 7 characters) fields, negative test")
    @Test(description = "Authorization with valid Login and invalid Password (less than 7 characters) fields, negative test")
    public void testLoginProcedureWithPasswordLessThanSevenCharacters() {
        loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG)
                .inputPasswordField("Pad11")
                .clickLoginButton();
        assertEquals(loginPage.getTextFromLoginErrorMessage(), LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869680")
    @Step("Login in lower case, negative test")
    @Test(description = "Login in lower case, negative test")
    public void testLoginProcedureWithLoginInLowerCase() {
        loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG.toLowerCase())
                .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                .clickLoginButton();
        assertEquals(loginPage.getTextFromLoginErrorMessage(), FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869677")
    @Step("Login from bank (user changed login) and valid password, negative test")
    @Test(description = "Login from bank (user changed login) and valid password, negative test")
    public void testLoginProcedureWithIncorrectLoginAndValidPassword() {
        loginPage.inputLoginField(NOT_REGISTERED_USER_LOGIN)
                .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                .clickLoginButton();
//        assertEquals(в loginPage должен быть метод, который найдет надпись, “Login or password are entered incorrectly.”,
//        но пока нету xpath, по которому эту надпись искать, соответственно нету и метода);
    }

    @TestRails(id = "C5869618")
    @Step("Login with valid data, positive test")
    @Test(description = "Login with valid data, positive test")
    public void testLoginProcedureWithValidData() {
        loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG)
                .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                .clickLoginButton();
        assertTrue(confirmationCodeModalPage.confirmationCodeWindowIsOpen());
        confirmationCodeModalPage
                .inputConfirmSMSField("1234")
                .clickOnConfirmButton();
        assertTrue(confirmationCodeModalPage.isLoginSuccess());
    }

    @TestRails(id = "C5880157")
    @Step("First authorization with valid data, positive test")
    @Test(description = "First authorization with valid data, positive test", enabled = false)
    public void testFirstAuthorizationWithValidData() throws SQLException {
        new PostAdapters().post(setPassportLoginPasswordForRegistration
                        ("Eminem100", "111Gv5dv", "PVS153215DS", "+10000000000"),
                API_HOST + API_REGISTRATION, 200);
        loginPage.inputLoginField("Eminem100")
                .inputPasswordField("111Gv5dv")
                .clickLoginButton();
        confirmationCodeModalPage
                .inputConfirmSMSField("1234")
                .clickOnConfirmButton();
//        assertEquals(после ввода правильного смс-кода должны быть перенапрвлены на страницу, где будет предложено
//        изменить логин/пароль при первом входе);
        new DBConnector().deleteUser("Eminem100");
    }

    @TestRails(id = "C5880167")
    @Step("First authorization with an unregistered login, negative test")
    @Test(description = "First authorization with an unregistered login, negative test")
    public void testFirstAuthorizationWithAnUnregisteredLogin() {

    }

    @TestRails(id = "C5880176")
    @Step("First authorization with an unregistered password, negative test")
    @Test(description = "First authorization with an unregistered password, negative test")
    public void testFirstAuthorizationWithAnUnregisteredPassword() {

    }

    @TestRails(id = "C5880179")
    @Step("Password recovery on first authorization, negative test")
    @Test(description = "Password recovery on first authorization, negative test")
    public void testPasswordRecoveryOnFirstAuthorization() {

    }

    @TestRails(id = "C5880189")
    @Step("First authorization after entering the wrong login or password three times , negative test")
    @Test(description = "First authorization after entering the wrong login or password three times , negative test")
    public void testFirstAuthorizationAfterEnteringTheWrongLoginOrPasswordThreeTimes() {

    }
}