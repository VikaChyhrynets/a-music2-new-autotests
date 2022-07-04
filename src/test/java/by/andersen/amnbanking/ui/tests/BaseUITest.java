package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.ui.pages.ConfirmationCodeModalPage;
import by.andersen.amnbanking.ui.pages.LoginPage;
import by.andersen.amnbanking.ui.pages.MainPage;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static by.andersen.amnbanking.utils.PropertyHelper.getProperty;
import static com.codeborne.selenide.Selenide.open;

public abstract class BaseUITest {

    protected LoginPage loginPage = new LoginPage();
    protected ConfirmationCodeModalPage confirmationCodeModalPage = new ConfirmationCodeModalPage();

    @BeforeMethod
    public void setUp() {
        loginPage.open();
    }

    @BeforeClass
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
    }

    @BeforeSuite
    public void navigateToLoginPage() {
        Configuration.startMaximized = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
}