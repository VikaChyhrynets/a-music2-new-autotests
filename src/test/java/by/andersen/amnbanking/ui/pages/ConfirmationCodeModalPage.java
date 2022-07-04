package by.andersen.amnbanking.ui.pages;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ConfirmationCodeModalPage extends BasePage {
    private static final SelenideElement CONFIRMATION_CODE_MODAL = $(By.id(":r5:"));
    private static final SelenideElement SMS_CODE_INPUT = $(By.id(":r7:"));
    private static final SelenideElement CONFIRM_CODE_BUTTON = $x("//div[contains(@class, 'ModalForm_dialogSubmitButton')]/button");
    public SelenideElement sendAgainButton = $x("//div[contains(@class, 'ModalBox_dialogSendButton')]/button");
    private static final SelenideElement SMS_CODE_VALIDATION = $x("//div[contains(@class, 'Login_formInputSMSMessage')]");
    public static final SelenideElement SUCCESS_LOGIN_MODAL = $(By.id("ModalSuccessfully"));

    public boolean confirmationCodeWindowIsOpen() {return CONFIRMATION_CODE_MODAL.isDisplayed();}

    @Step("Closing SMS confirmation window by clicking to empty area")
    public LoginPage closeSmsWindowByEmptyClick() {
        CONFIRMATION_CODE_MODAL.click(ClickOptions.usingDefaultMethod().offset(200, 200));

        return new LoginPage();
    }

    @Step("Entering SMS code")
    public ConfirmationCodeModalPage inputSmsCode(String smsCode) {
        SMS_CODE_INPUT.sendKeys(smsCode);

        return this;
    }

    @Step("Checking validation field")
    public String checkSmsInputValidationText() {return SMS_CODE_VALIDATION.getText();}

    @Step("Clicking Confirm button")
    public LoginPage clickConfirmButton() {
        CONFIRM_CODE_BUTTON.click();
        return new LoginPage();
    }
}
