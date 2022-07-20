package by.andersen.amnbanking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelBranchesAndATMOperationMode extends Response {
    private String dayOfWeek;
    private String openingTime;
    private String closingTime;
    private String lunchBreakBeginning;
    private String lunchBreakEnd;
}