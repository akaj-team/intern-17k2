package vn.asiantech.internship.day31;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 11/07/2017.
 */
public class Login {

    // Check Username
    public static boolean checkLengthOfUsername(String username) {
        return username.length() >= 6 && username.length() <= 24;
    }

    public static boolean checkSpaceOfUsername(String username) {
        return username.contains(" ");
    }

    public static boolean checkAlphabeOfUsername(String username) {
        for (int i = 0; i < username.length(); i++) {
            if (username.charAt(i) < 97 || username.charAt(i) > 122) {
                return false;
            }
        }
        return true;
    }

    // Check Password
    public static boolean checkLengthOfPassword(String password) {
        return password.length() >= 4;
    }

    public static boolean checkSpaceOfPassword(String password) {
        return password.contains(" ");
    }

    public static boolean checkNotSameUsername(String password, String username) {
        return password.equals(username);
    }

    public static boolean checkUpperNumberSpecial(String password) {
        boolean[] check = new boolean[3];
        int tong = 0;
        for (int i = 0; i < password.length(); i++) {
            char x = password.charAt(i);

            // Ki tu viet hoa
            if (x >= 65 && x <= 90 && !check[0]) {
                tong += 1;
                check[0] = true;
            }

            // Ki tu la so
            if (x >= 48 && x <= 57 && !check[1]) {
                tong += 1;
                check[1] = true;
            }

            // Ki tu dac biet
            if (x >= 33 && x <= 47 || x >= 58 && x <= 64 || x >= 91 && x <= 96 || x >= 123 && x <= 126 && !check[2]) {
                tong += 1;
                check[2] = true;
            }
            if (tong == 3) {
                break;
            }
        }
        return tong == 3;
    }
}
