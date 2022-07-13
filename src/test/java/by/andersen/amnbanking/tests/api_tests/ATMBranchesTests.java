package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.DataUrls.API_BANK_INFO;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.utils.ParserJson.parserArray;
import static by.andersen.amnbanking.utils.RequestSpecificationUtils.requestSpecification;
import static by.andersen.amnbanking.utils.RequestUtils.getRequest;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("E-1. Registration and authorization")
public class ATMBranchesTests extends BaseAPITest {

    @DataProvider(name = "Valid Cities")
    public Object[][] getValidCity() {
        return new Object[][]{{"London"}, {"Madrid"}, {"Berlin"}, {"Warsaw"}};
    }

    @DataProvider(name = "Incorrect Cities")
    public Object[][] getIncorrectCity() {
        return new Object[][]{{"Moscow"}, {"Rome"}, {"Belgrade"}, {"Budapest"}};
    }

    @Story("UC-1.7 ATMs and branches")
    @TmsLink("5943277")
    @Test(description = "Getting data on ATMs and branches with valid data, positive test", dataProvider = "Valid Cities")
    public void testATMBranchesWithCityWhichIsInDataBase(String city) {
        String response = getRequest(API_HOST + API_BANK_INFO + city, requestSpecification, SC_OK).asString();
        assertTrue(parserArray(response) != 0);
    }

    @Story("UC-1.7 ATMs and branches")
    @TmsLink("5943292")
    @Test(description = "Getting data on ATMs and branches with invalid data, negative test", dataProvider = "Incorrect Cities")
    public void testATMBranchesWithCityWhichIsNotInDataBase(String city) {
        String response = getRequest(API_HOST + API_BANK_INFO + city, requestSpecification, SC_OK).asString();
        assertEquals(parserArray(response), 0);
    }
}