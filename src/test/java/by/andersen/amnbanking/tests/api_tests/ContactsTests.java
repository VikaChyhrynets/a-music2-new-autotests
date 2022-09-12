package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import by.andersen.amnbanking.listener.UserDeleteListener;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import static by.andersen.amnbanking.DBConnector.DBConnector.INTERNATIONAL_PHONE_NUMBER_FOR_CARD_SUPPORT;
import static by.andersen.amnbanking.DBConnector.DBConnector.INTERNATIONAL_PHONE_NUMBER_FOR_INDIVIDUALS;
import static by.andersen.amnbanking.DBConnector.DBConnector.LOCAL_PHONE_NUMBER_FOR_CARD_SUPPORT;
import static by.andersen.amnbanking.DBConnector.DBConnector.LOCAL_PHONE_NUMBER_FOR_INDIVIDUALS;
import static by.andersen.amnbanking.DBConnector.DBConnector.OPENING_HOURS_FOR_CARD_SUPPORT;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_CONTACTS;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static by.andersen.amnbanking.DBConnector.DBConnector.WORKING_HOURS;
import static org.junit.Assert.assertEquals;
import static by.andersen.amnbanking.model.ModelContacts.arrayOfInternationalPhoneNumbers;

@Listeners(UserDeleteListener.class)
@Epic("E-1. Registration and authorization")
public class ContactsTests extends BaseAPITest {

    @Test(description = "Getting contacts with valid data")
    @Story("UC-1.9 Contacts")
    @TmsLink("C5951590")
    void getContacts() {
        ArrayList<String> expectedInternationalContacts = arrayOfInternationalPhoneNumbers();
        ArrayList<String> availableInternationalContacts = new GetAdapters().get(API_HOST + API_CONTACTS, SC_OK).path("internationalPhone");
        assertEquals(expectedInternationalContacts, availableInternationalContacts);
    }

    @Test(description = "Displaying opening hours for individuals")
    @Story("UC-1.9 Contacts")
    @TmsLink("C5957231")
    void displayOpeningHoursForIndividuals() throws SQLException {
        ArrayList<ArrayList<HashMap<String, String>>> availableOpeningHours = new GetAdapters().get(API_HOST + API_CONTACTS, SC_OK).path("operationModes");
        ArrayList<HashMap<String, String>> individualHours = availableOpeningHours.get(0);
        ArrayList<String> individualHoursResultGet = new ArrayList<String>();
        for (HashMap<String, String> individualHoursByDay : individualHours) {
            String openingTime = individualHoursByDay.get("openingTime").replace(":", "").replace("Z", "");
            String closingTime = individualHoursByDay.get("closingTime").replace(":", "").replace("Z", "");
            individualHoursResultGet.add(individualHoursByDay.get("dayOfWeek") + openingTime + closingTime);
        }
        getWorkingHoursForIndividuals();
        assertEquals(WORKING_HOURS, individualHoursResultGet);
    }

    @Test(description = "Phone number for individuals")
    @Story("UC-1.9 Contacts")
    @TmsLink("C5957233")
    void displayPhoneNumberForIndividuals() throws SQLException {
        ArrayList<String> localPhone = new GetAdapters().get(API_HOST + API_CONTACTS, SC_OK).path("localPhone");
        ArrayList<String> internationalPhone = new GetAdapters().get(API_HOST + API_CONTACTS, SC_OK).path("internationalPhone");
        getPhoneNumberForIndividuals();
        assertEquals(LOCAL_PHONE_NUMBER_FOR_INDIVIDUALS, localPhone.get(0));
        assertEquals(INTERNATIONAL_PHONE_NUMBER_FOR_INDIVIDUALS, internationalPhone.get(0));
    }

    @Test(description = "Displaying opening hours for 'Card Support'")
    @Story("UC-1.9 Contacts")
    @TmsLink("C5957235")
    void displayOpeningHoursForCardSupport() throws SQLException {
        ArrayList<ArrayList<HashMap<String, String>>> availableOpeningHours = new GetAdapters().get(API_HOST + API_CONTACTS, SC_OK).path("operationModes");
        ArrayList<HashMap<String, String>> individualHours = availableOpeningHours.get(1);
        ArrayList<String> openingHoursGet = new ArrayList<String>();
        for (HashMap<String, String> openingHours : individualHours) {
            String openingTime = openingHours.get("openingTime").replace(":", "").replace("Z", "");
            String closingTime = openingHours.get("closingTime").replace(":", "").replace("Z", "");
            openingHoursGet.add(openingHours.get("dayOfWeek") + openingTime + closingTime);
        }
        getOpeningHoursForCardSupport();
        assertEquals(OPENING_HOURS_FOR_CARD_SUPPORT, openingHoursGet);
    }

    @Test(description = "Phone number for 'Card Support'")
    @Story("UC-1.9 Contacts")
    @TmsLink("C5957237")
    void displayPhoneNumberForCardSupport() throws SQLException {
        ArrayList<String> localPhone = new GetAdapters().get(API_HOST + API_CONTACTS, SC_OK).path("localPhone");
        ArrayList<String > internationalPhone = new GetAdapters().get(API_HOST + API_CONTACTS, SC_OK).path("internationalPhone");
        getPhoneNumberForCardSupport();
        assertEquals(LOCAL_PHONE_NUMBER_FOR_CARD_SUPPORT, localPhone.get(1));
        assertEquals(INTERNATIONAL_PHONE_NUMBER_FOR_CARD_SUPPORT,  internationalPhone.get(1));
    }

}
