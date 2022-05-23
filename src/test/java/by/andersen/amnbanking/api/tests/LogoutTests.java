package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.TestRails;
import jsonBody.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.DataUrls.*;


public class LogoutTests extends BaseTest {

    @TestRails(id = "C5893156")
    @Test
    public void logoutActiveSessionTest() {
        new PostAdapters().postAuthWithSessionCode("1234");
        Response body = new GetAdapters().get(API_HOST + API_LOGOUT).as(Response.class);
        Assert.assertEquals(body.getMessage(), "Logged out successfully");
    }


    @TestRails(id = "?")
    @Test
    public void logoutWithNoActiveTokenTest() {
        new PostAdapters().postAuthWithSessionCode("1235");
        Response body = new GetAdapters().get(API_HOST + API_LOGOUT).as(Response.class);
        Assert.assertEquals(body.getMessage(), "No active session");
    }
}
