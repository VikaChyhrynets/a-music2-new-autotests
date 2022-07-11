package by.andersen.amnbanking.data;

public enum UserCreator {
    USER_0NE(new User.UserBuilder()
            .withLogin("Vladivostok2000")
            .withPassword("Vladivostok2000")
            .withPassport("ASD153215ASD")
            .withPhone("+10000000000").build());

    private User user;

    UserCreator(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}