package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.api.tests.adapters_steps.LoginAdapter;
import by.andersen.amnbanking.api.tests.objects.Login;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static by.andersen.amnbanking.data.DataUrls.*;

public class AuthorizationAndVerificationTests {

    private SoftAssert sAssert = new SoftAssert();

    @TestRails(id = "C5888309")
    @Test
    public void LoginPositive() {
        Login login = new LoginAdapter().postAsClass(API_HOST + API_LOGIN,
                JsonObjectHelper.setJsonObjectForLogin("111111111111111222", "11111111111111Q")).as(Login.class);
        sAssert.assertEquals(login.getMessage(), "Access granted");
    }
}
