package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.utils.TestRails;
import io.restassured.http.Cookie;
import jsonBody.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AuthToken.checkPassportAndGetCookie;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.*;
import static org.testng.Assert.assertEquals;

public class PasswordRecoveryTests {

    @Test
    @TestRails(id = "C5908792")
    public void sendValidPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Passport is valid. Sms sent successfully");
    }

    @Test
    @TestRails(id = "C5909076")
    public void sendUnregisteredPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "1111"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "This ID number is not registered. Please check the entered data or contact the bank");
    }

    @Test
    @TestRails(id = "C5908983")
    public void sendEmptyPassport(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(""), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "C5909034")
    public void sendPassportWithMoreThenThirtySymbols(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "324567898765434567865432456"),
                API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "C5909068")
    public void sendPassportWithSymbols(){
        Response resp = new PostAdapters().post(setIDForPassRecovery(PASSPORT_REG + "*"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "C5909070")
    public void sendPassportWithLowerCase(){
        Response resp = new PostAdapters().post(setIDForPassRecovery("kv24535756"), API_HOST + CHANGE_PASSWORD + CHECK_PASSPORT).as(Response.class);
        assertEquals(resp.getMessage(), "Invalid characters");
    }

    @Test
    @TestRails(id = "C5911652")
    public void sendValidPassportConfirmedWithSms(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHECK_SMS, loginAsCookie).as(Response.class);
        assertEquals(resp.getMessage(), "Change password code is correct");
    }

    @Test
    @TestRails(id = "C5911654")
    public void sendSmsWithLessThenFourDigits(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("123"), API_HOST + CHECK_SMS, loginAsCookie).as(Response.class);
        assertEquals(resp.getMessage(), "Sms code contains invalid characters");
    }

    @Test
    @TestRails(id = "C5911746")
    public void sendSmsWithMoreThenFourDigits(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Response resp = new PostAdapters().post(setSmsCode("12345"), API_HOST + CHECK_SMS, loginAsCookie).as(Response.class);
        assertEquals(resp.getMessage(), "Sms code contains invalid characters");
    }

    @Test
    @TestRails(id = "C5923248")
    public void sendSmsWithCookieReplacement(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response resp = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHECK_SMS, replacedCookie).as(Response.class);
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }

    @Test
    @TestRails(id = "C5923249")
    public void changingPasswordWithCookieReplacement(){
        Cookie loginAsCookie = checkPassportAndGetCookie(PASSPORT_REG);
        Cookie replacedCookie = new Cookie.Builder(loginAsCookie.getName(), USER_SESSION_CODE_LOGIN).build();
        Response checkSms = new PostAdapters().post(setSmsCode("1234"), API_HOST + CHECK_SMS, loginAsCookie).as(Response.class);
        Response resp = new PostAdapters().post(setPassword(PASSWORD_WITH_PASSPORT_REG), API_HOST + NEW_PASSWORD,replacedCookie).as(Response.class);
        assertEquals(checkSms.getMessage(),"Change password code is correct");
        assertEquals(resp.getMessage(), "User has not been verified yet");
    }


}
