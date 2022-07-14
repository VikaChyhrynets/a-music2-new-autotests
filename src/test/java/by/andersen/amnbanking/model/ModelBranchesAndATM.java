package by.andersen.amnbanking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelBranchesAndATM {
    private String type;
    private int number;
    private String city;
    private String address;
    private double latitude;
    private double longitude;
    private boolean moneyTransfer;
    private boolean topUp;
    private boolean topUpWithoutCard;
    private boolean payment;
    private boolean currencyExchange;
    private boolean pandus;
    private boolean exoticCurrencyExchange;
    private boolean consultation;
    private boolean insurance;
    private List<ModelBranchesAndATMOperationMode> operationModes;
}