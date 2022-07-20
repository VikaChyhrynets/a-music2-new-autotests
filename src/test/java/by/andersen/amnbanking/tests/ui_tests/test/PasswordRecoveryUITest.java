package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.utils.DataProviderTests;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.Alert.CONFIRMATION_CODE_MUST_BE_FILLED;
import static by.andersen.amnbanking.data.Alert.EMPTY_FIELDS;
import static by.andersen.amnbanking.data.Alert.FIELD_SHOULD_CONTAIN_FOUR_NUMBERS;
import static by.andersen.amnbanking.data.Alert.ID_LESS_THAN_2_SYMBOLS;
import static by.andersen.amnbanking.data.Alert.ID_MORE_30_SYMBOLS;
import static by.andersen.amnbanking.data.Alert.ID_WITHOUT_CHANGING_PASSWORD;
import static by.andersen.amnbanking.data.Alert.ID_WRONG_SYMBOLS;
import static by.andersen.amnbanking.data.Alert.SEND_SMS_POSITIVE;
import static by.andersen.amnbanking.data.Alert.UNREGISTERED_ID;
import static by.andersen.amnbanking.data.DataUrls.PASSPORT_REG;
import static by.andersen.amnbanking.data.SmsVerificationData.EMPTY_SMS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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

    @TmsLink("5945567")
    @Issue("A2N-492")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Try to change password with unregistered id number, positive test")
    public void passwordRecoveryWithUnregisteredIdTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber("MLF4568756DB123")
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                UNREGISTERED_ID.getMessage());
    }

    @TmsLink("5945581")
    @Issue("A2N-492")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Go to the previous page using the Back button, positive test")
    public void previouslyPageWithBackButtonOnTneFirstStepTest() {
        loginPage.clickLinkForgotPassword()
                .clickBackArrow();
        assertFalse(passwordRecovery.modalWindowIsNotVisible());
    }

    @TmsLink("5945657")
    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @Test(description = "Presence of the Enter the Confirmation code text on the code confirmation page, positive test")
    public void presenceTelephoneNumberWithTextTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber("BF12334376763")
                .clickContinueButton();
        assertEquals(passwordRecovery.getText2StepAfterSendSms(), SEND_SMS_POSITIVE.getMessage());
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
                EMPTY_FIELDS.getMessage());
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
                ID_LESS_THAN_2_SYMBOLS.getMessage());
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
                ID_MORE_30_SYMBOLS.getMessage());
    }

    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @TmsLinks({@TmsLink("5871565"), @TmsLink("5944823"), @TmsLink("5944832")})
    @Test(description = "Forbidden symbols in password field (small letter, russian letter, chars...)",
            dataProvider = "Forbidden symbols in Id number field", dataProviderClass = DataProviderTests.class)
    public void enterForbiddenSymbolsInIdFieldTest(String idNumber) {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(idNumber)
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                ID_WRONG_SYMBOLS.getMessage());
    }

    @TmsLink("5945073")
    @Issue("A2N-492")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Password recovery by a user who did not change their password on first login, positive test")
    public void enterValidPassportWithoutChangingFirstPassword() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber("DC")
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                ID_WITHOUT_CHANGING_PASSWORD.getMessage());
    }

    @TmsLinks(value = {@TmsLink("5945658"), @TmsLink("5945661"), @TmsLink("5945662"), @TmsLink("5945663"),
            @TmsLink("5945664"), @TmsLink("5945665")})
    @Issue("A2N-492")
    @Story("UC-1.3 Password recovery")
    @Test(dataProvider = "Invalid sms-code on 2 step password recovery", dataProviderClass = DataProviderTests.class,
            description = "Password recovery with invalid code confirmation field, negative test")
    public void enterInvalidSmsCodeConfirmationTest(String passport, String smsCode) {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(passport)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(smsCode)
                        .clickContinueButtonAfterEnteringSms();
        assertEquals(passwordRecovery.getErrorMessageCodeConfirmation(),
                FIELD_SHOULD_CONTAIN_FOUR_NUMBERS.getMessage());
    }

    @TmsLink("5945660")
    @Issue("A2N-492")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Password recovery with empty confirmation code")
    public void sendEmptyCodeConfirmationTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(EMPTY_SMS.getSms())
                .clickContinueButtonAfterEnteringSms();
        assertEquals(passwordRecovery.getErrorMessageCodeConfirmation(),
                CONFIRMATION_CODE_MUST_BE_FILLED.getMessage());
    }
}

