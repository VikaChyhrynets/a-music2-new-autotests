package by.andersen.amnbanking.tests.api_tests;


import by.andersen.amnbanking.apiControllers.Authentication;
import by.andersen.amnbanking.tests.base_test.BaseTest;
import org.testng.annotations.BeforeMethod;

public abstract class BaseAPITest extends BaseTest {

    protected Authentication authentication;

    @BeforeMethod
    public void createAuthentication() {
        authentication = new Authentication();
    }

}