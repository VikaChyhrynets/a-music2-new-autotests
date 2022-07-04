package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.data.Alert;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.SQLException;

import static by.andersen.amnbanking.api.tests.BaseAPITest.createUser;
import static by.andersen.amnbanking.api.tests.BaseAPITest.deleteUser;

public class ConfirmationCodeModalTest extends BaseUITest {

    SoftAssert softAssert = new SoftAssert();

    @Test(description = "Enter correct 7 symbols in the login and password fields, positive test")
    public void authWithValidDataForLoginAndPasswordFieldsWithSevenSymbolsTest() {
        loginPage.inputLoginField("Maleficent1")
                .inputPasswordField("Number1")
                .clickLoginButton();
        Assert.assertEquals(confirmationCodeModalPage.confirmationCodeWindowIsOpen(), true);
    }

    @TestRails(id = "C5931949")
    @Step("Enter forbidden letter in smsCode field, negative test")
    @Test(description = "Enter invalid smsCode with 1 letter at the end, negative test")
    public void authWithInValidDataLetterForSmsCodeConfirmationTest() {
        loginPage.inputLoginField("UserTest1")
                .inputPasswordField("UserTest1")
                .clickLoginButton()
                .confirmationCodeWindowIsOpen();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("123l")
                .clickConfirmButton();
        Assert.assertEquals(confirmationCodeModalPage.getErrorMessageForWrongCodeConfirmation(), Alert.WRONG_CODE_CONFIRMATION_MODAL.getValue());
    }

    @TestRails(id = "C5931951")
    @Step("Enter forbidden symbol")
    @Test(description = "Enter invalid smsCode with forbidden symbol slash at the end, negative test")
    public void authWithForbiddenSymbolForSmsCodeConfirmationTest() {
        loginPage.inputLoginField("UserTest1")
                .inputPasswordField("UserTest1")
                .clickLoginButton()
                .confirmationCodeWindowIsOpen();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("123/")
                .clickConfirmButton();
        Assert.assertEquals(confirmationCodeModalPage.getErrorMessageForWrongCodeConfirmation(), Alert.WRONG_CODE_CONFIRMATION_MODAL.getValue());
    }

    @TestRails(id = "C5931952")
    @Step("Enter wrong sms code and then authorization again, positive test ")
    @Test(description = "Enter wrong sms code and then authorization again, positive test")
    public void authAfterEnteringWrongConfirmationCodeOneTimeTest() throws SQLException {
        try {
            createUser();
            loginPage.inputLoginField("Eminem79")
                    .inputPasswordField("Eminem79")
                    .clickLoginButton();
            confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                    .clickConfirmButton()
                    .getErrorMessageFromModalWrongSmsCode();
            softAssert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(), "You have entered an incorrect SMS code");
            confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode()
                    .refreshPage();
            loginPage.inputLoginField("Eminem79")
                    .inputPasswordField("Eminem79")
                    .clickLoginButton();
            Assert.assertEquals(confirmationCodeModalPage.confirmationCodeWindowIsOpen(), true);
        } finally {
           deleteUser();
        }
    }

    @TestRails(id = "C5869688")
    @Step("Enter the wrong sms code three times and get the ban, positive test ")
    @Test(description = "wrong sms code 3 times, get the ban on 30 minutes, positive test ")
    public void authAfterEnteringWrongSmsCodeThreeTimesTest() throws SQLException {
        try {
            createUser();
            for (int i = 0; i < 2; i++) {
            loginPage.inputLoginField("Eminem79")
                    .inputPasswordField("Eminem79")
                    .clickLoginButton();
            confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                    .clickConfirmButton();
            confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode();
            confirmationCodeModalPage.refreshPage();
            }
            loginPage.inputLoginField("Eminem79")
                    .inputPasswordField("Eminem79")
                    .clickLoginButton();
            confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                    .clickConfirmButton();
            Assert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(),
                    "You have entered an incorrect SMS code three times");
        } finally {
            deleteUser();
        }
    }

    @TestRails(id = "C5931955")
    @Step("Sending a confirmation code when the ban has not expired, positive test ")
    @Test(description = "send sms-code when the ban time hasn't expired, positive test ")
    public void sendSmsCodeWhenBanNotExpiredTest() throws SQLException {
        try {
            createUser();
            for (int i = 0; i < 3; i++) {
            loginPage.inputLoginField("Eminem79")
                    .inputPasswordField("Eminem79")
                    .clickLoginButton();
            confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                    .clickConfirmButton();
            confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode();
            confirmationCodeModalPage.refreshPage();
            }
            loginPage.inputLoginField("Eminem79")
                    .inputPasswordField("Eminem79")
                    .clickLoginButton();
            confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                    .clickConfirmButton();
            Assert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(), "Send code again in");
        } finally {
            deleteUser();
        }
    }
}

