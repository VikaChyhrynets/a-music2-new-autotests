package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.ui.pages.ConfirmationCodeModalPage;
import by.andersen.amnbanking.ui.pages.LoginPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static by.andersen.amnbanking.utils.PropertyHelper.getProperty;
import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {

    protected LoginPage loginPage = new LoginPage();
    protected ConfirmationCodeModalPage confirmationCodeModalPage = new ConfirmationCodeModalPage();

    @BeforeSuite
    public void navigateToLoginPage() {
        Configuration.startMaximized = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeMethod
    public void refreshPage() {
        open(getProperty("start.url"));
    }
}