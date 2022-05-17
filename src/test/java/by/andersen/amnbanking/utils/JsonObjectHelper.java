package by.andersen.amnbanking.utils;

import org.json.simple.JSONObject;

public class JsonObjectHelper {

    private static final JSONObject jsonObject = new JSONObject();

    public static String setJsonObjectForRegistration(String login){
        jsonObject.put("login", login);
        jsonObject.put("password", "tPvpXJGRqAtbWN8I");

        return jsonObject.toJSONString();
    }
}