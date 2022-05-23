package by.andersen.amnbanking.data;

public class User {
    private final String loginPhone;
    private final String passport;
    private final String password;
    private final String login;

    public User(UserBuilder builder) {
        this.loginPhone = builder.loginPhone;
        this.passport = builder.passport;
        this.password = builder.password;
        this.login = builder.login;
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

    public String getLogin(){

        return login;
    }



    public static class UserBuilder {
        private String loginPhone;
        private String passport;
        private String password;
        private String login;

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

        public UserBuilder withLogin(String login){
            this.login = login;

            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
