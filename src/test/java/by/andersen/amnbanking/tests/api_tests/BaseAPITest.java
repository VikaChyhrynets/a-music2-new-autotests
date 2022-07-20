package by.andersen.amnbanking.tests.api_tests;


import by.andersen.amnbanking.apiControllers.Authentication;
import by.andersen.amnbanking.tests.base_test.BaseTest;
import org.testng.annotations.BeforeTest;

public abstract class BaseAPITest extends BaseTest {

    protected Authentication authentication;

    @BeforeTest
    public void createAuthentication() {
        authentication = new Authentication();
    }

}