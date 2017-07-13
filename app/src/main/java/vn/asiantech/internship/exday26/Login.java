package vn.asiantech.internship.exday26;

import java.util.Locale;

/**
 * Created by datbu on 12-07-2017.
 */

public class Login {

    public static boolean checkLengthUsername(String userName) {
        return userName.length() >= 6 && userName.length() <= 24;
    }

    public static boolean checkSpaceUserName(String userName) {
        return !userName.contains(" ");
    }

    public static boolean checkAlphaNumeric(String userName) {
        return userName.matches("[A-Za-z0-9]*");
    }

    public static boolean checkUpperCase(String userName) {
        return !userName.equals(userName.toLowerCase(Locale.getDefault())) || !userName.equals(userName.toUpperCase(Locale.getDefault()));
    }

    public static boolean checkPassLength(String pass) {
        return pass.length() >= 4;
    }

    public static boolean checkIsDuplicateUserName(String pass, String userName) {
        return !pass.equals(userName);
    }

    public static boolean checkPassInputType(String pass) {
        return pass.matches(".*[A-Z].*") && pass.matches(".*[a-z].*") && pass.matches("^[-a-zA-Z@.#$%^&*_&,<\\\\]+$");
    }

    public static boolean checkSpacePassword(String pass) {
        return !pass.contains(" ");
    }
}
