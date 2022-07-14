package by.andersen.amnbanking.data;

import lombok.Data;

@Data
public class User {
    private String login;
    private String password;
    private String passport;
    private String phone;

    public User(UserBuilder builder) {
        this.login = builder.login;
        this.password = builder.password;
        this.passport = builder.passport;
        this.phone = builder.phone;
    }

    public static class UserBuilder {
        private String phone;
        private String passport;
        private String password;
        private String login;

        public UserBuilder() {
        }

        public UserBuilder withLogin(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withPassport(String passport) {
            this.passport = passport;
            return this;
        }

        public UserBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}