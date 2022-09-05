package by.andersen.amnbanking.tests.ui_tests.test;

import by.andersen.amnbanking.listener.UserDeleteListener;
import io.qameta.allure.Epic;
import org.testng.annotations.Listeners;

@Listeners(UserDeleteListener.class)
@Epic("E-1. Registration and authorization")
public class ContactsTest extends BaseUITest {


}
