package by.andersen.amnbanking.utils;

import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;

import static by.andersen.amnbanking.tests.base_test.BaseTest.deleteUser;

@Log4j2
public final class ListenerUtils {

    private ListenerUtils() {
    }

    public static void takeScreenshotOnDeletingUser() {
        try {
            deleteUser();
        } catch (SQLException e) {
            log.error("Error. User wasn't deleted from Data Base");
        }
    }
}