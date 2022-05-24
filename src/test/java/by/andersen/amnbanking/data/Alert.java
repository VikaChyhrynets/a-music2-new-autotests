package by.andersen.amnbanking.data;

public enum Alert {
    EMPTY_LOGIN_OR_PASSWORD_FIELDS("Field must be filled"),
    LESS_7_SYMBOL_LOGIN_OR_PASSWORD_FIELDS("Field should contain at least 7 symbols"),
    LOGIN_OR_PASSWORD_FIELDS_MORE_TWENTY_SYMBOLS("Must be 20 characters or less"),
    FORBIDDEN_CHARACTERS_LOGIN_OR_PASSWORD_FIELDS("Field should contain letters, 1 capital letter, 1 number");
    String value;

    Alert(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
