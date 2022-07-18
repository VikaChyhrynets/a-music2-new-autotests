package by.andersen.amnbanking.utils;

import org.testng.annotations.DataProvider;

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
}

