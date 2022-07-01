package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.ui.pages.ConfirmationCodeModalPage;
import by.andersen.amnbanking.ui.pages.LoginPage;
import by.andersen.amnbanking.ui.pages.MainPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    LoginPage loginPage = new LoginPage();
    ConfirmationCodeModalPage confirmationCodeModalPage = new ConfirmationCodeModalPage();
    MainPage mainPage = new MainPage();


    @BeforeClass
    public static void setUpAll() {
        Configuration.startMaximized = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

}