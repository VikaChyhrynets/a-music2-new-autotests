package by.andersen.amnbanking.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsersData {
    USER_EMINEM79(new User("Eminem79", "111Gv5dvvf511", "PVS153215DSV", "+40796842639")),
    USER_MALEFICENT(new User("Maleficent1", "Number1", "222222AA", "+12345678911")),
    EM79_VALID_PASS(new User("Eminem79", "tPvpXJGRqAtbWN8I", "125649846",
            "+863455555555")),
    EM79_VALID_PASS_DIGITS(new User("Eminem79", "2Loc4567E", "112364486235",
            "+72355555555")),
    EM79_VAL_PASS_2NUMBERS(new User("Eminem79", "2Loc4567E", "LK",
            "+8565555555555")),
    EM79_MAX_SYM_PASS(new User("Eminem79", "2Loc4567E", "11236489235FR456871230L78D9632",
            "+3459755555555")),
    EM79_MIN_CHARS_PHONE(new User("Eminem79", "8Rvjsio457c", "NX4536489235",
            "+96023478512")),
    EM79_MAX_CHARS_PHONE(new User("Eminem79", "8Rvjsio457c", "NX4536489235",
            "+960234785126325"));

    private final User user;
}
