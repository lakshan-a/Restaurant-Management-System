package lk.ijse.restaurant.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidateController {
    public static boolean emailCheck(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean contactCheck(String contact) {
        String contactRegex = "^(?:7|0|(?:\\\\\\\\+94))[0-9]{9,10}$";
        Pattern pattern = Pattern.compile(contactRegex);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }

    public static boolean customerIdValidate(String custId) {
        String customerRegex = "^(C)[0-9]{3}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean customerNameValidate(String custName) {
        String customerRegex = "^[A-z\\s]{4,15}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custName);
        return matcher.matches();
    }

    public static boolean supplierIdValidate(String custId) {
        String customerRegex = "^(S)[0-9]{3}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean addressValidate(String custId) {
        String customerRegex = "[A-z @ 0-9]{4,20}";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean ItemNameValidate(String custName) {
        String customerRegex = "^[A-z\\s]{4,15}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custName);
        return matcher.matches();
    }

    public static boolean itemIdValidate(String custId) {
        String customerRegex = "^(I)[0-9]{3}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean FoodIdValidate(String custId) {
        String customerRegex = "^(F)[0-9]{3}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean quantityValidate(String custId) {
        String customerRegex = "^[1-9][0-9]*$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean priceValidate(String custId) {
        String customerRegex = "^[1-9][0-9]*(.[0-9]{2})?$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }
    public static boolean salaryIdValidate(String custId) {
        String customerRegex = "^(S)[0-9]{3}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean empIdValidate(String custId) {
        String customerRegex = "^(E)[0-9]{3}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean nicValidate(String custId) {
        String customerRegex = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean eventIdValidate(String custId) {
        String customerRegex = "^(E)[0-9]{3}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean passwordValidate(String password) {
        String customerRegex = "^(?=.?[A-Z])(?=.?[a-z])(?=.?[0-9])(?=.?[#?!@$ %^&*-]).{8,}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean attendIdValidate(String custId) {
        String customerRegex = "^(A)[0-9]{3}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custId);
        return matcher.matches();
    }

    public static boolean stringValidate(String custName) {
        String customerRegex = "^[A-z\\s]{3,15}$";
        Pattern pattern = Pattern.compile(customerRegex);
        Matcher matcher = pattern.matcher(custName);
        return matcher.matches();
    }

    public static boolean yesNoCheck(String input) {
        if (input.matches("^(Yes|yes|No|no|YES|NO)$")) {
            return true; // Input contains only numeric characters
        } else {
            return false; // Input contains non-numeric characters
        }
    }

}
