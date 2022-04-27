package by.andersen.amnbanking.ui.pages;

import by.andersen.amnbanking.data.User;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {
    public SelenideElement logInBtn = $(By.xpath("//button[contains(@class, 'form__login-btn')]"));
    public SelenideElement telInput = $("#phone");
    public SelenideElement passwordInput = $("#password");

    public LoginPage open() {
        Selenide.open(START_URL + "/login");
        return this;
    }

    public MainPage doLogin(User user) {
        this.logInBtn.click();
        this.telInput.setValue(user.getLoginPhone());
        this.passwordInput.setValue(user.getPassword());
        this.logInBtn.click();

        return new MainPage();
    }
}
