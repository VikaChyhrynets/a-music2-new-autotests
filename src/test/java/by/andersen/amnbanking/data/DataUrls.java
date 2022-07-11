package by.andersen.amnbanking.data;

import by.andersen.amnbanking.utils.PropertyHelper;

public class DataUrls {
    public final static String API_URL = PropertyHelper.getProperty("api.url");
    public static final String API_HOST = PropertyHelper.getProperty("api.host");
    public static final String API_LOGIN = PropertyHelper.getProperty("api.login");
    public static final String API_SESSIONCODE = PropertyHelper.getProperty("api.session_code");
    public static final String API_REGISTRATION = PropertyHelper.getProperty("api.registration");
    public static final String API_BANK_INFO = PropertyHelper.getProperty("api.bankInfo");
    public static final String API_LOGOUT = PropertyHelper.getProperty("api.logout");
    public static final String USER_BAN_PASS = PropertyHelper.getProperty("api.testBanPass");
    public static final String USER_WRONG_PASS = PropertyHelper.getProperty("api.testWrongPass");
    public static final String NOT_REGISTERED_USER_LOGIN = PropertyHelper.getProperty("api.testNotRegisteredLogin");
    public static final String USER_SESSION_CODE_LOGIN = PropertyHelper.getProperty("api.testSessionCodeLogin");
    public static final String LOGIN_WITH_PASSPORT_REG = PropertyHelper.getProperty("api.loginPass");
    public static final String PASSWORD_WITH_PASSPORT_REG = PropertyHelper.getProperty("api.passwPass");
    public static final String PASSPORT_REG = PropertyHelper.getProperty("api.passport");
    public static final String SMS_CODE = PropertyHelper.getProperty("api.getSmsCode");
    public static final String FILTER_TYPES = PropertyHelper.getProperty("api.getSmsFilterTypes");
    public static final String API_FIRST_ENTRY = PropertyHelper.getProperty("api.firstEntry");
    public static final String CHANGE_PASSWORD = PropertyHelper.getProperty("api.changePassword");
    public static final String CHECK_PASSPORT = PropertyHelper.getProperty("api.checkPassport");
    public static final String NEW_PASSWORD = PropertyHelper.getProperty("api.newPassword");
    public static final String CHECK_SMS = PropertyHelper.getProperty("api.checkSms");
}
