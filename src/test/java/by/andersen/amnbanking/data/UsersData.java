package by.andersen.amnbanking.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsersData {
    USER_EMINEM79(new User("Eminem79", "111Gv5dvvf511", "PVS153215DSV", "+40796842639")),
    USER_MALEFICENT(new User("Maleficent1", "Number1", "222222AA", "+12345678911"));

    private final User user;
}
