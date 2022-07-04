package by.andersen.amnbanking.ui.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.refresh;
import static java.time.Duration.ofSeconds;
import static com.codeborne.selenide.Selenide.$;

public class ConfirmationCodeModalPage extends BasePage {
    private static final By confirmationCodeModal = (By.id(":r5:"));
    private static final By smsCodeField = (By.id(":r7:"));
    private static final By confirmButton = (By.xpath("//*[text()= 'Confirm']"));
    private static final By errorMessageWrongSmsCode = (By.xpath("//*[contains(@class,'formInputSMSMessage')]"));
    private static final By errorModalSmsCode = (By.id("modal-modal-title"));
    private static final By clickProceedButtonModalError = (By.xpath("//button[contains(text(), 'Proceed')]"));
    private static final By loginSuccess = By.id("ModalSuccessfully");

    @Step("Field for entering an sms code confirmation")
    public ConfirmationCodeModalPage enterSmsCodeInFieldForCode(String smsCode) {
       $(smsCodeField).shouldBe(Condition.visible, ofSeconds(7)).sendKeys(smsCode);

        return this;
    }

    @Step("Confirm button on the modal page for entering sms code confirmation")
    public ConfirmationCodeModalPage clickConfirmButton() {
        $(confirmButton).click();

        return this;
    }

    @Step("Get the error messages for sms code confirmation ")
    public String getErrorMessageForWrongCodeConfirmation() {

        return $(errorMessageWrongSmsCode).getText();
    }

    @Step("Get the error messages from modal window after entering wrong sms code confirmation ")
    public String getErrorMessageFromModalWrongSmsCode() {

        return $(errorModalSmsCode).shouldBe(Condition.visible, ofSeconds(7)).getText();
    }

    @Step("Click proceed button on the modal after entering wrong for sms code confirmation ")
    public ConfirmationCodeModalPage clickProceedModalWrongMessageSmsCode() {
        $(clickProceedButtonModalError).shouldBe(Condition.visible, ofSeconds(7)).click();
        return this;
    }

    @Step("Refresh page")
    public LoginPage refreshPage() {
        refresh();
        return new LoginPage();
    }

    @Step("Is Confirmation Code Window Open?")
    public boolean confirmationCodeWindowIsOpen() {

        return $(confirmationCodeModal).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();

    }

    @Step("Entering a confirmation SMS into SMS-code input field")
    public ConfirmationCodeModalPage inputConfirmSMSField(String smsCodeVerification) {
        $(smsCodeField).sendKeys(smsCodeVerification);

        return this;
    }

    @Step("Click on confirm SMS-code button")
    public void clickOnConfirmButton() {
        $(confirmButton).click();
    }

    @Step("Is Login Success?")
    public boolean isLoginSuccess() {

        return $(loginSuccess).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
    }
}