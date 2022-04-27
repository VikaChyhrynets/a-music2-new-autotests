package by.andersen.amnbanking.api.tests;

import by.andersen.amnbanking.utils.PropertyHelper;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    @BeforeClass
    public void setup() {
        String systemPort = System.getProperty("server.port");
        String projectPort = PropertyHelper.getProperty("server.port");
        if (null == systemPort) {
            if (null == projectPort) {
                RestAssured.port = 8080;
            } else {
                RestAssured.port = Integer.parseInt(projectPort);
            }
        } else {
            RestAssured.port = Integer.parseInt(systemPort);
        }

        String systemHost = System.getProperty("server.host");
        String projectHost = PropertyHelper.getProperty("server.host");
        if (null == systemHost) {
            if (null == projectHost) {
                RestAssured.baseURI = "http://localhost";
            } else {
                RestAssured.baseURI = projectHost;
            }
        } else {
            RestAssured.baseURI = systemHost;
        }
    }
}
