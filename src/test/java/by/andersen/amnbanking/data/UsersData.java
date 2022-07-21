package by.andersen.amnbanking.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsersData {
    USER_0NE(new User("Vladivostok2000","Vladivostok2000","ASD153215ASD","+10000000000")),
    USER_EMINEM79(new User("Eminem79", "111Gv5dvvf511", "PVS153215DSV", "+40796842639")),
    USER_MALEFICENT(new User("Maleficent1", "Number1", "222222AA", "+12345678911")),
    EM79_VALID_PASS(new User("Eminem79", "tPvpXJGRqAtbWN8I", "KL125649846",
            "+863455555555")),
    USER_EM79_NEW_PASS(new User("Eminem79", "Number1", null, null)),
    EM79_VALID_PASS_DIGITS(new User("Eminem79", "2Loc4567E", "112364486235",
            "+72355555555")),
    EM79_VAL_PASS_2NUMBERS(new User("Eminem79", "2Loc4567E", "LK",
            "+8565555555555")),
    EM79_MAX_SYM_PASS(new User("Eminem79", "2Loc4567E", "11236489235FR456871230L78D9632",
            "+3459755555555")),
    EM79_MIN_CHARS(new User("Eminem79", "8Rvjsio4", "N5",
            "+96023478512")),
    EM79_MAX_CHARS_PHONE(new User("Eminem79", "8Rvjsio457c", "NX4536489235",
            "+960234785126325")),
    REG_PHONE_NUMBER(new User(null, "+375555555555", null, "+375555555501")),
    MORE_20_CHARS(new User("5Dvkfefnbjgldrkmk8ftt", "584841Cd2154ddvnvddvb", null, null)),
    EMPTY_USER_FIELDS(new User("", "", "", "")),
    MORE_THAN_MAX_CHARS(new User("", "", "PKVRT21587469532014567852154879", "")),
    LESS_THAN_MIN_CHARS(new User("Emine7", "8mvjio", "P", "+36210548971")),
    REG_PASSPORT_NUMBER(new User(null, null, "AM4567", null));

    private final User user;
}
