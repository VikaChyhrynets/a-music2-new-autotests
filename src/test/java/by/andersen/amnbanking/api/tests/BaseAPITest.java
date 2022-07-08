package by.andersen.amnbanking.api.tests;


import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;

@Log4j2
public class BaseAPITest implements CreateDeleteUser {

    @Override
    public void deleteUser() throws SQLException {

    }
    @Override
    public void createUser() {

    }

//    @Step("Registration new user in Data Base")
//    public void createUser() {
//            new PostAdapters().post(JsonObjectHelper.setPassportLoginPasswordForRegistration(
//                    UsersData.USER_EMINEM79.getUser().getLogin(),
//                    UsersData.USER_EMINEM79.getUser().getPassword(),
//                    UsersData.USER_EMINEM79.getUser().getPassport(),
//                    UsersData.USER_EMINEM79.getUser().getPhoneNumber()),
//                    API_HOST + API_REGISTRATION, SC_OK);
//    }
//
//    @Step("Deletes the created user after the test")
//    public void deleteUser() throws SQLException {
//        new DBConnector().deleteUser("Eminem79");
//    }
}

