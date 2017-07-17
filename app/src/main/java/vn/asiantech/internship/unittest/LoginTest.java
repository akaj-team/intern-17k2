package vn.asiantech.internship.unittest;

import java.util.Locale;

/**
 * Created by ducle on 12/07/2017.
 * LoginTest contain user and password
 */
public class LoginTest {

    public boolean isCheckUserLength(String user) {
        return user.length() >= 6 && user.length() <= 24;
    }

    public boolean isCheckUserSpace(String user) {
        for (int i = 0; i < user.length(); i++) {
            if (user.charAt(i) == ' ') {
                return false;
            }
        }
        return true;
    }

    public String getUser(String user) {
        return user.toUpperCase(Locale.US);
    }

    public boolean isCheckUserAlphanumeric(String user) {
        for (int i = 0; i < user.length(); i++) {
            if (user.charAt(i) >= '0' && user.charAt(i) <= '9') {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckPasswordLength(String password) {
        return password.length() >= 4;
    }

    public boolean isCheckPasswordChar(String password) {
        boolean isUppercaseLetter = false;
        boolean isNumber = false;
        boolean isSpecialLetter = false;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
                isUppercaseLetter = true;
                break;
            }
        }
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                isNumber = true;
                break;
            }
        }
        for (int i = 0; i < password.length(); i++) {
            if ((password.charAt(i) < '0' && password.charAt(i) != 32)
                    || (password.charAt(i) > '9' && password.charAt(i) < 'A')
                    || (password.charAt(i) > 'Z' && password.charAt(i) < 'a')
                    || password.charAt(i) > 'z') {
                isSpecialLetter = true;
                break;
            }
        }
        return isUppercaseLetter && isNumber && isSpecialLetter;
    }

    public boolean isCheckPasswordEqualUser(String user, String password) {
        return !user.equals(password);
    }
}
