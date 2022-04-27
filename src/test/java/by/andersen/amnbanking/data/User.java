package by.andersen.amnbanking.data;

public class User {
    private final String loginPhone;
    private final String passport;
    private final String password;

    public User(UserBuilder builder) {
        this.loginPhone = builder.loginPhone;
        this.passport = builder.passport;
        this.password = builder.password;
    }

    public String getLoginPhone() {
        return loginPhone;
    }

    public String getPassport() {
        return passport;
    }

    public String getPassword() {
        return password;
    }

    public static class UserBuilder {
        private String loginPhone;
        private String passport;
        private String password;

        public UserBuilder() {
        }

        public UserBuilder(String loginPhone, String passport, String password) {
            this.loginPhone = loginPhone;
            this.passport = passport;
            this.password = password;
        }

        public UserBuilder withPhone(String phone) {
            this.loginPhone = phone;
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

        public User build() {
            return new User(this);
        }
    }
}
