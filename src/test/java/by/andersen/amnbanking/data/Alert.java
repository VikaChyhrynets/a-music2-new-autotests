package by.andersen.amnbanking.data;

public enum Alert {
    EMPTY_FIELDS("Field must be filled"),
    EMPTY_LOGIN_OR_PASSWORD_FIELDS("Field must be filled"),
    LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS("Field should contain at least 7 symbols"),
    LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS("Must be 20 characters or less"),
    FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS("Field should contain letters, 1 capital letter, 1 number"),
    FIELD_CONTAIN_LETTERS_NUMBER("Field should contain letters, 1 number"),
    FIELD_SHOULD_CONTAIN_FOUR_NUMBERS("Field should contain 4 numbers"),
    CONFIRMATION_CODE_MUST_BE_FILLED("The confirmation code field must be filled"),
    ID_LESS_THAN_2_SYMBOLS("Field should contain at least 2 symbols"),
    ID_MORE_30_SYMBOLS("Must be 30 characters or less"),
    ID_WITHOUT_CHANGING_PASSWORD("The one-time password cannot be changed via password recovery. " +
            "Please contact the bank in case of problems with the one-time password"),
    INCORRECT_SMS_CODE("You have entered an incorrect SMS code"),
    MESSAGE_INCORRECT_SMS_3_TIMES("You have entered an incorrect SMS code three times"),
    SEND_CODE_AGAIN("Send code again in"),
    ID_WRONG_SYMBOLS("Field should contain capital letters and numbers");

    String value;

    Alert(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
