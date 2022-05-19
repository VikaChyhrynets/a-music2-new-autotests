package by.andersen.amnbanking.data;

import by.andersen.amnbanking.utils.PropertyHelper;

public class DataUrls {
    public final static String API_URL = PropertyHelper.getProperty("api.url");
    public static final String API_HOST = PropertyHelper.getProperty("api.host");
    public static final String API_LOGIN = PropertyHelper.getProperty("api.login");
    public static final String API_SESSIONCODE = PropertyHelper.getProperty("api.session_code");
    public static final String USER_LOGIN = PropertyHelper.getProperty("api.testLogin");
    public static final String USER_PASS = PropertyHelper.getProperty("api.testPass");
    public static final String USER_BAN_LOGIN = PropertyHelper.getProperty("api.testBanLogin");
    public static final String USER_BAN_PASS = PropertyHelper.getProperty("api.testBanPass");
    public static final String USER_WRONG_PASS = PropertyHelper.getProperty("api.testWrongPass");
    public static final String NOT_REGISTERED_USER_LOGIN = PropertyHelper.getProperty("api.testNotRegisteredLogin");

}
