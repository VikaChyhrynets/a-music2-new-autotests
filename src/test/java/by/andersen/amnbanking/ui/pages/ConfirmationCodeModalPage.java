package by.andersen.amnbanking.ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ConfirmationCodeModalPage extends BasePage {
    public SelenideElement confirmationCodeModal = $(By.id(":r5:"));

    public boolean confirmationCodeWindowIsOpen(){

        return this.confirmationCodeModal.isDisplayed();
    }
}
