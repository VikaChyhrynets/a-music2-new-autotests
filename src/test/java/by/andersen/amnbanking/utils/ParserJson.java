package by.andersen.amnbanking.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ParserJson {
    static String message;

    public static String parser(String body, String key) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
            message = (String) jsonObject.get(key);
            System.out.println(message);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return message;
    }
}