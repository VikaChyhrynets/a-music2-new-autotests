package by.andersen.amnbanking.utils;

import jsonBody.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParserJson {
    static String message;

    public String parser(Response response,  String key) {
        try {
            String jsonBody = response.getMessage();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonBody);
            message = (String) jsonObject.get(key);
            System.out.println(message);

        } catch (ParseException e) {
            e.printStackTrace();
        } return message;
    }
}
