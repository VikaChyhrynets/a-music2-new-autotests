package by.andersen.amnbanking.utils;


public class BDHelper {
    String query;

    public String setRequestForDeleteUser(String login) {
        query = "DELETE FROM users WHERE login = '"+ login +"'";
        return query;
    }

    public String getUserFormBD() {
        query = "SELECT login FROM users LIMIT 1";
        return query;
    }

    public String getIndividualWorkingHours() {
        query = "select day_of_week || right(cast(100 + extract(hour from opening_time) as varchar),2) " +
                "|| right(cast(100 + extract(minute from opening_time) as varchar),2) " +
                "|| right(cast(100 + extract(hour from closing_time) as varchar),2) " +
                "|| right(cast(100 + extract(minute from closing_time) as varchar),2) as workingHours\n" +
                "from contacts_operation_mode com \n" +
                "where contact_id  = 1";
        return query;
    }

    public String getPhoneNumberForIndividuals() {
        String query = "select distinct local_phone, international_phone " +
                "from contacts \n" +
                "where name = 'For individuals'";
        return query;
    }

    public String getOpeningHoursForCardSupport() {
        query = "select day_of_week || right(cast(100 + extract(hour from opening_time) as varchar),2) " +
                "|| right(cast(100 + extract(minute from opening_time) as varchar),2) " +
                "|| right(cast(100 + extract(hour from closing_time) as varchar),2) " +
                "|| right(cast(100 + extract(minute from closing_time) as varchar),2) as openingHours " +
                "from contacts_operation_mode " +
                "where contact_id = 2";
        return query;
    }

    public String getPhoneNumberForCardSupport() {
        String query = "select distinct local_phone, international_phone " +
                "from contacts \n" +
                "where name = 'Cards support'";
        return query;
    }

}