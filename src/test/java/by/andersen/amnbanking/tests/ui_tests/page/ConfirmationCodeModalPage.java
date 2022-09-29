package by.andersen.amnbanking.tests.ui_tests.page;

import by.andersen.amnbanking.tests.ui_tests.steps.LoginPageMethods;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static by.andersen.amnbanking.data.SmsVerificationData.SMS_INVALID;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class ConfirmationCodeModalPage extends BasePage {
    private static final By confirmationCodeModal = (By.id(":r5:"));
    private static final By smsCodeField = (By.id(":r7:"));
    private static final By confirmButton = (By.xpath("//*[text()= 'Confirm']"));
    private static final By errorMessageWrongSmsCode = (By.xpath("//div[contains(@class,\"ModalForm_formInputSMS\") and not (.//*[contains(@class,\"MuiFormControl-root\")])]/div"));
    private static final By wrongEnteredMessage = (By.xpath("//div[@class= 'ModalForm_formInputSMS__3vSof MuiBox-root css-0'][last()]"));
    private static final By clickSendAgainButtonModalError = (By.xpath("//*[text()= 'Send again']"));
    private static final By loginSuccess = By.id("ModalSuccessfully");
    private static final By FirstAuthorizationWithValidData = (By.xpath("//span[@class= 'Login_text__INpYu']"));

    @Step("Field for entering an sms code confirmation")
    public ConfirmationCodeModalPage enterSmsCodeInFieldForCode(String smsCode) {
        $(smsCodeField).shouldBe(Condition.visible, ofSeconds(7)).sendKeys(smsCode);

        return this;
    }

    @Step("Clear field for entering an sms code confirmation")
    public ConfirmationCodeModalPage clearSmsCodeInFieldForCode() {
        while(!$(smsCodeField).shouldBe(Condition.visible, ofSeconds(7)).getAttribute("value").equals("")) {
            $(smsCodeField).shouldBe(Condition.visible, ofSeconds(7)).sendKeys(Keys.BACK_SPACE);
        }
        return this;
    }

    @Step("Closing SMS confirmation window by clicking to empty area")
    public LoginPageMethods closeSmsWindowByEmptyClick() {
        $(confirmationCodeModal).click(ClickOptions.usingDefaultMethod().offset(200, 200));

        return new LoginPageMethods();
    }

    @Step("Entering SMS code")
    public ConfirmationCodeModalPage inputSmsCode(String smsCode) {
        $(smsCodeField).sendKeys(smsCode);

        return this;
    }

    @Step("Checking validation field")
    public String checkSmsInputValidationText() {return $(errorMessageWrongSmsCode).getText();}

    @Step("Confirm button on the modal page for entering sms code confirmation")
    public ConfirmationCodeModalPage clickConfirmButton() {
        $(confirmButton).click();

        return this;
    }

    @Step("Get the error messages for sms code confirmation ")
    public String getErrorMessageForWrongCodeConfirmation() {

        return $(errorMessageWrongSmsCode).getText();
    }

    @Step("Get error message when entering wrong sms code 3 times")
    public String getErrorMessageWhenEnteringWrongSmsCode() {
        return $(wrongEnteredMessage).shouldBe(Condition.visible, ofSeconds(10)).getText();
    }

    @Step("Click send again button on the modal after entering wrong for sms code confirmation ")
    public ConfirmationCodeModalPage clickSendAgainModalWrongMessageSmsCode() {
        $(clickSendAgainButtonModalError).shouldBe(Condition.visible, ofSeconds(7)).click();
        return this;
    }

    @Step("Refresh page")
    public LoginPage refreshPage() {
        refresh();
        return new LoginPage();
    }

    @Step("Is Confirmation Code Window Open?")
    public boolean confirmationCodeWindowIsOpen() {
        try {
            return $(confirmationCodeModal).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
        }
        catch (ElementNotFound e) {
            return false;
        }
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

    @Step("First successful authorization with valid data")
    public String resetPasswordWindowCheck() {
        return $(FirstAuthorizationWithValidData).getText();
    }

    @Step("Enter invalid confirmation code 3 times")
    public ConfirmationCodeModalPage enterInvalidConfirmationCode() {
        enterSmsCodeInFieldForCode(SMS_INVALID.getSms());
        clickConfirmButton();
        return new ConfirmationCodeModalPage();
    }

    @Step("Clear sms confirmation field")
    public ConfirmationCodeModalPage clearSMSConfirmationField() {
        for (int i=0; i < $(smsCodeField).getValue().length(); i++) {
            $(smsCodeField).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        }
        return new ConfirmationCodeModalPage();
    }


}
