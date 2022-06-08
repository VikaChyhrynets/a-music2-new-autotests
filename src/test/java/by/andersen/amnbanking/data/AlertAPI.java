package by.andersen.amnbanking.data;

public enum AlertAPI {
    LOGIN_SUCCESS("Access granted"),
    INVALID_USERNAME_OR_PASSWORD("Invalid characters in UserName or Password"),
    NOT_REGISTERED_USER("This user is not registered yet"),
    BAN_USER("Ban time is not over yet..."),
    REGISTRATION_SUCCESS_USER("User with login: Eminem78 added"),
    REGISTRATION_FAILED_USER("Invalid characters in UserName or Password"),
    REGISTRATION_FAILED_USER_PASSPORT("Invalid characters in passport"),
    REGISTRATION_FAILED_USER_PASSWORD("Invalid characters in password");

    String value;

    AlertAPI(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
