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

public abstract class BaseUITest {

    LoginPage loginPage = new LoginPage();
    ConfirmationCodeModalPage confirmationCodeModalPage = new ConfirmationCodeModalPage();
    MainPage mainPage = new MainPage();

    @BeforeMethod
    public void setUp() {
        loginPage.open();
    }

    @BeforeClass
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterSuite
    public void closeDriver() {getWebDriver().quit();}
}
