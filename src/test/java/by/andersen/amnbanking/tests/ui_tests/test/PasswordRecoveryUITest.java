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
import static by.andersen.amnbanking.data.DataUrls.API_FIRST_ENTRY;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.CHANGE_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.PASSPORT_REG;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_INVALID;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_VALID;
import static by.andersen.amnbanking.data.UsersData.USER_0NE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setNewPassword;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PERMANENT_REDIRECT;
import static by.andersen.amnbanking.data.Alert.*;
import static by.andersen.amnbanking.data.DataUrls.PASSPORT_REG;
import static by.andersen.amnbanking.data.SmsVerificationData.EMPTY_SMS;
import static by.andersen.amnbanking.data.SmsVerificationData.SMS_VALID;
import static by.andersen.amnbanking.data.UsersData.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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

    @TmsLink("5871567")
    @Issue("A2N-492")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Try to change password with unregistered id number, positive test")
    public void passwordRecoveryWithUnregisteredIdTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(EM79_VALID_PASS.getUser().getPassport())
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                UNREGISTERED_ID);
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
        assertEquals(passwordRecovery.getText2StepAfterSendSms(), SEND_SMS_POSITIVE);
    }

    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @TmsLink("C5944799")
    @Test(description = "Try to send empty ID number")
    public void enterEmptyIdNumberTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(EMPTY_USER_FIELDS.getUser().getPassport())
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                EMPTY_FIELDS);
    }

    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @TmsLink("5871553")
    @Test(description = "Try to send ID Number with less than 2 symbols")
    public void enterLessThan2CharsInIdNumberTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(LESS_THAN_MIN_CHARS.getUser().getPassport())
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                ID_LESS_THAN_2_SYMBOLS);
    }

    @Story("UC-1.3 Password recovery")
    @Issue("A2N-492")
    @TmsLink("5871564")
    @Test(description = "Try to send ID number with more than 30 symbols")
    public void enter31SymbolsInIdNumberTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(MORE_THAN_MAX_CHARS.getUser().getPassport())
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                ID_MORE_30_SYMBOLS);
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
                ID_WRONG_SYMBOLS);
    }

    @TmsLink("5945073")
    @Issue("A2N-492")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Password recovery by a user who did not change their password on first login, positive test")
    public void enterValidPassportWithoutChangingFirstPassword() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(EM79_VAL_PASS_2NUMBERS.getUser().getPassport())
                .clickContinueButton();
        assertEquals(passwordRecovery.getErrorMessageAfterEnterWrongIdNum(),
                ID_WITHOUT_CHANGING_PASSWORD);
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
                FIELD_SHOULD_CONTAIN_FOUR_NUMBERS);
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
                CONFIRMATION_CODE_MUST_BE_FILLED);
    }

    @TmsLink("5949447")
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter invalid new password with less than 7 symbols and get the error message, negative test")
    public void enterInvalidNewPasswordLessThan7SymbolsTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInNewPasswordField(LESS_THAN_MIN_CHARS.getUser().getPassword())
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getErrorMessageNewPassword(), PASSWORD_LESS_7_SYMBOLS);
    }

    @TmsLinks(value = {@TmsLink("5949448"), @TmsLink("5949449"), @TmsLink("5949450"), @TmsLink("5949451")})
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter valid new passwords and check that aria-invalid equals false, positive test",
            dataProvider = "valid boundary values new password ui", dataProviderClass = DataProviderTests.class)
    public void enterValidNewPasswordTest(String newPassword) {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInNewPasswordField(newPassword)
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getAttributeStatusAfterEnterPasswordInNewPassword("aria-invalid"), "false");
    }

    @TmsLink("5949452 ")
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter more than 20 characters in new password field and get error message, negative test")
    public void enterInvalidPasswordMoreThan20SymbolsTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInNewPasswordField(MORE_20_CHARS.getUser().getPassword())
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getErrorMessageNewPassword(), PASSWORD_MORE_20_SYMBOLS);
    }

    @TmsLink("5949453")
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter empty new password and get error message, negative test")
    public void enterInvalidEmptyPasswordTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInNewPasswordField(EMPTY_USER_FIELDS.getUser().getPassword())
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getErrorMessageNewPassword(), EMPTY_PASSWORD_FIELD);
    }

    @TmsLinks(value = {@TmsLink("5949454"), @TmsLink("5949455"), @TmsLink("5949456"), @TmsLink("5949457")})
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter invalid new passwords and check error messages, negative test",
            dataProvider = "invalid values password ui", dataProviderClass = DataProviderTests.class)
    public void enterInvalidNewPasswordTest(String newPassword) {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInNewPasswordField(newPassword)
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getErrorMessageNewPassword(), FIELD_CONTAIN_LETTERS_NUMBER);
    }

    @TmsLink("5949525")
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter less than 7 symbols in confirm password fields, negative test")
    public void enterLessThan7SymbolsConfirmPasswordTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInConfirmPasswordField(LESS_THAN_MIN_CHARS.getUser().getPassword())
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getErrorMessageConfirmPassword(), PASSWORD_LESS_7_SYMBOLS);
    }

    @TmsLink("5949530")
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter more than 20 characters in confirm password field and get error message, negative test")
    public void enterInvalidConfirmPasswordMoreThan20SymbolsTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInConfirmPasswordField(MORE_20_CHARS.getUser().getPassword())
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getErrorMessageConfirmPassword(), PASSWORD_MORE_20_SYMBOLS);
}

    @TmsLink("5949531")
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter empty confirm password and get error message, negative test")
    public void enterInvalidEmptyConfirmPasswordTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInConfirmPasswordField(EMPTY_USER_FIELDS.getUser().getPassword())
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getErrorMessageConfirmPassword(), EMPTY_PASSWORD_FIELD);
    }

    @TmsLinks(value = {@TmsLink("5949532"), @TmsLink("5949533"), @TmsLink("5949534"), @TmsLink("5949535")})
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter invalid confirm passwords and check error messages, negative test",
            dataProvider = "invalid values password ui", dataProviderClass = DataProviderTests.class)
    public void enterInvalidConfirmPasswordTest(String newPassword) {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInConfirmPasswordField(newPassword)
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getErrorMessageConfirmPassword(), FIELD_CONTAIN_LETTERS_NUMBER);
    }

    @TmsLink("5949989")
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Enter different new and confirm password and get error message, negative test")
    public void enterDifferentNewAndConfirmPasswordTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInNewPasswordField(EM79_MIN_CHARS.getUser().getPassword())
                .enterPasswordInConfirmPasswordField(EM79_MAX_CHARS.getUser().getPassword())
                .clickContinueButtonNewPassword();
        assertEquals(passwordRecovery.getErrorMessageConfirmPasswordNotMuch(), PASSWORDS_MUST_MATCH);
    }

    @TmsLink("5949994")
    @Issue("A2N-567")
    @Story("UC-1.3 Password recovery")
    @Test(description = "Click the eye button to viewing entered password, positive test")
    public void clickEyeButtonToViewingEnteringNewPasswordTest() {
        loginPage.clickLinkForgotPassword()
                .enterIdNumber(PASSPORT_REG)
                .clickContinueButton();
        passwordRecovery.enterSmsCodeConfirmation(SMS_VALID.getSms())
                .clickContinueButtonAfterEnteringSms()
                .enterPasswordInConfirmPasswordField(USER_0NE.getUser().getPassword())
                .clickEyeButtonConfirmPassword();
        assertEquals(passwordRecovery.getAttributeStatusAfterEnterPasswordInConfirmPassword("type"), "text");
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

