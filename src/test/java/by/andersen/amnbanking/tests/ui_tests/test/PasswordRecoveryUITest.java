package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.apiControllers.Authentication;
import by.andersen.amnbanking.data.Alert;
import by.andersen.amnbanking.listener.UserDeleteListener;
import by.andersen.amnbanking.utils.DataProviderTests;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.sql.SQLException;

import static by.andersen.amnbanking.data.Alert.ID_WITHOUT_CHANGING_PASSWORD;
import static by.andersen.amnbanking.data.AuthToken.loginAndGetBearerToken;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_INVALID;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_VALID;
import static by.andersen.amnbanking.data.UsersData.USER_0NE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PERMANENT_REDIRECT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Listeners(UserDeleteListener.class)
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

    @TmsLink("5945657")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Check presence of information text with user phone on the code confirmation page")
    public void checkTextWithUserPhoneOnCodeConfirmationPage() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                ID_WITHOUT_CHANGING_PASSWORD.getValue());
    }

    @TmsLink("5945672")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Trying to send confirmation code when ban is not expired", enabled = false)
    public void sendConfirmCodeWhenBanNotExpired() throws SQLException {
        createUser();
        String authToken = loginAndGetBearerToken(USER_0NE.getUser().getLogin(),
                USER_0NE.getUser().getPassword());
        new Authentication().sendSessionCode(authToken, SMS_VALID.getValue(),  SC_PERMANENT_REDIRECT);
        new PostAdapters().post(setNewPassword(USER_0NE.getUser().getPassword()),
                API_HOST + CHANGE_PASSWORD + API_FIRST_ENTRY, authToken, SC_OK);

        loginPage.inputLoginField(USER_0NE.getUser().getLogin())
                .inputPasswordField(USER_0NE.getUser().getPassword())
                .clickLoginButton();
        for(int i = 0; i < 3; i++) {
            confirmationCodeModalPage.enterSmsCodeInFieldForCode(SMS_INVALID.getValue())
                    .clickConfirmButton();
        }
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(USER_0NE.getUser().getPassport())
                .clickContinueButton();

        deleteUser();
        //TODO: Finish and enable test when validation will be ready
    }


}

