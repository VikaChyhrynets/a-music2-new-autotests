package by.andersen.amnbanking.tests.ui_tests.page;

import by.andersen.amnbanking.data.User;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.HashMap;
import java.util.Map;

import static by.andersen.amnbanking.data.UsersData.USER_ONE;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.refresh;
import static java.time.Duration.ofSeconds;

public class LoginPage extends BasePage {
    private static final By LOG_IN_INPUT = By.id(":r1:");
    private static final By TEL_INPUT = By.id("#phone");
    private static final By PASSWORD_INPUT = By.id(":r3:");
    private static final By INVALID_PASSWORD = By.xpath("//div[contains(@class, 'ggtXb')]");
    private static final By LOGIN_ALERT = By.xpath("//div[contains(@class, 'formInputLogin')]//*[contains(@class, 'formError')]");
    private static final By PASSWORD_ALERT = By.xpath("//*[contains(@class, 'formInputPassword')]//div[contains(@class, 'formError')]");
    private static final By LOGIN_BUTTON = By.xpath("//*[contains(@class, 'formButtonLogin')]//button[contains(@type, 'submit')]");
    private static final By CHECK_BOX_SHOW_PASSWORD = By.xpath("//div[contains(@class, 'showPassword')]/*");
    private static final By LOGIN_ERROR_MESSAGE = By.xpath("//div[contains(@class,'Login_formError')]");
    private static final By linkForgotPassword = By.xpath("//button[contains(@class,'X2kY6')]");
    private static final By contactsButton = By.xpath("//span[text()= 'Contacts']");
    private static final By LOGIN_WINDOW = By.xpath("//*[contains(@class, 'Login_form__nVOQc MuiBox-root css-0')]");

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

    @Step("Entering wrong password 3 times")
    public String alertMessage() {
       return $(INVALID_PASSWORD).getText();
    }

    @Step("User login and password fields should be empty")
    public boolean emptyInputLoginAndPasswordFields(String key) {
        Map<String, By> emptyFields = new HashMap<>();
        emptyFields.put("login", LOG_IN_INPUT);
        emptyFields.put("password", PASSWORD_INPUT);
        return $(emptyFields.get(key)).shouldBe(Condition.empty, ofSeconds(7)).getValue().isEmpty();
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

    @Step("Click the Contacts button")
    public LoginPage clickContactsButton() {
        $(contactsButton).click();
        return this;
    }

    @Step("Does user go back to Login page after clicking Back button")
    public boolean returnToLoginPage() {
        $(LOGIN_WINDOW).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
        return true;
    }

    @Step("Clear Login field")
    public LoginPage clearLoginField() {
        for (int i=0; i < $(LOG_IN_INPUT).getValue().length(); i++) {
            $(LOG_IN_INPUT).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        }
        return new LoginPage();
    }

    @Step("Clear Login field")
    public LoginPage clearPasswordField() {
        for (int i=0; i < $(PASSWORD_INPUT).getValue().length(); i++) {
            $(PASSWORD_INPUT).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        }
        return new LoginPage();
    }

    @Step("Persistent login")
    public LoginPage logInSystem() {
        refresh();
        inputLoginField(LOGIN_WITH_PASSPORT_REG)
                .inputPasswordField(USER_WRONG_PASS)
                .clickLoginButton();
        return new LoginPage();
    }

    @Step("Registration with wrong login")
    public LoginPage wrongLoginReg() {
        inputLoginField(LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS.getWrongData());
        inputPasswordField(PASSWORD_WITH_PASSPORT_REG);
        clickLoginButton();
        return new LoginPage();
    }

    @Step("Registration with wrong password")
    public LoginPage wrongPasswordReg() {
        clearLoginField();
        clearPasswordField();
        inputLoginField(LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS.getWrongData() + "AbC5");
        inputPasswordField("AbC5");
        clickLoginButton();
        return new LoginPage();
    }

}

