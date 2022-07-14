package by.andersen.amnbanking.listener;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import static by.andersen.amnbanking.utils.ListenerUtils.takeScreenshotOnDeletingUser;

public class UserDeleteListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenshotOnDeletingUser();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        takeScreenshotOnDeletingUser();
    }
}