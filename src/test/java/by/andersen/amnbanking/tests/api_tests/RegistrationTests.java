package by.andersen.amnbanking.tests.api_tests;

import by.andersen.amnbanking.DBConnector.DBConnector;
import by.andersen.amnbanking.adapters.PostAdapters;
import by.andersen.amnbanking.data.AlertAPI;
import by.andersen.amnbanking.jsonBody.Response;
import by.andersen.amnbanking.utils.JsonObjectHelper;
import by.andersen.amnbanking.utils.TestRails;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.data.DataUrls.API_REGISTRATION;

public class RegistrationTests {

    @Step("Deletes the created user after the test")
    public void deleteUserFromDB() throws SQLException {
        new DBConnector().deleteUser("Eminem78");
    }

    @TestRails(id = "C5888304")
    @Step("Registration new user with valid data, positive test")
    @Test(description = "Registration user with passport, valid date, positive test")
    public void registrationWithValidDateTest() throws SQLException {
        try {
            Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                            ("Eminem78", "tPvpXJGRqAtbWN8I", "125649846", "+71895555555"),
                    API_HOST + API_REGISTRATION, 200).as(Response.class);
            Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        } finally {
            deleteUserFromDB();
        }
    }

    @TestRails(id = "C5901579")
    @Step("Registration new user with valid data and BIG letters and numbers in passport field, positive test")
    @Test(description = "Registration, valid data, BIG letters and numbers in passport field, positive test")
    public void registrationValidDateWithPassportTest() throws SQLException {
        try {
            Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                            ("Eminem78", "8Rvjsio457c", "NX4536489235", "+863455555555"),
                    API_HOST + API_REGISTRATION, 200).as(Response.class);
            Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        } finally {
            deleteUserFromDB();
        }
    }

    @TestRails(id = "C5901827")
    @Step("Registration new user with valid data and only digits in passport field, positive test")
    @Test(description = "Registration, valid data, only digits in passport field, positive test")
    public void registrationValidDateWithOnlyDigitsInPassportTest() throws SQLException {
        try {
            Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                            ("Eminem78", "2Loc4567E", "112364486235", "+72355555555"),
                    API_HOST + API_REGISTRATION, 200).as(Response.class);
            Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        } finally {
            deleteUserFromDB();
        }
    }

    @TestRails(id = "C5901829")
    @Step("Registration new user with valid data and only two BIG letters in passport field, positive test")
    @Test(description = "Registration, valid data, only two BIG letters in passport field, positive test")
    public void registrationValidDateWithTwoLettersInPassportTest() throws SQLException {
        try {
            Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                            ("Eminem78", "2Loc4567E", "LK", "+8565555555555"),
                    API_HOST + API_REGISTRATION, 200).as(Response.class);
            Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        } finally {
            deleteUserFromDB();
        }
    }

    @TestRails(id = "C5901830")
    @Step("Registration new user with valid data and thirty symbols in passport field, positive test")
    @Test(description = "Registration, valid data, 30 symbols in passport field, positive test")
    public void registrationValidDateWithThirtyValidSymbolsInPassportTest() throws SQLException {
        try {
            Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                            ("Eminem78", "2Loc4567E", "11236489235FR456871230L78D9632", "+3459755555555"),
                    API_HOST + API_REGISTRATION, 200).as(Response.class);
            Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        } finally {
            deleteUserFromDB();
        }
    }

    @TestRails(id = "C5901579")
    @Step("Registration new user with valid data 12 chars in phone field, positive test")
    @Test(description = "Registration, valid data, 12 chars in phone field, positive test")
    public void registrationValidDateWithTwelveCharsPhoneTest() throws SQLException {
        try {
            Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                            ("Eminem78", "8Rvjsio457c", "NX4536489235", "+96023478512"),
                    API_HOST + API_REGISTRATION, 200).as(Response.class);
            Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        } finally {
            deleteUserFromDB();
        }
    }

    @TestRails(id = "C5901579")
    @Step("Registration new user with valid data 16 chars in phone field, positive test")
    @Test(description = "Registration, valid data, 16 chars in phone field, positive test")
    public void registrationValidDateWithSixteenCharsPhoneTest() throws SQLException {
        try {
            Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                            ("Eminem78", "8Rvjsio457c", "NX4536489235", "+960234785126325"),
                    API_HOST + API_REGISTRATION, 200).as(Response.class);
            Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_SUCCESS_USER.getValue());
        } finally {
            deleteUserFromDB();
        }
    }

    @TestRails(id = "C5901876")
    @Step("Registration new user with forbidden symbol . in passport field, negative test")
    @Test(description = "Registration, forbidden symbol . in passport field, negative test")
    public void registrationValidDateWithForbiddenSymbolInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "2Loc4567E", "11236489235FR456871232.", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id = "C5901831")
    @Step("Registration new user with small russian letters in passport field, negative test")
    @Test(description = "Registration, small russian letters in passport field, negative")
    public void registrationWithSmallRussianLettersInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "мн88954232156", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id = "C5901832")
    @Step("Registration new user with forbidden symbol . in passport field, negative test")
    @Test(description = "Registration, forbidden symbol . in passport field, negative test")
    public void registrationWithDuplicatePassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "AM4567", "+7523555555555"),
                API_HOST + API_REGISTRATION, 422).as(Response.class);
        Assert.assertEquals(response.getMessage(), "User with passport = AM4567 already registered");
    }

    @TestRails(id = "C5901848")
    @Step("Registration new user with big russian letters in passport field, negative test")
    @Test(description = "Registration, big russian letters in passport field, negative test")
    public void registrationWithBigRussianLettersInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "КВ88954232156", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id = "C5901849")
    @Step("Registration new user with space in passport field, negative test")
    @Test(description = "Registration, space in passport field, negative test")
    public void registrationWithSpaceInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "MN88954232156 ", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id = "C5901851")
    @Step("Registration new user with small letters in passport field, negative test")
    @Test(description = "Registration, small letters in passport field, negative test")
    public void registrationWithSmallLettersInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "mn88954232156", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id = "C5901853")
    @Step("Registration new user with only one big letter in passport field, negative test")
    @Test(description = "Registration, only 1 big letter in passport field, negative test")
    public void registrationWithOneLetterInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "P", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id = "C5901860")
    @Step("Registration new user with thirty-one symbols in passport field, negative test")
    @Test(description = "Registration, 31 symbols in passport field, negative test")
    public void registrationWithThirtyOneSymbolsInPassportTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("Eminem78", "8Scnjfvs56", "PL45873698710254D78519F12547862", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PASSPORT.getValue());
    }

    @TestRails(id = "C5895599")
    @Step("Registration new user with duplicate login field, negative test")
    @Test(description = "Registration, duplicate login field, negative test")
    public void registrationWithDuplicateLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333", "111111111122222222AA", "LP565482130", "+75555555555"),
                API_HOST + API_REGISTRATION, 422).as(Response.class);
        Assert.assertEquals(response.getMessage(),
                "User with login = 11122222222222233333 already registered");
    }

    @TestRails(id = "C5895603")
    @Step("Registration new user with only numbers login field, negative test")
    @Test(description = "Registration, only numbers login field, negative test")
    public void firstRegistrationWithOnlyNumbersLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("22233333333333333333", "111111111122222222AA", "8RIDNCKS457I", "+68495153205"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895605")
    @Step("Registration new user with invalid char in login field, negative test")
    @Test(description = "Registration, invalid char in login field, negative test")
    public void firstRegistrationWithInvalidCharLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("1113333333333333333a", "111111111122222222AA", "8RIDNCKS457I", "+68495153205"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895606")
    @Step("Registration new user with invalid cyrillic char login field, negative test")
    @Test(description = "Registration, invalid cyrillic char login field, negative test")
    public void firstRegistrationWithInvalidCyrillicLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("111впрТЫ", "111111111122222222AA", "8RIDNCKS457I", "+68495153205"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895612")
    @Step("Registration new user with less than 7 characters in login field, negative test")
    @Test(description = "Registration, less than 7 characters in login field, negative test")
    public void firstRegistrationWithInvalidLess7CharLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("111CDV", "LV11122222222AA", "8DNCKS457I", "+638495153205"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895614")
    @Step("Registration new user with more than 20 characters in login field, negative test")
    @Test(description = "Registration, more than 20 characters in login field, negative test")
    public void firstRegWithInvalidMore20CharLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("111111111111111111111", "111111111122222222AA", "8RIDNCKS457I", "+68495153205"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895629")
    @Step("Registration new user with special symbol in login field, negative test")
    @Test(description = "Registration, special symbol in login field, negative test")
    public void firstRegWithInvalidLoginSpecialSymbolTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        (" 111*?/)! ;", "111111111122222222AA", "8RIDNCKS457I", "+68495153205"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895638")
    @Step("Registration new user with empty login field, negative test")
    @Test(description = "Registration, empty login field, negative test")
    public void firstRegWithEmptyLoginTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("", "111111111122222222AA", "PH45421", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895776")
    @Step("Registration new user with only numbers in password field, negative test")
    @Test(description = "Registration, only numbers in password field, negative test")
    public void firstRegWithInvalidPasswordSpecialCharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333", "11122222222222233333", "PH45421", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895786")
    @Step("Registration new user with cyrillic chars in password field, negative test")
    @Test(description = "Registration, cyrillic chars in password field, negative test")
    public void firstRegWithInvalidPasswordCyrillicCharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333", "1111111111222222ваТЫ", "PH45421", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895791")
    @Step("Registration new user with less than 7 characters in password field, negative test")
    @Test(description = "Registration, less than 7 characters in password field, negative test")
    public void firstRegWithInvalidPasswordLess7CharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333", "111111", "PH45421", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895794")
    @Step("Registration new user with more than 20 characters in password field, negative test")
    @Test(description = "Registration, more than 20 characters in password field, negative test")
    public void firstRegWithInvalidPasswordMore20CharsTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333", "111111111111111111111", "PH45421", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895797")
    @Step("Registration new user with empty password field, negative test")
    @Test(description = "Registration, empty password field, negative test")
    public void firstRegWithEmptyPasswordTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333", "", "PH45421", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @TestRails(id = "C5895810")
    @Step("Registration new user with only letters in password field, negative test")
    @Test(description = "Registration, only letters in password field, negative test")
    public void firstRegWithInvalidPasswordOnlyLettersTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("11122222222222233333", "aaaaaaaaAA", "PH45421", "+75555555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER.getValue());
    }

    @Step("Registration new user with valid login, password, passport and duplicate phone number, negative test")
    @Test(description = "Registration with duplicate phone number, negative test")
    public void firstRegWithDuplicatePhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber2", "Vvjndf45sD78fd", "LMB54154862123", "+375555555555"),
                API_HOST + API_REGISTRATION, 422).as(Response.class);
        Assert.assertEquals(response.getMessage(), "User with phone = +375555555555 already registered");

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with space at the end, negative test")
    @Test(description = "Registration with invalid phone number with space at the ehd, negative test")
    public void firstRegWithSpaceAtTheEndPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+375235555555 "),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number without plus, negative test")
    @Test(description = "Registration with invalid phone number without plus, negative test")
    public void firstRegWithoutPlusPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "375235555555 "),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with space at the beginning, negative test")
    @Test(description = "Registration with invalid phone number with space at the beginning, negative test")
    public void firstRegWithSpaceAtBeginningPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", " +375235555555"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with dot, negative test")
    @Test(description = "Registration with invalid phone number with dot, negative test")
    public void firstRegWithDotPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+375235555555."),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with asterisk, negative test")
    @Test(description = "Registration with invalid phone number with asterisk, negative test")
    public void firstRegWithAsteriskPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+375235555555*"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with plus at the end, negative test")
    @Test(description = "Registration with invalid phone number with asterisk, negative test")
    public void firstRegWithPlusAtTheEndPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+375235555555+"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with comma at the end, negative test")
    @Test(description = "Registration with invalid phone number with comma at the end, negative test")
    public void firstRegWithCommaPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+375235555555,"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with plus and only spaces after, negative test")
    @Test(description = "Registration with invalid phone number with plus and only spaces after, negative test")
    public void firstRegWithPlusAndOnlySpacesAfterPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+            "),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with only spaces, negative test")
    @Test(description = "Registration with invalid phone number with only spaces, negative test")
    public void firstRegWithOnlySpacesPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "             "),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with 11 chars, negative test")
    @Test(description = "Registration with invalid phone number with less than 11 chars, negative test")
    public void firstRegWithElevenCharsPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+2056487951"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with 17 chars, negative test")
    @Test(description = "Registration with invalid phone number with 17 chars, negative test")
    public void firstRegWithSeventyCharsPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+3620547894563201"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with only small letters after + , negative test")
    @Test(description = "Registration with invalid phone number with only small letters after +, negative test")
    public void firstRegWithSmallLettersPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+mmmmmmmmmmm"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with only big letters after + , negative test")
    @Test(description = "Registration with invalid phone number with only big letters after +, negative test")
    public void firstRegWithBigLettersPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+LLLLLLLLLLLL"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password, passport and invalid phone number with letter, negative test")
    @Test(description = "Registration with invalid phone number with letter, negative test")
    public void firstRegWithLetterPhoneNumberTest() {
        Response response = new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration
                        ("LMakerNumber3", "Vvjndf445sD78fd", "LMB54154862123", "+920215h456"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), AlertAPI.REGISTRATION_FAILED_USER_PHONE.getValue());

    }

    @Step("Registration new user with valid login, password and without passport and phone, negative test")
    @Test(description = "Registration with valid login, password and without passport and phone, negative test")
    public void firstRegWithoutPassportAndPhoneTest() {
        Response response = new PostAdapters().post(new JsonObjectHelper().setJsonObjectForRegistrationWithPhone
                        ("LMakerNumber3", "Vvjndf445sD78fd", "+2056987894562"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Passport cannot be null");

    }

    @Step("Registration new user with valid login, password and without passport and phone, negative test")
    @Test(description = "Registration with valid login, password and without passport and phone, negative test")
    public void firstRegWithoutPhoneAndPhoneTest() {
        Response response = new PostAdapters().post(new JsonObjectHelper().setJsonObjectForRegistrationWithPassport
                        ("LMakerNumber4", "VLBFf445sD78fd", "LBER21032"),
                API_HOST + API_REGISTRATION, 400).as(Response.class);
        Assert.assertEquals(response.getMessage(), "Phone cannot be null");

    }
}