package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.model.Response;
import by.andersen.amnbanking.data.SmsVerificationData;
import by.andersen.amnbanking.data.UsersData;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AlertAPI.LOGOUT_SUCCESSFULLY;
import static by.andersen.amnbanking.data.AlertAPI.NO_ACTIVE_SESSION;
import static by.andersen.amnbanking.data.AuthToken.loginAndGetBearerToken;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGOUT;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PRECONDITION_FAILED;

@Epic("Epic 1: Registration and authorization")
public class LogoutTests extends BaseAPITest {

    @TmsLink("5893156")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "positive test, logout from active session")
    public void logoutActiveSessionTest() {
        String authToken = loginAndGetBearerToken(UsersData.USER_MALEFICENT.getUser().getLogin(),
                UsersData.USER_MALEFICENT.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_VALID.getValue()), API_HOST + API_SESSIONCODE,
                authToken, SC_OK);
        Response body = new GetAdapters().get(API_HOST + API_LOGOUT, authToken, SC_OK).as(Response.class);
        Assert.assertEquals(body.getMessage(), LOGOUT_SUCCESSFULLY);
    }


    @Story("UC-1.4 Registration (first login)")
    @Test(description = "negative test, logout from active session with wrong sms code")
    public void logoutWithNoActiveTokenTest() {
        String authToken = loginAndGetBearerToken(UsersData.USER_MALEFICENT.getUser().getLogin(),
                UsersData.USER_MALEFICENT.getUser().getPassword());
        new PostAdapters().post(setSmsCode(SmsVerificationData.SMS_INVALID.getValue()),
                API_HOST + API_SESSIONCODE,
                authToken, SC_BAD_REQUEST);
        Response body = new GetAdapters().get(API_HOST + API_LOGOUT, authToken,
                SC_PRECONDITION_FAILED).as(Response.class);
        Assert.assertEquals(body.getMessage(), NO_ACTIVE_SESSION);
    }
}
