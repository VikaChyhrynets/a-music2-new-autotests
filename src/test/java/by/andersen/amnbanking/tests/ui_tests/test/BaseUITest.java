package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.tests.base_test.BaseTest;
import by.andersen.amnbanking.tests.ui_tests.page.ConfirmationCodeModalPage;
import by.andersen.amnbanking.tests.ui_tests.page.LoginPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public abstract class BaseUITest extends BaseTest {

    protected LoginPage loginPage = new LoginPage();
    protected ConfirmationCodeModalPage confirmationCodeModalPage = new ConfirmationCodeModalPage();

    @BeforeMethod
    public void setUp() {
        loginPage.open();
    }

    @BeforeClass
    public void navigateToLoginPage() {
        Configuration.startMaximized = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
}