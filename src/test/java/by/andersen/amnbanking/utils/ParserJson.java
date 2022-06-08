package by.andersen.amnbanking.utils;

import jsonBody.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ParserJson {

    public static String parser(String key, Response response) throws ParseException{
        String responseJson = response.getMessage();
        JSONObject json = (JSONObject) new JSONParser().parse(responseJson);
        JSONObject message = ((JSONObject) json.get("message"));

        return message.get(key).toString();
    }
}
