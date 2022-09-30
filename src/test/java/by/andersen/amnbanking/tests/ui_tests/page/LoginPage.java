package by.andersen.amnbanking.tests.ui_tests.page;


import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

@Data
public class LoginPage extends BasePage {
    private SelenideElement loginField = $(By.id(":r1:"));
    private SelenideElement passwordField = $(By.id(":r3:"));
    private SelenideElement invalidPassword = $(By.xpath("//div[contains(@class, 'ggtXb')]"));
    private SelenideElement loginAlert = $(By.xpath("//div[contains(@class, 'formInputLogin')]//*[contains(@class, 'formError')]"));
    private SelenideElement passwordAlert = $(By.xpath("//*[contains(@class, 'formInputPassword')]//div[contains(@class, 'formError')]"));
    private SelenideElement loginButton = $(By.xpath("//*[contains(@class, 'formButtonLogin')]//button[contains(@type, 'submit')]"));
    private SelenideElement checkBoxShowPassword = $(By.xpath("//div[contains(@class, 'showPassword')]/*"));
    private SelenideElement loginErrorMessage = $(By.xpath("//div[contains(@class,'Login_formError')]"));
    private SelenideElement linkForgotPassword = $(By.xpath("//button[contains(@class,'X2kY6')]"));
    private SelenideElement contactsButton = $(By.xpath("//span[text()= 'Contacts']"));
    private SelenideElement loginWindow = $(By.xpath("//*[contains(@class, 'Login_form__nVOQc MuiBox-root css-0')]"));

}

