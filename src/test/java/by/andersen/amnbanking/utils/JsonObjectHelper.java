package by.andersen.amnbanking.utils;

import org.json.simple.JSONObject;

public class JsonObjectHelper {

    private static final JSONObject jsonObject = new JSONObject();

    public static String setJsonObjectForRegistrationAndLogin(String login, String password){
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        return jsonObject.toJSONString();
    }
}
