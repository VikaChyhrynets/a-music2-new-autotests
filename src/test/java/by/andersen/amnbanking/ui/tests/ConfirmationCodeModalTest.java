package by.andersen.amnbanking.ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ConfirmationCodeModalTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        loginPage.open();
    }

    @Test(description = "Enter correct 7 symbols in the login and password fields, positive test")
    public void authWithValidDataForLoginAndPasswordFieldsWithSevenSymbols() {
        loginPage.inputLoginField("Maleficent1")
                .inputPasswordField("Number1")
                .clickLoginButton();
        Assert.assertEquals(confirmationCodeModalPage.confirmationCodeWindowIsOpen(), true);
    }
}
