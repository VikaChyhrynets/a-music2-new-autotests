package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.adapters.GetAdapters;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.UsersData;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import jsonBody.Response;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import static by.andersen.amnbanking.data.AuthToken.getAuthToken;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_LOGOUT;
import static by.andersen.amnbanking.data.DataUrls.API_SESSIONCODE;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setSmsCode;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_PRECONDITION_FAILED;

@Epic("Epic 1: Registration and authorization")
public class LogoutTests extends BaseAPITest {

    static String authKey = getAuthToken(UsersData.USER_MALEFICENT.getUser().getLogin(),
            UsersData.USER_MALEFICENT.getUser().getPassword());

    @TmsLink("5893156")
    @Story("UC-1.4 Registration (first login)")
    @Test(description = "positive test, logout from active session")
    public void logoutActiveSessionTest() {
        new PostAdapters().post(setSmsCode("1234"),API_HOST + API_SESSIONCODE, authKey, SC_OK);
        Response body = new GetAdapters().get(API_HOST + API_LOGOUT, authKey, SC_OK).as(Response.class);
        Assert.assertEquals(body.getMessage(), "Logged out successfully");
    }


    @Story("UC-1.4 Registration (first login)")
    @Test(description = "negative test, logout from active session with wrong sms code")
    public void logoutWithNoActiveTokenTest() {
        new PostAdapters().post(setSmsCode("1235"),API_HOST + API_SESSIONCODE, authKey, SC_BAD_REQUEST);
        Response body = new GetAdapters().get(API_HOST + API_LOGOUT, authKey, SC_PRECONDITION_FAILED).as(Response.class);
        Assert.assertEquals(body.getMessage(), "No active session");
    }
}
