package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.api.tests.adapters.GetAdapters;
import by.andersen.amnbanking.utils.ParserJson;
import by.andersen.amnbanking.utils.PropertyHelper;
import by.andersen.amnbanking.utils.TestRails;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LogoutTests {

    @TestRails(id="C5893156")
    @Test
    public void logoutWithInvalidTokenDateTest() {
        new GetAdapters().get(PropertyHelper.getProperty("api.host") +
                        PropertyHelper.getProperty("api.logout")).asString();
        Assert.assertEquals("message", new ParserJson().parser("src/test/java/jsonBody/logoutInvalidToken.json", "message"));
    }
}
