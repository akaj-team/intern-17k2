package vn.asiantech.internship.ui.testcase;

import java.util.Locale;

/**
 * Copyright © 2017 AsianTech inc.
 * Created on 7/12/2017
 * @author Thanh Thien
 */
public class RegisterChecker {
    private char[] mNumbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public boolean checkMaxLength(String s, int maxLength) {
        return s.length() <= maxLength;
    }

    public boolean checkMinLength(String s, int minLength) {
        return s.length() >= minLength;
    }

    public boolean checkSpace(String s) {
        return s.indexOf(' ') == -1;
    }

    public boolean trueAllUserName(String s) {
        return s.matches("[a-z0-9]+");
    }

    public boolean hasNumber(String s) {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            for (char num : mNumbers) {
                if (s.charAt(i) == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasSymbol(String s) {
        String specialCharacters = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
        String[] str2 = s.split("");
        for (String aStr2 : str2) {
            if (specialCharacters.contains(aStr2)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasUpChar(String s) {
        String specialCharacters = "ZXCVBNMLKJHGFDSAQWERTYUIOP";
        String[] str2 = s.split("");
        for (String aStr2 : str2) {
            if (specialCharacters.contains(aStr2)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasUser(String password, String user) {
        return !password.toLowerCase(Locale.getDefault()).contains(user.toLowerCase(Locale.getDefault()));
    }
}
