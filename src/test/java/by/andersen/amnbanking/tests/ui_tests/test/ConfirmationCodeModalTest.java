package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.Alert.CONFIRMATION_CODE_MUST_BE_FILLED;
import static by.andersen.amnbanking.data.Alert.FIELD_SHOULD_CONTAIN_FOUR_NUMBERS;
import static by.andersen.amnbanking.data.Alert.INCORRECT_SMS_CODE;
import static by.andersen.amnbanking.data.Alert.MESSAGE_INCORRECT_SMS_3_TIMES;
import static by.andersen.amnbanking.data.Alert.SEND_CODE_AGAIN;
import static by.andersen.amnbanking.data.DataUrls.LOGIN_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.DataUrls.PASSWORD_WITH_PASSPORT_REG;
import static by.andersen.amnbanking.data.SmsVerificationData.EMPTY_SMS;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_3_SYMBOLS;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_4_SPACES;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_5_SYMBOLS;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_BEGIN_SPACE;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_INVALID;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_SLASH_END;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_SPACE_END;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_WITH_LETTER;
import static by.andersen.amnbanking.data.UsersData.USER_ONE;
import static by.andersen.amnbanking.data.UsersData.USER_MALEFICENT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Listeners(UserDeleteListener.class)
@Epic("E-1. Registration and authorization")
public class ConfirmationCodeModalTest extends BaseUITest {

    SoftAssert softAssert = new SoftAssert();

    @Story("UC-1.10 Confirmation code")
    @Test(description = "Enter correct 7 symbols in the login and password fields, positive test")
    public void authWithValidDataForLoginAndPasswordFieldsWithSevenSymbolsTest() {
        loginPage.inputLoginField(USER_MALEFICENT.getUser().getLogin())
                .inputPasswordField(USER_MALEFICENT.getUser().getPassword())
                .clickLoginButton();
        assertTrue(confirmationCodeModalPage.confirmationCodeWindowIsOpen());
    }

    @Story("UC-1.10 Confirmation code")
    @TestRails(id = "C5931949")
    @Test(description = "Enter invalid smsCode with 1 letter at the end, negative test")
    public void authWithInValidDataLetterForSmsCodeConfirmationTest() throws SQLException {
        createUser();
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(USER_ONE.getUser().getPassword())
                .clickLoginButton()
                .confirmationCodeWindowIsOpen();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode(SMS_WITH_LETTER.getSms())
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.getErrorMessageForWrongCodeConfirmation(),
                FIELD_SHOULD_CONTAIN_FOUR_NUMBERS);
        deleteUser();
    }

    @Story("UC-1.10 Confirmation code")
    @TestRails(id = "C5931951")
    @Test(description = "Enter invalid smsCode with forbidden symbol slash at the end, negative test")
    public void authWithForbiddenSymbolForSmsCodeConfirmationTest() throws SQLException {
        createUser();
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(USER_ONE.getUser().getPassword())
                .clickLoginButton()
                .confirmationCodeWindowIsOpen();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode(SMS_SLASH_END.getSms())
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.getErrorMessageForWrongCodeConfirmation(),
                FIELD_SHOULD_CONTAIN_FOUR_NUMBERS);
        deleteUser();
    }

    @Story("UC-1.10 Confirmation code")
    @TestRails(id = "C5931952")
    @Test(description = "Enter wrong sms code and then authorization again, positive test")
    public void authAfterEnteringWrongConfirmationCodeOneTimeTest() throws SQLException {
        createUser();
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(USER_ONE.getUser().getPassword())
                .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode(SMS_INVALID.getSms())
                .clickConfirmButton()
                .getErrorMessageFromModalWrongSmsCode();
        softAssert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(),
                INCORRECT_SMS_CODE);
        confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode()
                .refreshPage();
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(USER_ONE.getUser().getPassword())
                .clickLoginButton();
        assertTrue(confirmationCodeModalPage.confirmationCodeWindowIsOpen());
        deleteUser();
    }

    @Story("UC-1.10 Confirmation code")
    @TmsLink("5869688")
    @Test(description = "Enter the wrong sms code three times and get the ban, positive test, positive test ")
    public void authAfterEnteringWrongSmsCodeThreeTimesTest() throws SQLException {
        createUser();
        for (int i = 0; i < 2; i++) {
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                 .inputPasswordField(USER_ONE.getUser().getPassword())
                 .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode(SMS_INVALID.getSms())
                 .clickConfirmButton();
        confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode();
        confirmationCodeModalPage.refreshPage();
    }
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(USER_ONE.getUser().getPassword())
                .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode(SMS_INVALID.getSms())
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(),
                MESSAGE_INCORRECT_SMS_3_TIMES);
        deleteUser();
    }

    @Story("UC-1.10 Confirmation code")
    @TmsLink("5931955")
    @Test(description = "Sending a confirmation code when the ban has not expired, positive test, positive test ")
    public void sendSmsCodeWhenBanNotExpiredTest() throws SQLException {
        createUser();
        for (int i = 0; i < 3; i++) {
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                 .inputPasswordField(USER_ONE.getUser().getPassword())
                 .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode(SMS_INVALID.getSms())
                 .clickConfirmButton();
        confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode();
        confirmationCodeModalPage.refreshPage();
    }
        loginPage.inputLoginField(USER_ONE.getUser().getLogin())
                .inputPasswordField(USER_ONE.getUser().getPassword())
                .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode(SMS_INVALID.getSms())
                .clickConfirmButton();
        Assert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(),
                SEND_CODE_AGAIN);
        deleteUser();
    }

    @Story("UC-1.10 Confirmation code")
    @Test(description = "Close sms confirmation window and try to login again, negative test")
    public void closeSmsWindowAndLoginAgain() {
        assertTrue(loginPage.inputLoginField(LOGIN_WITH_PASSPORT_REG)
                .inputPasswordField(PASSWORD_WITH_PASSPORT_REG)
                .clickLoginButton()
                .closeSmsWindowByEmptyClick()
                .clickLoginButton().confirmationCodeWindowIsOpen());
    }

    @Story("UC-1.10 Confirmation code")
    @TmsLink("5931941")
    @Test(description = "Authorization with less than 4 characters in the Code confirmation field, negative test")
    public void authWithLessThan4Digits() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode(SMS_3_SYMBOLS.getSms())
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(),
                FIELD_SHOULD_CONTAIN_FOUR_NUMBERS);
    }

    @Story("UC-1.10 Confirmation code")
    @TmsLink("5931942")
    @Test(description = "Authorization with more than 4 characters in the Сode confirmation field, negative test")
    public void authWithLessMore4Digits() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode(SMS_5_SYMBOLS.getSms())
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(),
                FIELD_SHOULD_CONTAIN_FOUR_NUMBERS);
    }

    @Story("UC-1.10 Confirmation code")
    @TmsLink("5931938")
    @Test(description = "Authorization with more than 4 characters in the Сode confirmation field, negative test")
    public void authWithEmptySms() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode(EMPTY_SMS.getSms())
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(),
               CONFIRMATION_CODE_MUST_BE_FILLED);
    }

    @Story("UC-1.10 Confirmation code")
    @TmsLink("5931945")
    @Test(description = "Authorization with a space at the beginning of the Code confirmation field")
    public void authWithSmsWithSpaceAtTheBeginning() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode(SMS_BEGIN_SPACE.getSms())
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(),
                FIELD_SHOULD_CONTAIN_FOUR_NUMBERS);
    }

    @Story("UC-1.10 Confirmation code")
    @TmsLink("5931947")
    @Test(description = "Authorization with a space at the end of the Code confirmation field")
    public void authWithSmsWithSpaceAtTheEnd() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode(SMS_SPACE_END.getSms())
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(),
                FIELD_SHOULD_CONTAIN_FOUR_NUMBERS);
    }

    @Story("UC-1.10 Confirmation code")
    @TmsLink("5931948")
    @Test(description = "Authorization with only spaces in the Code confirmation field")
    public void authWithSmsWithOnlySpaces() {
        loginPage.loginUserWithCreds(LOGIN_WITH_PASSPORT_REG, PASSWORD_WITH_PASSPORT_REG)
                .inputSmsCode(SMS_4_SPACES.getSms())
                .clickConfirmButton();
        assertEquals(confirmationCodeModalPage.checkSmsInputValidationText(),
                FIELD_SHOULD_CONTAIN_FOUR_NUMBERS);
    }
}