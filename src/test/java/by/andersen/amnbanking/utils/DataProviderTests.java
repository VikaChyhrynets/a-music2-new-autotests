package by.andersen.amnbanking.utils;

import org.testng.annotations.DataProvider;

import static by.andersen.amnbanking.data.DataUrls.PASSPORT_REG;
import static by.andersen.amnbanking.data.SmsVerificationData.*;
import static by.andersen.amnbanking.data.UsersData.EM79_MIN_CHARS;

public class DataProviderTests {

    @DataProvider(name = "User's registration with invalid passport field data")
    public static Object[][] inputForUserRegistrationWithInValidPassportField() {
        return new Object[][]{
                {"11236489235FR456871232."},
                {"мн88954232156"},
                {"КВ88954232156"},
                {"MN88954232156 "},
                {"mn88954232156"},
                {"P"},
                {"PL45873698710254D78519F12547862"}
        };
    }

    @DataProvider(name = "User's registration with invalid login field data")
    public static Object[][] inputForUserRegistrationWithInValidLoginField() {
        return new Object[][]{
                {"22233333333333333333"},
                {"Eminem79а"},
                {"111впрТЫ"},
                {"111CDV"},
                {"111111111111111111111Pv"},
                {" 111*?/)! ;"},
                {""}
        };
    }

    @DataProvider(name = "User's registration with invalid password field data")
    public static Object[][] inputForUserRegistrationWithInValidPasswordField() {
        return new Object[][]{
                {"11122222222222233333"},
                {"1111111111222222ваТЫ"},
                {"111111"},
                {"111111111111111111111"},
                {""},
                {"aaaaaaaaAA"}
        };
    }

    @DataProvider(name = "User's registration with invalid phone field data")
    public static Object[][] inputForUserRegistrationWithInValidPhoneField() {
        return new Object[][]{
                {"+375235555555 "},
                {"375235555555 "},
                {" +375235555555"},
                {"+375235555555."},
                {"+375235555555*"},
                {"+375235555555+"},
                {"+375235555555,"},
                {"+            "},
                {"             "},
                {"+2056487951"},
                {"+3620547894563201"},
                {"+mmmmmmmmmmm"},
                {"+LLLLLLLLLLLL"},
                {"+920215h456"}
        };
    }

    @DataProvider(name = "ChangePasswordAfter1LoginInvalidPass")
    public static Object[][] changePasswordAfter1LoginUserInvalidPass() {
        return new Object[][]{
                {"Num1"},
                {"NumLcndvS"},
                {"569102561"},
                {"5691Lvd."},
                {""},
                {"Number1 "}
        };
    }

    @DataProvider(name = "Forbidden symbols in Id number field")
    public static Object[][] InvalidIdNumberUiNegativeTest() {
        return new Object[][]{
                {"l7895G"},
                {"P.48654"},
                {"ж785F4"},
                {"ЧF78545"},
                {" PG452"},
                {"LM89 "}
        };
    }

    @DataProvider(name = "Valid ID number")
    public static Object[][] ValidIdNumberUiPositiveTest() {
        return new Object[][]{
                {"BF12334376763", "+90237467824742"},
                {"UT1234567891234567891234567891", "+51547599564785"},
                {"20PDCOLX3I406UWJKQ3GU8THHTZUO", "+21547599564785"},
                {"2C", "+11547599564785"}
        };
    }

    @DataProvider(name = "Invalid sms-code on 2 step password recovery")
    public static Object[][] invalidSmsCodeConfirmation2StepOnModalPagePasswordRecoveryTest() {
        return new Object[][]{
                {PASSPORT_REG, SMS_3_SYMBOLS.getSms()},
                {PASSPORT_REG, SMS_5_SYMBOLS.getSms()},
                {PASSPORT_REG, SMS_BEGIN_SPACE.getSms()},
                {PASSPORT_REG, SMS_SPACE_END.getSms()},
                {PASSPORT_REG, SMS_4_SPACES.getSms()},
                {PASSPORT_REG, SMS_WITH_LETTER.getSms()},
                {PASSPORT_REG, SMS_ASTERISK_PLUSES.getSms()}
        };
    }

    @DataProvider(name = "invalid login validation")
    public static Object[][] invalidLoginUIValidationTest() {
        return new Object[][]{
                {"Fklid7*@"},
                {"5Dvkfefnb~mk8ftt"},
                {"@5Dvkfefnbmk8ftt"},
                {"5Dvkf-efnbmk8ftt"},
                {"5Dvkfefnbmk8ftt/"},
                {"5Dvkfefnbmk8+ftt"},
                {"5Dv^kfefnbmk8ftt"},
                {"5Dvkfefnb«mkftt"},
                {"5*Dvkfefnbmk8ftt"},
                {"5Dvkfefnbm.k8ftt"},
                {"5Dvkfefnbm>k8ftt"},
                {"SELECT*FROM users"},
                {"5Dvkfefnbm?k8ftt"},
                {"5Dvkfefnbm&k8ftt"},
                {"5Dvkfefnbm#k8ftt"},
                {"5Dvkfefnbm]k8ftt"},
                {"5Dvkfefnbm k8ftt"},
                {"5Dvkfefnbm❞k8ftt"},
                {"5Dvkfefnbm$k8ftt"},
                {"_5Dvkfefnbmk8ftt"},
                {"5Dvkfefnbm'k8ftt"},
                {"5Dvkfefnbm=k8ftt"},
                {"5Dvkfefn:k8ftt"},
                {"5Dvkfefn;k8ftt"},
                {"5Dvkfefnbm!k8ftt"},
                {"5Dvkfefnbm←k8ftt"},
                {"А5Dvkfefbmk8ftt"},
                {"RTNZKCVF"}
        };
    }

    @DataProvider(name = "invalid password validation")
    public static Object[][] invalidPasswordUIValidationTest() {
        return new Object[][]{
                {"584841Cd2154ddvnvddvb"},
                {"584841Cd»nvddvb"},
                {"584841Cd❝nvddvb"}
        };
    }

    @DataProvider(name = "invalid password valid login validation")
    public static Object[][] invalidPasswordValidLoginUIValidationTest() {
        return new Object[][]{
                {"*12Ty354785"},
                {"!1235Xc4785"},
                {"-1Vc2354785"},
                {"@2354Lk785"},
                {"+23547Mn85"},
                {"З23Rb54785"},
                {"-235Xn4785"},
                {" 23Dv54785"},
                {"/23Dv54785"},
                {"2354,78Dc5"},
                {"<2354785Fv"},
                {"X∑235n4785"},
                {"SELECT*FROM users1"},
                {"235478Sa5?"},
                {"23Q4&7z85"},
                {"23Q4.c785"},
                {"23Q4:c785"},
                {"23Q4;c785"},
                {"23Q4c785%"},
                {"23Q4c785$"},
                {"#23Q4c785"},
                {"№23Q4c785"},
                {"23Q~4c785d"},
                {"23Q➝4c785d"},
                {"23Q¼4c785d"},
                {"23Q^4c785d"},
                {"I'l4584c785d"},
                {"584841Cd»nvddvb"},
                {"584841Cd❝nvddvb"}
        };
    }

    @DataProvider(name = "valid boundary values new password ui")
    public static Object[][] validBoundaryValuesPasswordUiPasswordRecovery() {
        return new Object[][]{
                {EM79_MIN_CHARS.getUser().getPassword()},
                {EM79_MIN_CHARS.getUser().getPassword()+"8"},
                {"78vhfytuiropgHklbgh"},
                {"78vhfytuiropgHklbghv"}
        };
    }

    @DataProvider(name = "invalid values password ui")
    public static Object[][] invalidPasswordUiPasswordRecovery() {
        return new Object[][]{
                {"lkdhfbhd"},
                {"12345678"},
                {"123.567k"},
                {"п123456l"}
        };
    }
}

