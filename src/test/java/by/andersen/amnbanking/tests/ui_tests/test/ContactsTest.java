package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.listener.UserDeleteListener;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.ArrayList;

import static by.andersen.amnbanking.data.Contacts.EXPECTED_CARDS_TITLE;
import static by.andersen.amnbanking.data.Contacts.EXPECTED_INDIVIDUALS_TITLE;
import static by.andersen.amnbanking.data.Contacts.INTERNATIONAL_CARDS_SUPPORT_PHONE;
import static by.andersen.amnbanking.data.Contacts.INTERNATIONAL_PHONE_FOR_INDIVIDUALS;
import static by.andersen.amnbanking.data.Contacts.LOCAL_CARDS_SUPPORT_PHONE;
import static by.andersen.amnbanking.data.Contacts.LOCAL_PHONE_FOR_INDIVIDUALS;
import static org.junit.Assert.assertEquals;
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
    @Test(description = "Presence of text and count of elements 'For individuals', 'Cards support' on the Contacts page")
    public void checkTitlesAvailability(){
        loginPage.clickContactsButton();
        ArrayList<String> allTitles = contactsPage.getTitles();
        assertEquals(4, allTitles.size());
        int forInd = 0;
        int cardSup = 0;
        for(int i = 0; i < allTitles.size(); i++) {
            if(!allTitles.get(i).equals(EXPECTED_CARDS_TITLE)) {
                assertEquals(EXPECTED_INDIVIDUALS_TITLE,allTitles.get(i));
                forInd++;
            }
            else {
                assertEquals(EXPECTED_CARDS_TITLE,allTitles.get(i));
                cardSup++;
            }
        }
        assertEquals(2, forInd);
        assertEquals(2, cardSup);
    }

    @Story("UC-1.9 Contacts")
    @Test(description = "Presence of correct local phone numbers on the Contacts page")
    public void checkCorrectLocalPhoneNumbers() {
        loginPage.clickContactsButton();
        ArrayList<String> localPhoneNumbers = contactsPage.getLocalPhoneNumbers();
        for(int i = 0; i < localPhoneNumbers.size(); i++) {
            if(!localPhoneNumbers.get(i).equals(LOCAL_CARDS_SUPPORT_PHONE)) {
                assertEquals(LOCAL_PHONE_FOR_INDIVIDUALS,localPhoneNumbers.get(i));
            }
            if(!localPhoneNumbers.get(i).equals(LOCAL_PHONE_FOR_INDIVIDUALS)) {
                assertEquals(LOCAL_CARDS_SUPPORT_PHONE,localPhoneNumbers.get(i));
            }
        }
    }

    @Story("UC-1.9 Contacts")
    @Test(description = "Presence of correct international phone numbers on the Contacts page")
    public void checkCorrectInternationalPhoneNumbers() {
        loginPage.clickContactsButton();
        ArrayList<String> internationalPhoneNumbers = contactsPage.getInternationalPhoneNumbers();
        for(int i = 0; i < internationalPhoneNumbers.size(); i++) {
            if(!internationalPhoneNumbers.get(i).equals(INTERNATIONAL_CARDS_SUPPORT_PHONE)) {
                assertEquals(INTERNATIONAL_PHONE_FOR_INDIVIDUALS,internationalPhoneNumbers.get(i));
            }
            if(!internationalPhoneNumbers.get(i).equals(INTERNATIONAL_PHONE_FOR_INDIVIDUALS)) {
                assertEquals(INTERNATIONAL_CARDS_SUPPORT_PHONE,internationalPhoneNumbers.get(i));
            }
        }
    }

}
