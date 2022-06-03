package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.TestRails;
import jsonBody.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.data.DataUrls.CHECK_PASSPORT;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setIDForPassRecovery;
import static org.testng.Assert.assertEquals;

public class PasswordRecoveryTests {

    @BeforeClass
    public void setUp(){
    }

    @Test
    @TestRails(id = "5908792")
    public void sendValidPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Passport is valid. Sms sent successfully");
    }

    @Test
    @TestRails(id = "5909076")
    public void sendUnregisteredPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "1111"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "This ID number is not registered. Please check the entered data or contact the bank");
    }

    @Test
    @TestRails(id = "5908983")
    public void sendEmptyPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(""), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "5909034")
    public void sendPassportWithMoreThenThirtySymbols(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "324567898765434567865432456"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "5909068")
    public void sendPassportWithSymbols(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "*"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "5909070")
    public void sendPassportWithLowerCase(){
        Response resp = new PostAdapters().post(setIDForPassRecovery("kv24535756"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }
}
