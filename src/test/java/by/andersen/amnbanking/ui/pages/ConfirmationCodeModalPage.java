package by.andersen.amnbanking.ui.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class ConfirmationCodeModalPage extends BasePage {
    private static final By confirmationCodeModal = (By.id(":r5:"));
    private static final By smsCodeField = (By.id(":r7:"));
    private static final By confirmButton = (By.xpath("//*[text()= 'Confirm']"));
    private static final By modalCongratulations = (By.id("modal-modal-description"));
    private static final By errorMessageWrongSmsCode = (By.xpath("//*[contains(@class,'formInputSMSMessage')]"));
    private static final By errorModalSmsCode = (By.id("modal-modal-title"));
    private static final By clickProceedButtonModalError = (By.xpath("//button[contains(text(), 'Proceed')]"));


    @Step("Checking that modal for entering sms code is open")
    public boolean confirmationCodeWindowIsOpen() {
        $(confirmationCodeModal).shouldBe(Condition.visible, ofSeconds(5));

        return true;
    }

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

    @Step("Checking that login in the system was successfully" )
    public String successLoginInTheSystemAfterEnterSmsCode() {

        return $(modalCongratulations).shouldBe(Condition.visible, ofSeconds(7)).getText();
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
}
