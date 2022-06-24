package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGOUT;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;


public class LogoutTests extends BaseTest {

    static String authKey = getAuthToken("Maleficent1", "Number1");

    @TestRails(id = "C5893156")
    @Step("Logout from active session, positive test")
    @Test(description = "positive test, logout from active session")
    public void logoutActiveSessionTest() {
        new PostAdapters().post(setSmsCode("1234"),API_HOST + API_SESSIONCODE, authKey, 200);
        Response body = new GetAdapters().get(API_HOST + API_LOGOUT, authKey, 200).as(Response.class);
        Assert.assertEquals(body.getMessage(), "Logged out successfully");
    }


    @TestRails(id = "?")
    @Step("Logout with wrong sms code, negative test")
    @Test(description = "negative test, logout from active session with wrong sms code")
    public void logoutWithNoActiveTokenTest() {
        new PostAdapters().post(setSmsCode("1235"),API_HOST + API_SESSIONCODE, authKey, 400);
        Response body = new GetAdapters().get(API_HOST + API_LOGOUT, authKey, 400).as(Response.class);
        Assert.assertEquals(body.getMessage(), "No active session");
    }
}
