package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.data.Alert;
import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Listeners(UserDeleteListener.class)
public class ConfirmationCodeModalTest extends BaseUITest {

    SoftAssert softAssert = new SoftAssert();

    @Test(description = "Enter correct 7 symbols in the login and password fields, positive test")
    public void authWithValidDataForLoginAndPasswordFieldsWithSevenSymbolsTest() {
        loginPage.inputLoginField("Maleficent1")
                .inputPasswordField("Number1")
                .clickLoginButton();
        assertTrue(confirmationCodeModalPage.confirmationCodeWindowIsOpen());
    }

    @TestRails(id = "C5931949")
    @Step("Enter forbidden letter in smsCode field, negative test")
    @Test(description = "Enter invalid smsCode with 1 letter at the end, negative test")
    public void authWithInValidDataLetterForSmsCodeConfirmationTest() throws SQLException {
        createUser();
        loginPage.inputLoginField("Vladivostok2000")
                .inputPasswordField("Vladivostok2000")
                .clickLoginButton()
                .confirmationCodeWindowIsOpen();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("123l")
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.getErrorMessageForWrongCodeConfirmation(), Alert.FIELD_SHOULD_CONTAIN_FOUR_NUMBERS.getValue());
        deleteUser();
    }

    @TestRails(id = "C5931951")
    @Step("Enter forbidden symbol")
    @Test(description = "Enter invalid smsCode with forbidden symbol slash at the end, negative test")
    public void authWithForbiddenSymbolForSmsCodeConfirmationTest() throws SQLException {
        createUser();
        loginPage.inputLoginField("Vladivostok2000")
                .inputPasswordField("Vladivostok2000")
                .clickLoginButton()
                .confirmationCodeWindowIsOpen();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("123/")
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.getErrorMessageForWrongCodeConfirmation(), Alert.FIELD_SHOULD_CONTAIN_FOUR_NUMBERS.getValue());
        deleteUser();
    }

    @TestRails(id = "C5931952")
    @Step("Enter wrong sms code and then authorization again, positive test ")
    @Test(description = "Enter wrong sms code and then authorization again, positive test")
    public void authAfterEnteringWrongConfirmationCodeOneTimeTest() throws SQLException {
        createUser();
        loginPage.inputLoginField("Vladivostok2000")
                .inputPasswordField("Vladivostok2000")
                .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                .clickConfirmButton()
                .getErrorMessageFromModalWrongSmsCode();
        softAssert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(), "You have entered an incorrect SMS code");
        confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode()
                .refreshPage();
        loginPage.inputLoginField("Vladivostok2000")
                .inputPasswordField("Vladivostok2000")
                .clickLoginButton();
        assertTrue(confirmationCodeModalPage.confirmationCodeWindowIsOpen());
        deleteUser();
    }

    @TestRails(id = "C5869688")
    @Step("Enter the wrong sms code three times and get the ban, positive test ")
    @Test(description = "wrong sms code 3 times, get the ban on 30 minutes, positive test ")
    public void authAfterEnteringWrongSmsCodeThreeTimesTest() throws SQLException {
        createUser();
        for (int i = 0; i < 2; i++) {
            loginPage.inputLoginField("Vladivostok2000")
                    .inputPasswordField("Vladivostok2000")
                    .clickLoginButton();
            confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                    .clickConfirmButton();
            confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode();
            confirmationCodeModalPage.refreshPage();
        }
        loginPage.inputLoginField("Vladivostok2000")
                .inputPasswordField("Vladivostok2000")
                .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                .clickConfirmButton();
        Assert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(),
                "You have entered an incorrect SMS code three times");
        deleteUser();
    }

    @TestRails(id = "C5931955")
    @Step("Sending a confirmation code when the ban has not expired, positive test ")
    @Test(description = "send sms-code when the ban time hasn't expired, positive test ")
    public void sendSmsCodeWhenBanNotExpiredTest() throws SQLException {
        createUser();
        for (int i = 0; i < 3; i++) {
            loginPage.inputLoginField("Vladivostok2000")
                    .inputPasswordField("Vladivostok2000")
                    .clickLoginButton();
            confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                    .clickConfirmButton();
            confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode();
            confirmationCodeModalPage.refreshPage();
        }
        loginPage.inputLoginField("Vladivostok2000")
                .inputPasswordField("Vladivostok2000")
                .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                .clickConfirmButton();
        Assert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(), "Send code again in");
        deleteUser();
    }

    @Test(description = "Close sms confirmation window and try to login again, negative test")
    public void closeSmsWindowAndLoginAgain() {
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