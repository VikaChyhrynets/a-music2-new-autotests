package by.andersen.amnbanking.ui.pages;

import by.andersen.amnbanking.data.User;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage {
    public SelenideElement logInInput = $(By.id(":r1:"));
    public SelenideElement telInput = $("#phone");
    public SelenideElement passwordInput = $(By.id(":r3:"));
    public SelenideElement loginAlert = $x("//div[contains(@class, 'formInputLogin')]//*[contains(@class, 'formError')]");
    public SelenideElement passwordAlert = $x("//*[contains(@class, 'formInputPassword')]//div[contains(@class, 'formError')]");
    public SelenideElement loginBtn = $x("//*[contains(@class, 'formButtonLogin')]//button[contains(@type, 'submit')]");
    public SelenideElement checkBoxShowPassword = $x("//div[contains(@class, 'showPassword')]/*");
    private static final By LOGIN_ERROR_MESSAGE = By.xpath("//div[contains(@class,'Login_formError')]");

    public void open() {
        Selenide.open(START_URL);
    }

    public MainPage doLogin(User user) {
        this.logInInput.click();
        this.telInput.setValue(user.getLoginPhone());
        this.passwordInput.setValue(user.getPassword());
        this.logInInput.click();

        return new MainPage();
    }

    @Step("Entering a user login into Login input field")
    public LoginPage inputLoginField(String login) {
        this.logInInput.sendKeys(login);

        return this;
    }

    @Step("Clicking on Login input field")
    public LoginPage clickLoginField() {
        this.logInInput.click();

        return this;
    }

    @Step("Entering a user password into Password input field")
    public LoginPage inputPasswordField(String password) {
        this.passwordInput.sendKeys(password);

        return this;
    }

    @Step("Clicking on \"Password\" input field")
    public LoginPage clickPasswordField() {
        this.passwordInput.click();

        return this;
    }

    @Step("Checking alert message for \"Login\" input field")
    public String getAlertMessageLogin() {
        return this.loginAlert.getText();
    }

    @Step("Checking alert message for \"Password\" input field")
    public String getAlertMessagePassword() {
        return this.passwordAlert.getText();
    }

    @Step("Clicking on button \"Login\"")
    public ConfirmationCodeModalPage clickLoginButton() {
        this.loginBtn.click();

        return new ConfirmationCodeModalPage();
    }

    @Step("Clicking on button \"Show Password\"")
    public String clickShowPasswordCheckbox(String password, String type) {
        this.passwordInput.sendKeys(password);
        this.checkBoxShowPassword.click();
        return this.passwordInput.getAttribute(type);
    }

    @Step("Clicking on button \"Hide Password\"")
    public String clickHidePasswordCheckbox(String password, String type) {
        this.passwordInput.sendKeys(password);
        this.checkBoxShowPassword.click();
        this.checkBoxShowPassword.click();
        return this.passwordInput.getAttribute(type);
    }

    public String getTextFromLoginErrorMessage(){
        return $(LOGIN_ERROR_MESSAGE).getText();
    }
}