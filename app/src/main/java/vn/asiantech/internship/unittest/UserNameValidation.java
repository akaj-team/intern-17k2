package vn.asiantech.internship.unittest;

import java.util.Locale;

/**
 * Author AsianTech Inc.
 * Created by sony on 10/07/2017.
 */
public final class UserNameValidation {
    private UserNameValidation() {
    }

    public static boolean checkUserNameLength(String username) {
        return username.length() >= 6 && username.length() <= 24;
    }

    public static boolean checkUserNameSpace(String useName) {
        return !useName.contains(" ");
    }

    public static boolean checkUserNameCharacter(String useName) {
        return useName.matches("[A-Za-z0-9]+");
    }

    public static boolean checkUserNameIgnoreUpperCase(String useName) {
        return useName.equals(useName.toLowerCase(Locale.getDefault()));
    }
}
