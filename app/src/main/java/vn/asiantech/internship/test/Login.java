package vn.asiantech.internship.test;

/**
 * Created by ducle on 12/07/2017.
 * Login contain user and password
 */
public class Login {
    private String user;
    private String password;

    public static boolean checkUserLength(String user) {
        return user.length() >= 6 && user.length() <= 24;
    }

    public static boolean checkUserSpace(String user) {
        for (int i = 0; i < user.length(); i++) {
            if (user.charAt(i) == ' ') {
                return false;
            }
        }
        return true;
    }

    public static String getUser(String user) {
        return user.toUpperCase();
    }

    public static boolean checkUserAlphanumeric(String user) {
        for (int i = 0; i < user.length(); i++) {
            if (user.charAt(i) >= '0' && user.charAt(i) <= '9') {
                return true;
            }
        }
        return false;
    }
}
