package by.andersen.amnbanking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelContacts {
    private String name;
    private String localPhone;
    private String internationalPhone;
    private List<ModelContacts> operationModes;

    public static ArrayList<String> arrayOfInternationalPhoneNumbers() {
        ArrayList<String> expectedInternetContacts = new ArrayList<String>();
        expectedInternetContacts.add("+16846540102");
        expectedInternetContacts.add("+16846540103");
        return expectedInternetContacts;
    }
}
