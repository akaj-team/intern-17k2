package vn.asiantech.internship.unittest;

import java.util.Locale;

/**
 * Author AsianTech Inc.
 * Created by sony on 10/07/2017.
 */
public final class UserNameValidation {
    private UserNameValidation() {
    }

    public static boolean checkUserNameLength(String userName) {
        return userName.length() >= 6 && userName.length() <= 24;
    }

    public static boolean checkUserNameSpace(String userName) {
        return !userName.contains(" ");
    }

    public static boolean checkUserNameCharacter(String userName) {
        return userName.matches("[A-Za-z0-9]+");
    }

    public static boolean checkUserNameIgnoreUpperCase(String userName) {
        return userName.equals(userName.toLowerCase(Locale.getDefault()));
    }
}
