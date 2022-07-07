package by.andersen.amnbanking.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class User {
    private final String login;
    private final String password;
    private final String passport;
    private final String phoneNumber;

        public User(UserBuilder builder) {
        this.login = builder.login;
        this.password = builder.password;
        this.passport = builder.passport;
        this.phoneNumber = builder.phoneNumber;
    }

    public static class UserBuilder {
        private String login;
        private String password;
        private String passport;
        private String phoneNumber;

        public UserBuilder() {
        }

//        public UserBuilder(String login, String password, String passport,  String phoneNumber) {
//            this.login = login;
//            this.password = password;
//            this.passport = passport;
//            this.phoneNumber = phoneNumber;
//        }

        public UserBuilder withPhone(String phone) {
            this.phoneNumber = phone;

            return this;
        }

        public UserBuilder withPassport(String passport) {
            this.passport = passport;

            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;

            return this;
        }

        public UserBuilder withLoginPassword(String login, String password){
            this.login = login;
            this.password = password;

            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
