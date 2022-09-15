package by.andersen.amnbanking.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contacts {

    private String name;
    private String localPhone;
    private String internationalPhone;
    private ArrayList<Contacts> operationModes;
    public static final String LOCAL_CARDS_SUPPORT_PHONE = "3800";
    public static final String LOCAL_PHONE_FOR_INDIVIDUALS = "3700";
    public static final String INTERNATIONAL_CARDS_SUPPORT_PHONE = "+16846540103";
    public static final String INTERNATIONAL_PHONE_FOR_INDIVIDUALS = "+16846540102";
    public static final String EXPECTED_INDIVIDUALS_TITLE = "For individuals";
    public static final String EXPECTED_CARDS_TITLE = "Cards support";

    public static ArrayList<String> arrayOfInternationalPhoneNumbers() {
        ArrayList<String> expectedInternetContacts = new ArrayList<String>();
        expectedInternetContacts.add("+16846540102");
        expectedInternetContacts.add("+16846540103");
        return expectedInternetContacts;
    }

}
