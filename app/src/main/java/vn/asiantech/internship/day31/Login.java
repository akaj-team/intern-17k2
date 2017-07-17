package vn.asiantech.internship.day31;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 11/07/2017.
 */
public final class Login {

    private Login() {
    }

    public static boolean checkLengthOfUsername(String username) {
        return username.length() >= 6 && username.length() <= 24;
    }

    public static boolean checkSpaceOfUsername(String username) {
        return username.contains(" ");
    }

    public static boolean checkAlphabeOfUsername(String username) {
        return username.matches("[a-z]*");
    }

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
        return password.matches(".*[A-Z]+.*[0-9]+.*[^a-z0-9A-Z]+.*");
    }
}
