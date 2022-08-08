package by.andersen.amnbanking.data;

public class Alert {
    public static final String EMPTY_FIELDS = "Field must be filled";
    public static final String LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS = "Field should contain at least 7 symbols";
    public static final String LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS = "Must be 20 characters or less";
    public static final String FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS = "Field should contain letters at least" +
            " 1 capital and 1 lowercase letter";
    public static final String FIELD_CONTAIN_LETTERS_NUMBER = "Field should contain letters, 1 number";
    public static final String FIELD_SHOULD_CONTAIN_FOUR_NUMBERS = "Field should contain 4 numbers";
    public static final String CONFIRMATION_CODE_MUST_BE_FILLED = "The confirmation code field must be filled";
    public static final String ID_LESS_THAN_2_SYMBOLS = "Field should contain at least 2 symbols";
    public static final String ID_MORE_30_SYMBOLS = "Must be 30 characters or less";
    public static final String ID_WITHOUT_CHANGING_PASSWORD = "The one-time password cannot be changed via password " +
            "recovery. Please contact the bank in case of problems with the one-time password";
    public static final String INCORRECT_SMS_CODE = "You have entered an incorrect SMS code";
    public static final String MESSAGE_INCORRECT_SMS_3_TIMES = "You have entered an incorrect SMS code three times";
    public static final String SEND_CODE_AGAIN = "Send code again in";
    public static final String UNREGISTERED_ID = "This ID number is not registered. Please check the entered data or " +
            "contact the bank";
    public static final String ID_WRONG_SYMBOLS = "The field must contain only capital letters and numbers";
    public static final String SEND_SMS_POSITIVE = "We sent an SMS with a 4-digit verification code to +90237467824742";
    public static final String PASSWORD_LESS_7_SYMBOLS = "Password should contain at least 7 symbols";
    public static final String PASSWORD_MORE_20_SYMBOLS = "Must be 20 characters or less";
    public static final String EMPTY_PASSWORD_FIELD = "Field \"Password\" must be filled";
    public static final String PASSWORDS_MUST_MATCH = "Passwords must match";
}
