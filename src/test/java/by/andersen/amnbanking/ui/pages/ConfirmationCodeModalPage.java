package by.andersen.amnbanking.ui.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class ConfirmationCodeModalPage extends BasePage {
    private static final By CONFIRM_WINDOW = By.id(":r5:");
    private static final By CONFIRM_LOGIN_FIELD = By.id(":r7:");
    private static final By CLICK_CONFIRM_BUTTON = By.xpath("//div[contains(@class,'SubmitButton')]");
    private static final By LOGIN_SUCCESS = By.id("ModalSuccessfully");

    public boolean confirmationCodeWindowIsOpen() {
        return $(CONFIRM_WINDOW).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
    }

    public ConfirmationCodeModalPage inputLoginField(String smsCodeVerification) {
        $(CONFIRM_LOGIN_FIELD).sendKeys(smsCodeVerification);
        return this;
    }

    public void clickOnConfirmButton() {
        $(CLICK_CONFIRM_BUTTON).click();
    }

    public boolean isLoginSuccess() {
        return $(LOGIN_SUCCESS).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
    }
}