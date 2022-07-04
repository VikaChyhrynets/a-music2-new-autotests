package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.data.Alert;
import by.andersen.amnbanking.utils.TestRails;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ConfirmationCodeModalTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        loginPage.open();
    }

    @Test(description = "Enter correct 7 symbols in the login and password fields, positive test")
    public void authWithValidDataForLoginAndPasswordFieldsWithSevenSymbols() {
        assertTrue(loginPage.inputLoginField("Maleficent1")
                .inputPasswordField("Number1")
                .clickLoginButton().confirmationCodeWindowIsOpen());

    }

    @Test(description = "Close sms confirmation window and try to login again, negative test")
    public void closeSmsWindowAndLoginAgain(){
        assertTrue(loginPage.inputLoginField("UserForTest111")
                .inputPasswordField("Password11")
                .clickLoginButton()
                .closeSmsWindowByEmptyClick()
                .clickLoginButton().confirmationCodeWindowIsOpen());
    }

    @Test(description = "Authorization with less than 4 characters in the Code confirmation field, negative test")
    @TestRails(id = "C5931941")
    public void authWithLessThan4Digits() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode("123")
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(), Alert.FIELD_SHOULD_CONTAIN_FOUR_NUMBERS.getValue());
    }

    @Test(description = "Authorization with more than 4 characters in the Сode confirmation field, negative test")
    @TestRails(id = "C5931942")
    public void authWithLessMore4Digits() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode("12345")
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(), Alert.FIELD_SHOULD_CONTAIN_FOUR_NUMBERS.getValue());
    }

    @Test(description = "Authorization with more than 4 characters in the Сode confirmation field, negative test")
    @TestRails(id = "C5931938")
    public void authWithEmptySms() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode("")
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(), Alert.CONFIRMATION_CODE_MUST_BE_FILLED.getValue());
    }

    @Test(description = "Authorization with a space at the beginning of the Code confirmation field")
    @TestRails(id = "C5931945")
    public void authWithSmsWithSpaceAtTheBeginning() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode(" 1234")
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(), Alert.FIELD_SHOULD_CONTAIN_FOUR_NUMBERS.getValue());
    }

    @Test(description = "Authorization with a space at the end of the Code confirmation field")
    @TestRails(id = "C5931947")
    public void authWithSmsWithSpaceAtTheEnd() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode("1234 ")
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(), Alert.FIELD_SHOULD_CONTAIN_FOUR_NUMBERS.getValue());
    }

    @Test(description = "Authorization with only spaces in the Code confirmation field")
    @TestRails(id = "C5931948")
    public void authWithSmsWithOnlySpaces() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode("    ")
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(), Alert.FIELD_SHOULD_CONTAIN_FOUR_NUMBERS.getValue());
    }
}
