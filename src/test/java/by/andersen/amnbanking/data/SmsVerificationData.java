package by.andersen.amnbanking.data;

public enum SmsVerificationData {
   SMS_VALID("1234"),
   SMS_INVALID("1235");

    String value;

    SmsVerificationData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
