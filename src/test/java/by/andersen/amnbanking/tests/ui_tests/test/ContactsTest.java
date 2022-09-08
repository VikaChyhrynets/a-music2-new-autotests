package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.listener.UserDeleteListener;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.junit.Assert.assertTrue;

@Listeners(UserDeleteListener.class)
@Epic("E-1. Registration and authorization")
public class ContactsTest extends BaseUITest {

    @Story("UC-1.9 Contacts")
    @TmsLinks(value = {@TmsLink("C5957160"), @TmsLink("C5957163")})
    @Test(description = "Open the Contacts page")
    public void openContactsPage() {
        loginPage.clickContactsButton();
        assertTrue(contactsPage.getContactCenterPhones());
    }

    @Story("UC-1.9 Contacts")
    @TmsLinks(value = {@TmsLink("C5957159")})
    @Test(description = "Return to the Login page")
    public void ReturnToLoginPage() {
        loginPage.clickContactsButton();
        contactsPage.backToLoginPage();
        assertTrue(loginPage.returnToLoginPage());
    }

    @Story("UC-1.9 Contacts")
    @TmsLinks(value = {@TmsLink("C5957227"), @TmsLink("C5957234")})
    @Test(description = "Presence of text 'For individuals', 'Cards support' on the Сontacts page")
    public void textForIndividualsIsOnPage() {
        loginPage.clickContactsButton();
        assertTrue(contactsPage.checkForIndividualsText());
        assertTrue(contactsPage.checkCardsSupportText());
    }

}
