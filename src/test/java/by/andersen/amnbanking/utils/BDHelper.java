package by.andersen.amnbanking.utils;


public class BDHelper {
    String query;

    public String setRequestForDeleteUser() {
        query = "DELETE FROM users WHERE login = '7qqUqJm00LANA'";
        return query;
    }

    public String getUserFormBD() {
        query = "SELECT login FROM users LIMIT 1";
        return query;
    }
}