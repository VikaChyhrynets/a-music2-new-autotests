package by.andersen.amnbanking.utils;

import org.testng.annotations.DataProvider;

public class DataProviderForTests {
    @DataProvider(name = "Special Character")
    public static Object[][] getSpecialCharacter() {
        return new Object[][]{
                {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"?"}, {"!"}, {"*"}, {"\""}, {"№"}, {";"}, {":"},
                {"'"}, {"\\"}, {"/"}, {"("}, {")"}, {"["}, {"]"}, {"{"}, {"}"}, {"<"}, {">"}, {"Я"}
        };
    }

    @DataProvider(name = "Valid Cities")
    public static Object[][] getValidCity() {
        return new Object[][]{{"London"}, {"Madrid"}, {"Berlin"}, {"Warsaw"}};
    }

    @DataProvider(name = "Incorrect Cities")
    public static Object[][] getIncorrectCity() {
        return new Object[][]{{"Moscow"}, {"Rome"}, {"Belgrade"}, {"Budapest"}};
    }
}