package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.data.Alert;
//import by.andersen.amnbanking.data.User;
//import by.andersen.amnbanking.ui.pages.MainPage;
import by.andersen.amnbanking.utils.TestRails;
import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
//import static com.codeborne.selenide.Selenide.webdriver;
//import static com.codeborne.selenide.WebDriverConditions.url;

public class LoginTest extends BaseTest {
//    //TODO: change with actual data once it created
//    private final User user = new User.UserBuilder("11111", "11111", "11111").build();
//
//
//    @BeforeMethod
//    public void setUp() {
//        loginPage.open();
//    }
//
//    @TestRails(id = "123")
//    @Test
//    public void weCanSuccessLoginWithValidCredentials() {
//        loginPage.doLogin(user);
//
//        webdriver().shouldHave(url(MainPage.URL));
//    }

    @TestRails(id = "C5869665")
    @Test(description = "negative test")
    public void authWithInvalidLoginLessThan7SymbolsTest() {
        loginPage.inputLoginField("Gsvop4")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.LESS_8_SYMBOL_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869679")
    @Test(description = "negative test")
    public void authWithForbiddenSymbolLoginTest() {
        loginPage.inputLoginField("Fklid7*@")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869669")
    @Test(description = "negative test")
    public void authEmptyLoginAndValidPasswordTest() {
        loginPage.inputLoginField("")
                .inputPasswordField("84dc565d")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.EMPTY_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869673")
    @Test(description = "negative test")
    public void authEmptyPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.EMPTY_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869681")
    @Test(description = "negative test")
    public void authValidPasswordAndOnlyCapitalLetterInLoginTest() {
        loginPage.inputLoginField("RTNZKCVF")
                .inputPasswordField("84dc565d")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessageLogin(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "C5869674")
    @Test(description = "negative test")
    public void authAsteriskSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("*12354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authExclamationSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("!12354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authВashSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("-12354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authAtSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("@2354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }
    @TestRails(id = "")
    @Test(description = "negative test")
    public void authPlusSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("+2354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authMinusSymbolPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("-2354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authWhitespacePasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField(" 2354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }
    @TestRails(id = "")
    @Test(description = "negative test")
    public void authSlashPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("/2354785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authСommaPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("2354,785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authTagsPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("<2354785>")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authSQLInjectionPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("SELECT * FROM users")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authQuestionPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("2354785?")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authAmpersandPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4&785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authDotPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4.c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authСolonPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4:c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authSemicolonPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4;c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authPercentPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4c785%")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authDollarPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q4c785$")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authHashtagPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("#23Q4c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authNumberPasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("№23Q4c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }

    @TestRails(id = "")
    @Test(description = "negative test")
    public void authTildePasswordAndValidLoginTest() {
        loginPage.inputLoginField("Ksc8kvmx")
                .inputPasswordField("23Q~4c785")
                .clickLoginButton();
        Assert.assertEquals(loginPage.getAlertMessagePassword(), Alert.FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS.getValue());
    }
}
