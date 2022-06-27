package by.andersen.amnbanking.data;

import by.andersen.amnbanking.adapters.PostAdapters;

import static by.andersen.amnbanking.data.AlertAPI.INVALID_USERNAME_OR_PASSWORD;
import static by.andersen.amnbanking.data.DataUrls.*;
import static by.andersen.amnbanking.utils.JsonObjectHelper.setJsonObjectForRegistrationAndLogin;
import static by.andersen.amnbanking.utils.ParserJson.parser;
import static org.testng.Assert.assertEquals;

public class DoLogin {
    public static void loginWithInvalidLoginWithSpecialCharacter(String specialCharacter) {
        assertEquals(getMessageFromResponse(USER_LOGIN + specialCharacter, USER_PASS),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    public static void loginWithInvalidPasswordWithSpecialCharacter(String specialCharacter) {
        assertEquals(getMessageFromResponse(USER_LOGIN, USER_PASS + specialCharacter),
                INVALID_USERNAME_OR_PASSWORD.getValue());
    }

    private static String getMessageFromResponse(String login, String password) {
        return parser(new PostAdapters()
                .post(setJsonObjectForRegistrationAndLogin(login, password),
                        API_HOST + API_LOGIN, 400).asString(), "message");
    }
}