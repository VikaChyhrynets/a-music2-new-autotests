package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import by.andersen.amnbanking.utils.ParserJson;
import by.andersen.amnbanking.utils.TestRails;
import org.testng.Assert;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.api.tests.AuthorizationAndVerificationTests.authKey;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGOUT;
import static by.andersen.amnbanking.data.FilesPath.logoutNoActiveToken;


public class LogoutTests {

    @TestRails(id = "C5893156")
    @Test
    public void logoutActiveSessionTest() {

    }

    @TestRails(id="?")
    @Test
    public void logoutWithNoActiveTokenTest() {
        new GetAdapters().get(authKey, "1234",API_HOST + API_LOGOUT).asString();
        Assert.assertEquals("No active session", new ParserJson().parser(logoutNoActiveToken, "message"));
    }
}
