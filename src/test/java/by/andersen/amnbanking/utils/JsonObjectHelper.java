package by.andersen.amnbanking.utils;

import org.json.simple.JSONObject;

public class JsonObjectHelper {

    private static final JSONObject jsonObject = new JSONObject();

    public static String setJsonObjectForRegistrationAndLogin(String login, String password){
        jsonObject.clear();
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        return jsonObject.toJSONString();
    }

    public String setJsonObjectForRegistrationWithPhone(String login, String password, String phone){
        jsonObject.clear();
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("phone", phone);

        return jsonObject.toJSONString();
    }

    public String setJsonObjectForRegistrationWithPassport(String login, String password, String passport){
        jsonObject.clear();
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("passport", passport);

        return jsonObject.toJSONString();
    }

    public static String setNewPassword(String password){
        jsonObject.clear();
        jsonObject.put("newPassword", password);

        return jsonObject.toJSONString();
    }

    public static String setFilterType(String type){
        jsonObject.clear();
        jsonObject.put("smsFilterType", type);

        return jsonObject.toJSONString();
    }

    public static String setPassportLoginPasswordForRegistration(String login, String password, String passport,
                                                                 String phoneNumber, String firstName, String lastName){
        jsonObject.clear();
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("passport", passport);
        jsonObject.put("phone", phoneNumber);
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);

        return jsonObject.toJSONString();
    }


    public static String setPassportForRegistration(String passport){
        jsonObject.clear();
        jsonObject.put("passport", passport);

        return jsonObject.toJSONString();
    }

    public static String setSmsCode(String sms){
        jsonObject.clear();
        jsonObject.put("smsCode", sms);

        return jsonObject.toJSONString();
    }

    public static String setIDForPassRecovery(String passport){
        jsonObject.clear();
        jsonObject.put("passport", passport);

        return jsonObject.toJSONString();
    }

    public static String setPassword(String password){
        jsonObject.clear();
        jsonObject.put("newPassword", password);

        return jsonObject.toJSONString();
    }
}
