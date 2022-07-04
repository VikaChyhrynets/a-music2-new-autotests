package by.andersen.amnbanking.ui.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class ConfirmationCodeModalPage extends BasePage {
    private static final By CONFIRM_WINDOW = By.id(":r5:");
    private static final By CONFIRM_SMS_FIELD = By.id(":r7:");
    private static final By CLICK_CONFIRM_BUTTON = By.xpath("//div[contains(@class,'SubmitButton')]");
    private static final By LOGIN_SUCCESS = By.id("ModalSuccessfully");

    @Step("Is Confirmation Code Window Open?")
    public boolean confirmationCodeWindowIsOpen() {

        return $(CONFIRM_WINDOW).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
    }

    @Step("Entering a confirmation SMS into SMS-code input field")
    public ConfirmationCodeModalPage inputConfirmSMSField(String smsCodeVerification) {
        $(CONFIRM_SMS_FIELD).sendKeys(smsCodeVerification);

        return this;
    }

    @Step("Click on confirm SMS-code button")
    public void clickOnConfirmButton() {
        $(CLICK_CONFIRM_BUTTON).click();
    }

    @Step("Is Login Success?")
    public boolean isLoginSuccess() {

        return $(LOGIN_SUCCESS).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
    }
}