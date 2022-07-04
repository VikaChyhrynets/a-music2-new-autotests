package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.ui.pages.ConfirmationCodeModalPage;
import by.andersen.amnbanking.ui.pages.LoginPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public abstract class BaseUITest {

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