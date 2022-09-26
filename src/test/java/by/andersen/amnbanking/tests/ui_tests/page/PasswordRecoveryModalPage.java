package by.andersen.amnbanking.tests.ui_tests.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static by.andersen.amnbanking.data.UsersData.EMPTY_USER_FIELDS;
import static by.andersen.amnbanking.data.UsersData.LESS_THAN_MIN_CHARS;
import static by.andersen.amnbanking.data.UsersData.MORE_THAN_MAX_CHARS;
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
    private static final By errorMessageInputIdNumber = By.xpath("//div[text()= 'This ID number is not registered. Please check the entered data or contact the bank']");
    private static final By errorMessageWrongPassword = By.xpath("//div[contains(@class, 'RvDTg')]");
    private static final By errorMessageFieldChangeIDWithoutChangingPassword = By.xpath("//div[text()= 'This ID number is not registered. Please check the entered data or contact the bank']");
    private static final By titleTextWithPhoneNumber2Step = By.xpath("//p[contains(@class,'rg9OK')]");
    private static final By modalWindowPasswordRecovery = By.xpath("//*[contains(@class,'LvKN9')]");
    private static final By backButton = By.xpath("//*[text()= 'Back']");
    private static final By errorMessageSmsConfirmation = By.xpath("//*[contains(@class,'+xIHL')]");
    private static final By errorMessageNewPassword = By.xpath("//*[contains(@class,'0JwCH')]");
    private static final By errorMessageConfirmPassword = By.xpath("//div[contains(@class,'0JwCH')][2]");
    private static final By errorMessageConfirmPasswordNotMuch = By.xpath("//div[contains(@class,'0JwCH')]");
    private static final By eyeButtonNewPassword = By.xpath("//form[contains(@class,'07NPd')]//*[contains(@class,'g-r7n')]");
    private static final By eyeButtonConfirmPassword = By.xpath("//form/div[3]/div[2]");
    private static final By sendingCodeConfirmation = By.xpath("//p[contains(@class, 'rg9OK')]");


    @Step("In the modal Password Recovery first step is enter user's ID number")
    public PasswordRecoveryModalPage enterIdNumber(String idNumber) {
        $(inputIdNumber).sendKeys(idNumber);
        return this;
    }

    @Step("The user enter wrong ID Number and get message")
    public String getErrorMessageAfterEnterWrongIdNum() {
        return $(errorMessageInputIdNumber).getText();
    }

    @Step("The user enters empty ID Number and gets message")
    public String getErrorMessageAfterEnterEmptyIdNum() {
        return $(errorMessageWrongPassword).getText();
    }

    @Step("The user enters only 1 ID-symbol and gets message")
    public String getErrorMessageAfterEnter1Symbol() {
        return $(errorMessageWrongPassword).getText();
    }

    @Step("The user enters more than 30 symbols and gets message")
    public String getErrorMessageAfterEnterMoreThan30Symbols() {
        return $(errorMessageWrongPassword).getText();
    }

    @Step("The user enters forbidden symbols and gets message")
    public String getErrorMessageAfterEnterForbiddenSymbols() {
        return $(errorMessageWrongPassword).getText();
    }

    @Step("The user changes ID without changing password on first login and gets message")
    public String getErrorMessageAfterIDChangeWithNoChangingPassword() {
        return $(errorMessageFieldChangeIDWithoutChangingPassword).getText();
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
    @Step("Get text with sending code confirmation")
    public String getMessageAboutSendingCode() {
        return $(sendingCodeConfirmation).getText();
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

    @Step("Clear field")
    public PasswordRecoveryModalPage clearField() {
        for (int i = 0; i < $(inputIdNumber).getValue().length(); i++) {
            $(inputIdNumber).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        }
        return new PasswordRecoveryModalPage();
    }

    @Step("Enter empty ID")
    public PasswordRecoveryModalPage enterEmptyID() {
        enterIdNumber(EMPTY_USER_FIELDS.getUser().getPassport());
        clickContinueButton();
        return new PasswordRecoveryModalPage();
    }

    @Step("Enter less than minimal quantity of symbols")
    public PasswordRecoveryModalPage enterLessIDSymbols() {
        enterIdNumber(LESS_THAN_MIN_CHARS.getUser().getPassport());
        clickContinueButton();
        return new PasswordRecoveryModalPage();
    }

    @Step("Enter more than maximal quantity of symbols")
    public PasswordRecoveryModalPage enterMoreIDSymbols() {
        enterIdNumber(MORE_THAN_MAX_CHARS.getUser().getPassport());
        clickContinueButton();
        return new PasswordRecoveryModalPage();
    }

    @Step("Enter forbidden symbols")
    public PasswordRecoveryModalPage enterForbiddenSymbols(String idNumber) {
        enterIdNumber(idNumber);
        clickContinueButton();
        return new PasswordRecoveryModalPage();
    }

    @Step("User clicks the link 'Forgot password?'")
    public PasswordRecoveryModalPage forgotPassword() {
        enterIdNumber(EMPTY_USER_FIELDS.getUser().getPassport());
        clickContinueButton();
        return new PasswordRecoveryModalPage();
    }

}

