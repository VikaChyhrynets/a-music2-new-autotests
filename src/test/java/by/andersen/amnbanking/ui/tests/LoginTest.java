package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.data.Alert;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        loginPage.open();
    }

    @TestRails(id = "C5869665")
    @Step("Enter invalid login shorter than 7 symbols")
    @Test(description = "negative test")
    public void authWithInvalidLoginLessThan7SymbolsTest() {
        loginPage.inputLoginField("Gsvop4")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869679")
    @Step("Enter invalid symbols into login field")
    @Test(description = "negative test")
    public void authWithForbiddenSymbolLoginTest() {
        loginPage.inputLoginField("Fklid7*@")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900035")
    @Step("Enter invalid login longer than 20 symbols")
    @Test(description = "negative test")
    public void authWithTwentyOneSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbjgldrkmk8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS.getValue());
    }

    @TestRails(id = "C5900176")
    @Step("Enter invalid login with ~ symbol")
    @Test(description = "negative test")
    public void authTildaSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnb~mk8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900198")
    @Step("Enter invalid login with @ symbol")
    @Test(description = "negative test")
    public void authAtSymbolLoginTest() {
        loginPage.inputLoginField("@5Dvkfefnbmk8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900200")
    @Step("Enter invalid login with - symbol")
    @Test(description = "negative test")
    public void authMinusSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkf-efnbmk8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900201")
    @Step("Enter invalid login with / symbol")
    @Test(description = "negative test")
    public void authSlashSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbmk8ftt/")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900204")
    @Step("Enter invalid login with + symbol")
    @Test(description = "negative test")
    public void authPlusSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbmk8+ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900206")
    @Step("Enter invalid login with ^ symbol")
    @Test(description = "negative test")
    public void authCircumflexSymbolLoginTest() {
        loginPage.inputLoginField("5Dv^kfefnbmk8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900210")
    @Step("Enter invalid login with « symbol")
    @Test(description = "negative test")
    public void authQuotationSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnb«mkftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900241")
    @Step("Enter invalid login with * symbol")
    @Test(description = "negative test")
    public void authAsteriskSymbolLoginTest() {
        loginPage.inputLoginField("5*Dvkfefnbmk8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900242")
    @Step("Enter invalid login with . symbol")
    @Test(description = "negative test")
    public void authDotSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm.k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900244")
    @Step("Enter invalid login with > symbol")
    @Test(description = "negative test")
    public void authTagSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm>k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900246")
    @Step("Enter SQL query to login field")
    @Test(description = "negative test")
    public void authSQLSymbolLoginTest() {
        loginPage.inputLoginField("SELECT*FROM users")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900247")
    @Step("Enter invalid login with ? symbol")
    @Test(description = "negative test")
    public void authQuestionSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm?k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900249")
    @Step("Enter invalid login with & symbol")
    @Test(description = "negative test")
    public void authAmpersandSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm&k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900250")
    @Step("Enter invalid login with # symbol")
    @Test(description = "negative test")
    public void authHashtagSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm#k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900283")
    @Step("Enter invalid login with ] symbol")
    @Test(description = "negative test")
    public void authNumberSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm]k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900285")
    @Step("Enter invalid login with space")
    @Test(description = "negative test")
    public void authWhitespaceLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900295")
    @Step("Enter invalid login with ❞ symbol")
    @Test(description = "negative test")
    public void authDoubleUpperCommaLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm❞k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900468")
    @Step("Enter invalid login with $ symbol")
    @Test(description = "negative test")
    public void authDollarSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm$k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900469")
    @Step("Enter invalid login with _ symbol")
    @Test(description = "negative test")
    public void authUnderliningSymbolLoginTest() {
        loginPage.inputLoginField("_5Dvkfefnbmk8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900470")
    @Step("Enter invalid login with ' symbol")
    @Test(description = "negative test")
    public void authApostropheSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm'k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900471")
    @Step("Enter invalid login with = symbol")
    @Test(description = "negative test")
    public void authEquallySymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm=k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900473")
    @Step("Enter invalid login with : symbol")
    @Test(description = "negative test")
    public void authColonSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefn:k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900751")
    @Step("Enter invalid login with ; symbol")
    @Test(description = "negative test")
    public void authSemicolonSymbolLoginTest() {
        loginPage.inputLoginField("5Dvkfefn;k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900752")
    @Step("Enter invalid login with ! symbol")
    @Test(description = "negative test")
    public void authExclamationMarkLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm!k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5900753")
    @Step("Enter invalid login with ← symbol")
    @Test(description = "negative test")
    public void authWithArrowLoginTest() {
        loginPage.inputLoginField("5Dvkfefnbm←k8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Step("Enter login with cyrillic letters")
    @Test(description = "negative test")
    public void authRussianSymbolLoginTest() {
        loginPage.inputLoginField("А5Dvkfefbmk8ftt")
                .clickPasswordField();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Step("Enter password longer than 20 symbols")
    @Test(description = "negative test")
    public void authWithTwentyOneSymbolPasswordTest() {
        loginPage.inputPasswordField("584841Cd2154ddvnvddvb")
                .clickLoginField();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS.getValue());
    }

    @TestRails(id = "")
    @Step("Enter invalid password with » symbol")
    @Test(description = "negative test")
    public void authWithQuotationSymbolPasswordTest() {
        loginPage.inputPasswordField("584841Cd»nvddvb")
                .clickLoginField();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter invalid password with ❝ symbol")
    @Test(description = "negative test")
    public void authWithDoubleUpperCommaSymbolPasswordTest() {
        loginPage.inputPasswordField("584841Cd❝nvddvb")
                .clickLoginField();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "C5869669")
    @Step("Enter valid password with blank login field")
    @Test(description = "negative test")
    public void authEmptyLoginAndValidPasswordTest() {
        loginPage.inputLoginField("")
                .inputPasswordField("84dcB565d")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.EMPTY_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869673")
    @Step("Enter valid login with blank password field")
    @Test(description = "negative test")
    public void authEmptyPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.EMPTY_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869681")
    @Step("Enter all capital letters login and valid password")
    @Test(description = "negative test")
    public void authValidPasswordAndOnlyCapitalLetterInLoginTest() {
        loginPage.inputLoginField("RTNZKCVF")
                .inputPasswordField("84dDc565d")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869674")
    @Step("Enter valid login and invalid password with * symbol")
    @Test(description = "negative test")
    public void authAsteriskSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("*12Ty354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ! symbol")
    @Test(description = "negative test")
    public void authExclamationSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("!1235Xc4785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with - symbol")
    @Test(description = "negative test")
    public void authBashSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("-1Vc2354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with @ symbol")
    @Test(description = "negative test")
    public void authAtSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("@2354Lk785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with + symbol")
    @Test(description = "negative test")
    public void authPlusSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("+23547Mn85")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and password with cyrillic letters")
    @Test(description = "negative test")
    public void authRussianSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Кsc8kvmx")
                .inputPasswordField("З23Rb54785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with - symbol")
    @Test(description = "negative test")
    public void authMinusSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("-235Xn4785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with space")
    @Test(description = "negative test")
    public void authWhitespacePasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField(" 23Dv54785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with / symbol")
    @Test(description = "negative test")
    public void authSlashPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("/23Dv54785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with , symbol")
    @Test(description = "negative test")
    public void authCommaPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("2354,78Dc5")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with < symbol")
    @Test(description = "negative test")
    public void authTagsPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("<2354785Fv")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ∑ symbol")
    @Test(description = "negative test")
    public void authMathematicsSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("X∑235n4785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter SQL query in password input field")
    @Test(description = "negative test")
    public void authSQLInjectionPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("SELECT*FROM users1")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ? symbol")
    @Test(description = "negative test")
    public void authQuestionPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("235478Sa5?")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with & symbol")
    @Test(description = "negative test")
    public void authAmpersandPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4&7z85")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with . symbol")
    @Test(description = "negative test")
    public void authDotPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4.c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with : symbol")
    @Test(description = "negative test")
    public void authWithColonPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4:c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ; symbol")
    @Test(description = "negative test")
    public void authSemicolonPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4;c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with % symbol")
    @Test(description = "negative test")
    public void authPercentPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4c785%")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with $ symbol")
    @Test(description = "negative test")
    public void authDollarPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4c785$")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with # symbol")
    @Test(description = "negative test")
    public void authHashtagPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("#23Q4c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with № symbol")
    @Test(description = "negative test")
    public void authNumberPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("№23Q4c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ~ symbol")
    @Test(description = "negative test")
    public void authTildePasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q~4c785d")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ➝ symbol")
    @Test(description = "negative test")
    public void authWithArrowPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q➝4c785d")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ¼ symbol")
    @Test(description = "negative test")
    public void authWithFractionPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q¼4c785d")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ^ symbol")
    @Test(description = "negative test")
    public void authWithCircumflexPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q^4c785d")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Enter valid login and invalid password with ' symbol")
    @Test(description = "negative test")
    public void authWithApostrophePasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("I'l4584c785d")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FIELD_CONTAIN_LETTERS_NUMBER.getValue());
    }

    @TestRails(id = "")
    @Step("Show password button check")
    @Test(description = "negative test")
    public void showPasswordIconTest() {
        Assert.assertEquals(loginPage.clickShowPasswordCheckbox("Drn1f7sC", "type"), "text");
    }

    @TestRails(id = "")
    @Step("Hide password button check")
    @Test(description = "negative test")
    public void hidePasswordIconTest() {
        Assert.assertEquals(loginPage.clickHidePasswordCheckbox("Drn1f7sC", "type"),"password");
    }
}
