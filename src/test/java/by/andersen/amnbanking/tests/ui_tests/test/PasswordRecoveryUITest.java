package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.data.Alert;
import by.andersen.amnbanking.utils.DataProviderTests;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.Alert.ID_WITHOUT_CHANGING_PASSWORD;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("E-1. Registration and authorization")
public class PasswordRecoveryUITest extends BaseUITest {

    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @Test(description = "Send correct ID number", dataProvider = "Valid ID number",
            dataProviderClass = DataProviderTests.class)
    public void enterValidIdNumberTest(String idNumber, String phoneNumber) {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(idNumber)
                .clickContinueButton();
        assertTrue(passwordRecovery.getPhoneNumberFrom2StepAfterSendSms(phoneNumber));
    }

    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @TmsLink("C5944799")
    @Test(description = "Try to send empty ID number")
    public void enterEmptyIdNumberTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber("")
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                Alert.EMPTY_FIELDS.getValue());
    }

    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @TmsLink("5871553")
    @Test(description = "Try to send ID Number with less than 2 symbols")
    public void enterLessThan2CharsInIdNumberTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber("P")
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                Alert.ID_LESS_THAN_2_SYMBOLS.getValue());
    }

    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @TmsLink("5871564")
    @Test(description = "Try to send ID number with more than 30 symbols")
    public void enter31SymbolsInIdNumberTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber("PKVRT21587469532014567852154879")
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                Alert.ID_MORE_30_SYMBOLS.getValue());
    }

    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @TmsLinks({@TmsLink("5871565"), @TmsLink("5944823"), @TmsLink("5944832")})
    @Test(description = "Forbidden symbols in password field (small letter, russian letter, chars...)",
            dataProvider = "Forbidden symbols in Id number field",  dataProviderClass = DataProviderTests.class)
    public void enterForbiddenSymbolsInIdFieldTest(String idNumber) {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(idNumber)
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                Alert.ID_WRONG_SYMBOLS.getValue());
    }

    @TmsLink("5945073")
    @Issue("A2N-492")
    @Story("UC-1.3 Password recovery")
    @Test()
    public void enterValidPassportWithoutChangingFirstPassword() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber("DC")
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                ID_WITHOUT_CHANGING_PASSWORD.getValue());
    }
}

