package by.andersen.amnbanking.data;

public enum AlertAPI {
    LOGIN_SUCCESS("Access granted"),
    INVALID_USERNAME_OR_PASSWORD("Invalid characters in UserName or Password"),
    NOT_REGISTERED_USER("This user is not registered yet"),
    BAN_USER("Ban time is not over yet..."),
    REGISTRATION_SUCCESS_USER("User with login: 7qqUqJm00LANA added"),
    REGISTRATION_FAILED_USER("Invalid characters in UserName or Password");

    String value;

    AlertAPI(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
