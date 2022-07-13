package by.andersen.amnbanking.data;

public enum AlertAPI {
    LOGIN_SUCCESS("Access granted"),
    INVALID_USERNAME_OR_PASSWORD("Invalid characters in UserName or Password"),
    NOT_REGISTERED_USER("This user is not registered yet"),
    BAN_USER("Ban time is not over yet..."),
    REGISTRATION_SUCCESS_USER("User with login: Eminem79 added"),
    REGISTRATION_FAILED_USER("Invalid characters in UserName or Password"),
    REGISTRATION_FAILED_USER_PASSPORT("Invalid characters in passport"),
    REGISTRATION_FAILED_USER_PASSWORD("Invalid characters in password"),
    REGISTRATION_FAILED_USER_PHONE("Invalid characters in phone number"),
    INVALID_SMS("Invalid sms code"),
    INVALID_CHARACTERS("Invalid characters"),
    INVALID_SMS_FILTER("Invalid smsFilterType provided SMS_FOR_CHANGE_PASSPORT"),
    SUCCESSFUL_PASSWORD_CHANGED("Password changed successfully! Please login again"),
    REQUIRED_PASSWORD("Required to change password on first login"),
    LOGOUT_SUCCESSFULLY("Logged out successfully"),
    NO_ACTIVE_SESSION("No active session"),
    SESSION_CODE_CORRECT("Session code is correct"),
    ALREADY_REG_PHONE("User with phone = +375555555555 already registered"),
    ALREADY_REG_LOGIN("User with login = 11122222222222233333 already registered"),
    PASSPORT_NULL("Passport cannot be null"),
    PHONE_NULL("Phone cannot be null"),
    SMS_CODE_INVALID("Sms code contains invalid characters"),
    CHANGE_PASSWORD_CODE_CORRECT("Change password code is correct"),
    USER_NOT_VERIFIED("User has not been verified yet"),
    FAILED_CHANGE_PASSWORD_FROM_BANK("The one-time password cannot be changed via password recovery. " +
            "Please contact the bank in case of problems with the one-time password"),
    NOT_REGISTER_ID("This ID number is not registered. Please check the entered data or contact the bank"),
    PASSPORT_IS_VALID("Passport is valid. Sms sent successfully"),
    SMS_SENT_SUCCESSFULLY("Sms sent successfully");

    String value;

    AlertAPI(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
