package by.andersen.amnbanking.tests.ui_tests.page;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class ContactsPage extends BasePage {

    private static final By contactCenterPhones = (By.xpath("//span[text()= 'Contact center phones']"));
    private static final By backButton = (By.xpath("//button"));
    private static final By forIndividualsText = (By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/span[1]"));
    // the only way to find unique selector "forIndividualsText" is to use XPath as it is typed twice on the page
    // also tried //*[contains(@class, 'ContactsInformation_tittle__5m5qy')] and //*[(text()= 'For individuals')]
    private static final By cardsSupportText = (By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/span[1]"));

    @Step("Does Contact center phones line exist")
    public boolean getContactCenterPhones() {
        $(contactCenterPhones).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
        return true;
    }

    @Step("Click Back button to return to the Login page")
    public ContactsPage backToLoginPage() {
        $(backButton).click();
        return this;
    }

    @Step("Is 'For individuals' text present on the Contacts page")
    public boolean checkForIndividualsText() {
        $(forIndividualsText).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
        return true;
    }

    @Step("Is 'Cards support' text present on the Contacts page")
    public boolean checkCardsSupportText() {
        $(cardsSupportText).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
        return true;
    }

}
