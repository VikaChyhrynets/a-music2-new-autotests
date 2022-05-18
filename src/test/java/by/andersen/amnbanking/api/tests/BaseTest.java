package by.andersen.amnbanking.api.tests;


import static by.andersen.amnbanking.data.AuthToken.getAuthToken;


public class BaseTest {

  public static String authKey;

    public void  getAuthTokenWeb() {
        String authKey = getAuthToken();
        System.out.println(authKey);
    }
}
