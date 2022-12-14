package by.andersen.amnbanking.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SmsVerificationData {
    SMS_VALID("1234"),
    SMS_INVALID("1235"),
    SMS_3_SYMBOLS("123"),
    SMS_5_SYMBOLS("12345"),
    EMPTY_SMS(""),
    SMS_SMALL_LETTERS("brab"),
    SMS_1_LETTER("123a"),
    SMS_ASTERISK_PLUSES("+++*"),
    SMS_AMPERSAND("123&"),
    SMS_SPACE_END("123 "),
    SMS_4_SPACES("    "),
    SMS_MIDDLE_SPACE("12 34"),
    SMS_BEGIN_SPACE(" 1234"),
    SMS_SLASH_MIDDLE("12/4"),
    SMS_SLASH_END("123/"),
    SMS_WITH_LETTER("123L");

    private final String sms;
}

