package by.andersen.amnbanking.ui.tests;

import by.andersen.amnbanking.data.User;
import by.andersen.amnbanking.ui.pages.LoginPage;
import by.andersen.amnbanking.ui.pages.MainPage;
import by.andersen.amnbanking.utils.TestRails;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;

public class ExampleLoginTest extends BaseTest {
    //TODO: change with actual data once it created
    private final User user = new User.UserBuilder("11111", "11111", "11111").build();

    LoginPage loginPage = new LoginPage();

//    @BeforeMethod
//    public void setUp() {
//        loginPage.open();
//    }

//    @TestRails(id = "123")
//    @Test
//    public void weCanSuccessLoginWithValidCredentials() {
//        loginPage.doLogin(user);
//
//        webdriver().shouldHave(url(MainPage.URL));
//    }
}
