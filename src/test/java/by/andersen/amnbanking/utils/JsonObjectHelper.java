package by.andersen.amnbanking.utils;

import org.json.simple.JSONObject;

public class JsonObjectHelper {

    private static final JSONObject jsonObject = new JSONObject();

    public static String setJsonObjectForRegistrationAndLogin(String login, String password){
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        return jsonObject.toJSONString();
    }

    public static String setNewPassword(String password){
        jsonObject.put("newPassword", password);

        return jsonObject.toJSONString();
    }

    public static String setFilterType(String type){
        jsonObject.put("smsFilterType", type);

        return jsonObject.toJSONString();
    }

    public static String setPassportLoginPasswordForRegistration(String login, String password, String passport){
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("passport", passport);

        return jsonObject.toJSONString();
    }

    public static String setPassportForRegistration(String passport){
        jsonObject.put("passport", passport);

        return jsonObject.toJSONString();
    }

    public static String setSmsCode(String sms){
        jsonObject.put("smsCode", sms);

        return jsonObject.toJSONString();
    }

    public static String setIDForPassRecovery(String passport){
        jsonObject.put("passport", passport);

        return jsonObject.toJSONString();
    }

    public static String setPassword(String password){
        jsonObject.put("newPassword", password);

        return jsonObject.toJSONString();
    }
}
