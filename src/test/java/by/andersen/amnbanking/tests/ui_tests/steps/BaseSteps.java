package by.andersen.amnbanking.tests.ui_tests.steps;

import by.andersen.amnbanking.tests.ui_tests.page.LoginPage;
import by.andersen.amnbanking.utils.PropertyHelper;


public abstract class BaseSteps {
    protected LoginPage loginPage = new LoginPage();
    final static String START_URL = PropertyHelper.getProperty("start.url");

}
