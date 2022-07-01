package by.andersen.amnbanking.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class ConfirmationCodeModalPage extends BasePage {
    public SelenideElement confirmationCodeModal = $(By.id(":r5:"));

    public boolean confirmationCodeWindowIsOpen() {
        this.confirmationCodeModal.shouldBe(Condition.visible, ofSeconds(5));

        return true;
    }
}
