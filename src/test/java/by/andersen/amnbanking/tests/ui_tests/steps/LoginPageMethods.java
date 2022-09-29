package by.andersen.amnbanking.tests.ui_tests.steps;

import by.andersen.amnbanking.data.User;
import by.andersen.amnbanking.data.UsersData;
import by.andersen.amnbanking.tests.ui_tests.page.ConfirmationCodeModalPage;
import by.andersen.amnbanking.tests.ui_tests.page.LoginPage;
import by.andersen.amnbanking.tests.ui_tests.page.MainPage;
import by.andersen.amnbanking.tests.ui_tests.page.PasswordRecoveryModalPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import java.util.HashMap;
import java.util.Map;
import static java.time.Duration.ofSeconds;

public class LoginPageMethods extends BaseSteps {

    public MainPage doLogin(User user) {
        loginPage.getLoginField().click();
        loginPage.getPasswordField().setValue(user.getPassword());
        loginPage.getLoginButton().click();
        return new MainPage();
    }

    public LoginPage open() {
        Selenide.open(START_URL);
        return new LoginPage();
    }

    @Step("Entering a user login into Login input field")
    public LoginPageMethods inputLoginField(String login) {
        loginPage.getLoginField().sendKeys(login);
        return new LoginPageMethods();
    }

    @Step("Entering wrong password 3 times")
    public String alertMessage() {
        return loginPage.getInvalidPassword().getText();
    }

    @Step("User login and password fields should be empty")
    public boolean emptyInputLoginAndPasswordFields(String key) {
        Map<String, SelenideElement> emptyFields = new HashMap<>();
        emptyFields.put("login", loginPage.getLoginField());
        emptyFields.put("password", loginPage.getPasswordField());
        return (emptyFields.get(key)).shouldBe(Condition.empty, ofSeconds(7)).getValue().isEmpty();
    }

    @Step("Clicking on Login input field")
    public LoginPageMethods clickLoginField() {
        loginPage.getLoginField().click();
        return new LoginPageMethods();
    }

    @Step("Entering a user password into Password input field")
    public LoginPageMethods inputPasswordField(String password) {
        loginPage.getPasswordField().sendKeys(password);
        return new LoginPageMethods();
    }

    @Step("Clicking on 'Password' input field")
    public LoginPageMethods clickPasswordField() {
        loginPage.getPasswordField().click();
        return new LoginPageMethods();
    }

    @Step("Checking alert message for 'Login' input field")
    public String getAlertMessageLogin() {
        return loginPage.getLoginAlert().getText();
    }

    @Step("Checking alert message for 'Password' input field")
    public String getAlertMessagePassword() {
        return loginPage.getPasswordAlert().getText();
    }

    @Step("Clicking on button 'Login'")
    public ConfirmationCodeModalPage clickLoginButton() {
        loginPage.getLoginButton().click();
        return new ConfirmationCodeModalPage();
    }

    @Step("Clicking on button \"Show Password\"")
    public String clickShowPasswordCheckbox(String password, String type) {
        loginPage.getPasswordField().sendKeys(password);
        loginPage.getCheckBoxShowPassword().click();
        return loginPage.getPasswordField().getAttribute(type);
    }

    @Step("Clicking on button \"Hide Password\"")
    public String clickHidePasswordCheckbox(String password, String type) {
        loginPage.getPasswordField().sendKeys(password);
        loginPage.getCheckBoxShowPassword().click();
        loginPage.getCheckBoxShowPassword().click();
        return loginPage.getPasswordField().getAttribute(type);
    }

    public String getTextFromLoginErrorMessage() {
        return loginPage.getLoginErrorMessage().getText();
    }

    @Step("Enter login and password and press \"Login\"")
    public ConfirmationCodeModalPage loginUserWithCreds(String login, String password) {
        loginPage.getLoginField().sendKeys(login);
        loginPage.getPasswordField().sendKeys(password);
        loginPage.getLoginButton().click();
        return new ConfirmationCodeModalPage();
    }

    @Step("Click on the link forgot password")
    public PasswordRecoveryModalPage clickLinkForgotPassword() {
        loginPage.getLinkForgotPassword().click();
        return new PasswordRecoveryModalPage();
    }

    @Step("Click the Contacts button")
    public LoginPageMethods clickContactsButton() {
        loginPage.getContactsButton().click();
        return new LoginPageMethods();
    }

    @Step("Does user go back to Login page after clicking Back button")
    public boolean returnToLoginPage() {
        loginPage.getLoginWindow().shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
        return true;
    }

    @Step("Clear Login field")
    public LoginPage clearLoginAndPasswordFields() {
        for (int i=0; i < loginPage.getLoginField().getValue().length(); i++) {
            loginPage.getLoginField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        }
        for (int i=0; i < loginPage.getPasswordField().getValue().length(); i++) {
            loginPage.getPasswordField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        }
        return new LoginPage();
    }

    @Step("Persistent login")
    public LoginPageMethods logInSystem() {
        clearLoginAndPasswordFields();
        inputLoginField(UsersData.USER_EMINEM79.getUser().getLogin())
                .inputPasswordField(UsersData.USER_EMINEM79.getUser().getPassword())
                .clickLoginButton();
        return new LoginPageMethods();
    }

    @Step("Registration with wrong login")
    public LoginPageMethods wrongLoginReg() {
        inputLoginField(UsersData.LESS_THAN_MIN_CHARS.getUser().getLogin());
        inputPasswordField(UsersData.LESS_THAN_MIN_CHARS.getUser().getPassword() + "aDf5");
        clickLoginButton();
        return new LoginPageMethods();
    }

    @Step("Registration with wrong password")
    public LoginPageMethods wrongPasswordReg() {
        clearLoginAndPasswordFields();
        inputLoginField(UsersData.LESS_THAN_MIN_CHARS.getUser().getLogin() + "AbC5");
        inputPasswordField(UsersData.LESS_THAN_MIN_CHARS.getUser().getPassword());
        clickLoginButton();
        return new LoginPageMethods();
    }

    @Step("Does user go back to Login page after clicking Back button")
    public LoginPageMethods enterLoginAndPasswordToGetSMSConfirmationCode() {
        inputLoginField(UsersData.USER_ONE.getUser().getLogin())
                .inputPasswordField(UsersData.USER_ONE.getUser().getPassword())
                .clickLoginButton();
        return new LoginPageMethods();
    }

}
