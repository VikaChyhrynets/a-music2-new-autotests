package by.andersen.amnbanking.data;

public enum WrongUserData {
    LOGIN_OR_PASSWORD_LESS_THAN_7_CHARACTERS("Some1"),
    LOGIN_0R_PASSWORD_MORE_THAN_20_CHARACTERS("SomePassWORd1SomePass"),
    LOGIN_OR_PASSWORD_ONLY_LETTERS("SomePassWORd"),
    LOGIN_OR_PASSWORD_FIELD_IS_EMPTY(""),
    PASSWORD_ONLY_NUMBERS("111234569"),
    LOGIN_WITHOUT_CAPITAL_LETTER("userfortest111"),
    LOGIN_ONLY_NUMBERS_WITHOUT_111_AT_THE_BEGINNING("115457821");

    private String wrongData;

    WrongUserData(String wrongData) {
        this.wrongData = wrongData;
    }

    public String getWrongData (){return wrongData;}
}