package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.Alert;
import by.andersen.amnbanking.data.UsersData;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;

@Epic("Epic 1: Registration and authorization")
public class ConfirmationCodeModalTest extends BaseUITest {

    @Override
    @BeforeMethod
    public void deleteUser() throws SQLException {
        new DBConnector().deleteUser("Eminem79");
    }

    @Override
    public void createUser() {
        new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration(
                        UsersData.USER_EMINEM79.getUser().getLogin(),
                        UsersData.USER_EMINEM79.getUser().getPassword(),
                        UsersData.USER_EMINEM79.getUser().getPassport(),
                        UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
                API_HOST + API_REGISTRATION, SC_OK);
    }

    SoftAssert softAssert = new SoftAssert();

    @Test(description = "Enter correct 7 symbols in the login and password fields, positive test")
    public void authWithValidDataForLoginAndPasswordFieldsWithSevenSymbolsTest() {
        loginPage.inputLoginField("Maleficent1")
                .inputPasswordField("Number1")
                .clickLoginButton();
        Assert.assertEquals(confirmationCodeModalPage.confirmationCodeWindowIsOpen(), true);
    }

    @TmsLink("5931949")
    @Story("UC-1.10 Code confirmation.")
    @Test(description = "Enter invalid smsCode with 1 letter at the end, negative test")
    public void authWithInValidDataLetterForSmsCodeConfirmationTest() {
        loginPage.inputLoginField("UserTest1")
                .inputPasswordField("UserTest1")
                .clickLoginButton()
                .confirmationCodeWindowIsOpen();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("123l")
                .clickConfirmButton();
        Assert.assertEquals(confirmationCodeModalPage.getErrorMessageForWrongCodeConfirmation(),
                Alert.WRONG_CODE_CONFIRMATION_MODAL.getValue());
    }

    @TmsLink("5931951")
    @Story("UC-1.10 Code confirmation.")
    @Test(description = "Enter invalid smsCode with forbidden symbol slash at the end, negative test")
    public void authWithForbiddenSymbolForSmsCodeConfirmationTest() {
        loginPage.inputLoginField("UserTest1")
                .inputPasswordField("UserTest1")
                .clickLoginButton()
                .confirmationCodeWindowIsOpen();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("123/")
                .clickConfirmButton();
        Assert.assertEquals(confirmationCodeModalPage.getErrorMessageForWrongCodeConfirmation(),
                Alert.WRONG_CODE_CONFIRMATION_MODAL.getValue());
    }

    @TmsLink("5931952")
    @Story("UC-1.10 Code confirmation.")
    @Test(description = "Enter wrong sms code and then authorization again, positive test")
    public void authAfterEnteringWrongConfirmationCodeOneTimeTest()  {
        createUser();
        loginPage.inputLoginField("Eminem79")
                .inputPasswordField("111Gv5dvvf511")
                .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                .clickConfirmButton()
                .getErrorMessageFromModalWrongSmsCode();
        softAssert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(),
                "You have entered an incorrect SMS code");
        confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode()
                .refreshPage();
        loginPage.inputLoginField("Eminem79")
                .inputPasswordField("111Gv5dvvf511")
                .clickLoginButton();
        Assert.assertEquals(confirmationCodeModalPage.confirmationCodeWindowIsOpen(), true);
    }

    @TmsLink("5869688")
    @Story("UC-1.10 Code confirmation.")
    @Test(description = "wrong sms code 3 times, get the ban on 30 minutes, positive test ")
    public void authAfterEnteringWrongSmsCodeThreeTimesTest()  {
        createUser();
        for (int i = 0; i < 2; i++) {
            loginPage.inputLoginField("Eminem79")
                    .inputPasswordField("111Gv5dvvf511")
                    .clickLoginButton();
            confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                    .clickConfirmButton();
            confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode();
            confirmationCodeModalPage.refreshPage();
        }
        loginPage.inputLoginField("Eminem79")
                .inputPasswordField("111Gv5dvvf511")
                .clickLoginButton();
        confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                .clickConfirmButton();
        Assert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(),
                "You have entered an incorrect SMS code three times");
    }

    @TmsLink("5931955")
    @Story("UC-1.10 Code confirmation.")
    @Test(description = "send sms-code when the ban time hasn't expired, positive test ")
    public void sendSmsCodeWhenBanNotExpiredTest()  {
        createUser();
            for (int i = 0; i < 3; i++) {
                loginPage.inputLoginField("Eminem79")
                        .inputPasswordField("111Gv5dvvf511")
                        .clickLoginButton();
                confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                        .clickConfirmButton();
                confirmationCodeModalPage.clickProceedModalWrongMessageSmsCode();
                confirmationCodeModalPage.refreshPage();
            }
            loginPage.inputLoginField("Eminem79")
                    .inputPasswordField("111Gv5dvvf511")
                    .clickLoginButton();
            confirmationCodeModalPage.enterSmsCodeInFieldForCode("1235")
                    .clickConfirmButton();
            Assert.assertEquals(confirmationCodeModalPage.getErrorMessageFromModalWrongSmsCode(),
                    "Send code again in");
    }
}

