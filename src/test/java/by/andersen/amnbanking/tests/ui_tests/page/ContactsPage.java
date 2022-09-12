package by.andersen.amnbanking.tests.ui_tests.page;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import java.util.ArrayList;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.time.Duration.ofSeconds;

public class ContactsPage extends BasePage {
    private static final By contactCenterPhones = (By.xpath("//span[text()= 'Contact center phones']"));
    private static final By backButton = (By.xpath("//button"));
    private static final By forIndividualsText = (By.xpath("//div[contains(@class, 'Contacts_main')]" +
            "//child::span[contains(@class, 'ContactsInformation_tittle') and (text()= 'For individuals')]"));
    private static final By cardsSupportText = (By.xpath("//div[contains(@class, 'Contacts_main')]" +
            "//child::span[contains(@class, 'ContactsInformation_tittle') and (text()= 'Cards support')]"));
    private static final By commonTitleFourElements = (By.xpath("//span[contains(@class, 'ContactsInformation_tittle')]"));
    public static final String EXPECTED_INDIVIDUALS_TITLE = "For individuals";
    public static final String EXPECTED_CARDS_TITLE = "Cards support";
    public static final By localPhoneNumber = (By.xpath("//span[contains(@class, 'ContactsInformation_localText__NyKvb')]"));
    public static final By internationalPhoneNumber = (By.xpath("//span[contains(@class, 'ContactsInformation_internationalText__mcnWs')]"));
    public static final String LOCAL_CARDS_SUPPORT_PHONE = "3800";
    public static final String LOCAL_PHONE_FOR_INDIVIDUALS = "3700";
    public static final String INTERNATIONAL_CARDS_SUPPORT_PHONE = "+16846540103";
    public static final String INTERNATIONAL_PHONE_FOR_INDIVIDUALS = "+16846540102";




    @Step("Does Contact center phones line exist")
    public boolean getContactCenterPhones() {
        $(contactCenterPhones).shouldBe(Condition.visible, ofSeconds(10)).isDisplayed();
        return true;
    }

    @Step("Click Back button to return to the Login page")
    public ContactsPage backToLoginPage() {
        $(backButton).click();
        return new ContactsPage();
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

    public ArrayList<String> getTitles() {
        ArrayList<String> allTitles = new ArrayList<String>();
        for(int i = 0; i < $$(commonTitleFourElements).size(); i++) {
             allTitles.add($$(commonTitleFourElements).get(i).getText());
        }
        return allTitles;
    }

    public ArrayList<String> getLocalPhoneNumbers() {
    ArrayList<String> localPhoneNumbers = new ArrayList<String>();
        for(int i = 0; i < $$(localPhoneNumber).size(); i++) {
            localPhoneNumbers.add($$(localPhoneNumber).get(i).getText());
        }
        return localPhoneNumbers;
    }

    public ArrayList<String> getInternationalPhoneNumbers() {
        ArrayList<String> internationalPhoneNumbers = new ArrayList<String>();
        for(int i = 0; i < $$(internationalPhoneNumber).size(); i++) {
            internationalPhoneNumbers.add($$(internationalPhoneNumber).get(i).getText());
        }
        return internationalPhoneNumbers;
    }

}
