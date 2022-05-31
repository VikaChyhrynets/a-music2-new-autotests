package by.andersen.amnbanking.utils;


public class BDHelper {
    String query;

    public String setRequestForDeleteUser(String login) {
        query = "DELETE FROM users WHERE login = '"+ login +"'";
        return query;
    }

    public String getUserFormBD() {
        query = "SELECT login FROM users LIMIT 1";
        return query;
    }
}