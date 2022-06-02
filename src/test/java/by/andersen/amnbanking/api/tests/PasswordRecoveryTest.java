package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import jsonBody.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.DataUrls.PASSPORT_REG;
import static org.testng.Assert.assertEquals;

public class PasswordRecoveryTest {

    @BeforeClass
    public void setUp(){
    }

    @Test
    public void sendValidPassport(){
        Response resp = new PostAdapters().checkPassport(PASSPORT_REG).as(Response.class);
        assertEquals(resp.getMessage(), "Passport is valid. Sms sent successfully");
    }

    @Test
    public void sendUnregisteredPassport(){
        Response resp = new PostAdapters().checkPassport(PASSPORT_REG + "1111").as(Response.class);
        assertEquals(resp.getMessage(), "This ID number is not registered. Please check the entered data or contact the bank");
    }
}
