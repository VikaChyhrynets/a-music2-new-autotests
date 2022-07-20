package by.andersen.amnbanking.apiControllers;

import by.andersen.amnbanking.model.ModelBranchesAndATM;
import by.andersen.amnbanking.utils.ResponseBodyUtils;
import io.restassured.response.ResponseBody;

import java.util.List;

import static by.andersen.amnbanking.data.DataUrls.API_BANK_INFO;
import static by.andersen.amnbanking.data.DataUrls.API_HOST;
import static by.andersen.amnbanking.utils.RequestSpecificationUtils.requestSpecification;
import static by.andersen.amnbanking.utils.RequestUtils.getRequest;

public class BankInfo {
    public static List<ModelBranchesAndATM> getInfoAboutATMAndBranches(String city, int statusCode) {
        ResponseBody responseBody = getRequest(API_HOST + API_BANK_INFO + city, requestSpecification, statusCode)
                .getBody();
        return ResponseBodyUtils.getObjectsByJsonPath(responseBody, "",
                ModelBranchesAndATM.class);
    }
}