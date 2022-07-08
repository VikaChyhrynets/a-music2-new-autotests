package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.api.tests.CreateDeleteUser;
import by.andersen.amnbanking.api.tests.BaseAPITest;
import by.andersen.amnbanking.ui.pages.ConfirmationCodeModalPage;
import by.andersen.amnbanking.ui.pages.LoginPage;
import by.andersen.amnbanking.ui.pages.MainPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.sql.SQLException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class BaseUITest implements CreateDeleteUser {

    protected LoginPage loginPage = new LoginPage();
    protected ConfirmationCodeModalPage confirmationCodeModalPage = new ConfirmationCodeModalPage();
    protected MainPage mainPage = new MainPage();
    protected BaseAPITest baseAPITest = new BaseAPITest();

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

    @Override
    public void deleteUser() throws SQLException {

    }

    @Override
    public void createUser() {

    }
}
