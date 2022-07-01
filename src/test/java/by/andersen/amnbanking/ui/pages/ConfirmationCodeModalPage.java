package by.andersen.amnbanking.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ConfirmationCodeModalPage extends BasePage {
    public SelenideElement confirmationCodeModal = $(By.id(":r5:"));
    public SelenideElement smsCodeInput = $(By.id(":r7:"));
    public SelenideElement confirmCodeButton = $x("//div[contains(@class, 'ModalForm_dialogSubmitButton')]/button");
    public SelenideElement sendAgainButton = $x("//div[contains(@class, 'ModalBox_dialogSendButton')]/button");
    public SelenideElement smsCodeValidation = $x("//div[contains(@class, 'Login_formInputSMSMessage')]");

    public boolean confirmationCodeWindowIsOpen(){

        return this.confirmationCodeModal.isDisplayed();
    }

    @Step("Closing SMS confirmation window by clicking to empty area")
    public LoginPage closeSmsWindowByEmptyClick() {
        confirmationCodeModal.click(200, 200);

        return new LoginPage();
    }

    @Step("Entering SMS code")
    public ConfirmationCodeModalPage inputSmsCode(String smsCode) {
        smsCodeInput.sendKeys(smsCode);

        return this;
    }

    @Step("Checking validation field")
    public String checkSmsInputValidationText() {return this.smsCodeValidation.getText();}
}
