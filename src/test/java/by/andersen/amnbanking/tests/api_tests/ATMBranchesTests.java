package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.model.ModelBranchesAndATM;
import by.andersen.amnbanking.utils.DataProviderForTests;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import java.util.List;

import static by.andersen.amnbanking.apiControllers.BankInfo.getInfoAboutATMAndBranches;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("E-1. Registration and authorization")
public class ATMBranchesTests extends BaseAPITest {

    @Story("UC-1.7 ATMs and branches")
    @TmsLink("5943277")
    @Test(description = "Getting data on ATMs and branches with valid data, positive test",
            dataProvider = "Valid Cities", dataProviderClass = DataProviderForTests.class)
    public void testATMBranchesWithCityWhichIsInDataBase(String city) {
        List<ModelBranchesAndATM> branchesAndATMList = getInfoAboutATMAndBranches(city, SC_OK);
        assertTrue(branchesAndATMList.size() != 0);
    }

    @Story("UC-1.7 ATMs and branches")
    @TmsLink("5943292")
    @Test(description = "Getting data on ATMs and branches with invalid data, negative test",
            dataProvider = "Incorrect Cities", dataProviderClass = DataProviderForTests.class)
    public void testATMBranchesWithCityWhichIsNotInDataBase(String city) {
        List<ModelBranchesAndATM> branchesAndATMList = getInfoAboutATMAndBranches(city, SC_OK);
        assertEquals(branchesAndATMList.size(), 0);
    }
}