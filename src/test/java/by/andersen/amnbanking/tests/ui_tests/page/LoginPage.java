package by.andersen.amnbanking.tests.ui_tests.page;

import by.andersen.amnbanking.data.User;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {
    private static final By LOG_IN_INPUT = By.id(":r1:");
    private static final By TEL_INPUT = By.id("#phone");
    private static final By PASSWORD_INPUT = By.id(":r3:");
    private static final By LOGIN_ALERT = By.xpath("//div[contains(@class, 'formInputLogin')]//*[contains(@class, 'formError')]");
    private static final By PASSWORD_ALERT = By.xpath("//*[contains(@class, 'formInputPassword')]//div[contains(@class, 'formError')]");
    private static final By LOGIN_BUTTON = By.xpath("//*[contains(@class, 'formButtonLogin')]//button[contains(@type, 'submit')]");
    private static final By CHECK_BOX_SHOW_PASSWORD = By.xpath("//div[contains(@class, 'showPassword')]/*");
    private static final By LOGIN_ERROR_MESSAGE = By.xpath("//div[contains(@class,'Login_formError')]");
    private static final By linkForgotPassword = By.xpath("//button[contains(@class,'X2kY6')]");

    public MainPage doLogin(User user) {
        $(LOG_IN_INPUT).click();
        $(TEL_INPUT).setValue(user.getPhone());
        $(PASSWORD_INPUT).setValue(user.getPassword());
        $(LOG_IN_INPUT).click();

        return new MainPage();
    }

    public LoginPage open() {
        Selenide.open(START_URL);
        return this;
    }

    @Step("Entering a user login into Login input field")
    public LoginPage inputLoginField(String login) {
        $(LOG_IN_INPUT).sendKeys(login);

        return this;
    }

    @Step("Clicking on Login input field")
    public LoginPage clickLoginField() {
        $(LOG_IN_INPUT).click();

        return this;
    }

    @Step("Entering a user password into Password input field")
    public LoginPage inputPasswordField(String password) {
        $(PASSWORD_INPUT).sendKeys(password);

        return this;
    }

    @Step("Clicking on \"Password\" input field")
    public LoginPage clickPasswordField() {
        $(PASSWORD_INPUT).click();

        return this;
    }

    @Step("Checking alert message for \"Login\" input field")
    public String getAlertMessageLogin() {
        return $(LOGIN_ALERT).getText();
    }

    @Step("Checking alert message for \"Password\" input field")
    public String getAlertMessagePassword() {
        return $(PASSWORD_ALERT).getText();
    }

    @Step("Clicking on button \"Login\"")
    public ConfirmationCodeModalPage clickLoginButton() {
        $(LOGIN_BUTTON).click();

        return new ConfirmationCodeModalPage();
    }

    @Step("Clicking on button \"Show Password\"")
    public String clickShowPasswordCheckbox(String password, String type) {
        $(PASSWORD_INPUT).sendKeys(password);
        $(CHECK_BOX_SHOW_PASSWORD).click();
        return $(PASSWORD_INPUT).getAttribute(type);
    }

    @Step("Clicking on button \"Hide Password\"")
    public String clickHidePasswordCheckbox(String password, String type) {
        $(PASSWORD_INPUT).sendKeys(password);
        $(CHECK_BOX_SHOW_PASSWORD).click();
        $(CHECK_BOX_SHOW_PASSWORD).click();
        return $(PASSWORD_INPUT).getAttribute(type);
    }

    public String getTextFromLoginErrorMessage() {
        return $(LOGIN_ERROR_MESSAGE).getText();
    }

    @Step("Enter login and password and press \"Login\"")
    public ConfirmationCodeModalPage loginUserWithCreds(String login, String password) {
        $(LOG_IN_INPUT).sendKeys(login);
        $(PASSWORD_INPUT).sendKeys(password);
        $(LOGIN_BUTTON).click();

        return new ConfirmationCodeModalPage();
    }

    @Step("Click on the link forgot password")
    public PasswordRecoveryModalPage clickLinkForgotPassword() {
        $(linkForgotPassword).click();
        return new PasswordRecoveryModalPage();
    }
}

