package by.andersen.amnbanking.tests.ui_tests.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class PasswordRecoveryModalPage {
    private static final By inputIdNumber = By.id(":r7:");
    private static final By continueButtonAfterEnterId = By.xpath("//*[contains(@class,'9FepN')]");
    private static final By inputSmsCode = By.id(":r9:");
    private static final By continueButtonAfterEnterSms = By.xpath("//*[contains(@class,'pwwK8')]");
    private static final By inputNewPassword = By.id(":rb:");
    private static final By inputConfirmPassword = By.id(":rd:");
    private static final By continueButtonAfterEnterNewPass = By.xpath("//*[contains(@class,'hAnNq')]");
    private static final By greyTextAboveSmsCodeField = By.xpath("//p[contains(@class,'Fmyzh')]");
    private static final By errorMessageInputIdNumber = By.xpath("//*[contains(@class,'RvDTg')]");
    private static final By titleTextWithPhoneNumber2Step = By.xpath("//p[contains(@class,'rg9OK')]");
    private static final By modalWindowPasswordRecovery = By.xpath("//*[contains(@class,'LvKN9')]");
    private static final By backButton = By.xpath("//*[text()= 'Back']");
    private static final By errorMessageSmsConfirmation = By.xpath("//*[contains(@class,'+xIHL')]");
    private static final By errorMessageNewPassword = By.xpath("//*[contains(@class,'0JwCH')]");
    private static final By errorMessageConfirmPassword = By.xpath("//div[contains(@class,'0JwCH')][2]");
    private static final By errorMessageConfirmPasswordNotMuch = By.xpath("//div[contains(@class,'0JwCH')]");
    private static final By eyeButtonNewPassword = By.xpath("//form[contains(@class,'07NPd')]//*[contains(@class,'g-r7n')]");
    private static final By eyeButtonConfirmPassword = By.xpath("//form/div[3]/div[2]");


    @Step("In the modal Password Recovery first step is enter user's ID number")
    public PasswordRecoveryModalPage enterIdNumber(String idNumber) {
        $(inputIdNumber).sendKeys(idNumber);
        return this;
    }

    @Step("The user enter wrong ID Number and get message")
    public String getErrorMessageAfterEnterWrongIdNum() {
        return $(errorMessageInputIdNumber).getText();
    }

    @Step("Click continue button on first step with ID number")
    public PasswordRecoveryModalPage clickContinueButton() {
        $(continueButtonAfterEnterId).click();
        return this;
    }

    @Step("Text above the sms code field is visible")
    public String getTextAboveSmsField() {
        return $(greyTextAboveSmsCodeField).getText();
    }

    @Step("Get text with user's phone number")
    public boolean getPhoneNumberFrom2StepAfterSendSms(String phoneNumber) {
        return $(titleTextWithPhoneNumber2Step).getText().contains(phoneNumber);
    }

    @Step("Get text with user's phone number")
    public String getText2StepAfterSendSms() {
        return $(titleTextWithPhoneNumber2Step).getText();
    }

    @Step("Click on back arrow")
    public LoginPage clickBackArrow() {
        $(backButton).click();

        return new LoginPage();
    }

    @Step("Close modal window use back button")
    public boolean modalWindowIsNotVisible() {
        return $(modalWindowPasswordRecovery).isDisplayed();
    }

    @Step("Enter an sms code to the field")
    public PasswordRecoveryModalPage enterSmsCodeConfirmation(String smsCode) {
        $(inputSmsCode).sendKeys(smsCode);
        return this;
    }

    @Step("Get error message after entered invalid sms-code confirmation")
    public String getErrorMessageCodeConfirmation() {
        return $(errorMessageSmsConfirmation).getText();
    }

    @Step("Click continue button after entered sms code")
    public PasswordRecoveryModalPage clickContinueButtonAfterEnteringSms() {
        $(continueButtonAfterEnterSms).click();
        return this;
    }

    @Step("Enter password in New password field")
    public PasswordRecoveryModalPage enterPasswordInNewPasswordField(String newPassword) {
        $(inputNewPassword).sendKeys(newPassword);

        return this;
    }

    @Step("Enter password in New password field on the third step for password recovery and get attribute status")
    public String getAttributeStatusAfterEnterPasswordInNewPassword(String attribute) {
        return $(inputNewPassword).getAttribute(attribute);
    }

    @Step("Get error message after entering wrong New password")
    public String getErrorMessageNewPassword() {
        return $(errorMessageNewPassword).getText();
    }

    @Step("Click continue button on 3 step after entering new password")
    public PasswordRecoveryModalPage clickContinueButtonNewPassword() {
        $(continueButtonAfterEnterNewPass).click();
        return this;
    }

    @Step("Enter password in Confirm password field")
    public PasswordRecoveryModalPage enterPasswordInConfirmPasswordField(String newPassword) {
        $(inputConfirmPassword).sendKeys(newPassword);

        return this;
    }

    @Step("Get error message after entering wrong password in confirm password field")
    public String getErrorMessageConfirmPassword() {
        return  $(errorMessageConfirmPassword).getText();
    }

    @Step("Enter password in Confirm password field on the third step for password recovery and get aria-invalid type")
    public String getErrorMessageConfirmPasswordNotMuch() {
        return $(errorMessageConfirmPasswordNotMuch).getText();
    }

    @Step("Press the eye button to viewing or hidden entered new password")
    public PasswordRecoveryModalPage clickEyeButtonNewPassword() {
        $(eyeButtonNewPassword).click();
        return this;
    }

    @Step("Press the eye button to viewing or hidden entered confirm password")
    public PasswordRecoveryModalPage clickEyeButtonConfirmPassword() {
        $(eyeButtonConfirmPassword).click();
        return this;
    }

    @Step("Enter password in New password field on the third step for password recovery and get attribute status")
    public String getAttributeStatusAfterEnterPasswordInConfirmPassword(String attribute) {
        return $(inputConfirmPassword).getAttribute(attribute);
    }
}
