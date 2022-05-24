package by.andersen.amnbanking.ui.pages;

import by.andersen.amnbanking.data.User;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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


    public LoginPage open() {
        Selenide.open(START_URL);
        return this;
    }

    public MainPage doLogin(User user) {
        this.logInInput.click();
        this.telInput.setValue(user.getLoginPhone());
        this.passwordInput.setValue(user.getPassword());
        this.logInInput.click();

        return new MainPage();
    }

    public LoginPage inputLoginField(String login) {
        this.logInInput.sendKeys(login);

        return this;
    }

    public LoginPage clickLoginField() {
        this.logInInput.click();

        return this;
    }

    public LoginPage inputPasswordField(String password) {
        this.passwordInput.sendKeys(password);

        return this;
    }

    public LoginPage clickPasswordField() {
        this.passwordInput.click();

        return this;
    }

    public String getAlertMessageLogin() {
        return this.loginAlert.getText();
    }

    public String getAlertMessagePassword() {
        return this.passwordAlert.getText();
    }

    public LoginPage clickLoginButton() {
        this.loginBtn.click();

        return this;
    }

    public boolean clickShowPasswordCheckbox() {
        this.checkBoxShowPassword.click();
        return this.checkBoxShowPassword.isEnabled();
    }

    public boolean clickHidePasswordCheckbox() {
        this.checkBoxShowPassword.click();
        this.checkBoxShowPassword.click();
        return this.checkBoxShowPassword.isDisplayed();
    }
}
