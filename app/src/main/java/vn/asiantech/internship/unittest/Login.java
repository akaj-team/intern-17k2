package vn.asiantech.internship.unittest;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/11/2017.
 */

public class Login {
    public static boolean checkUserLength(String user) {
        if (user.length() > 5 && user.length() < 25) {
            return true;
        }
        return false;
    }

    public static boolean checkUserAlphaNumber(String user) {
        for (int i = 0; i < user.length(); i++) {
            char c = user.charAt(i);
            if (c < '0' || (c > '9' && c < 'a') || c > 'z') {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPassLength(String pass) {
        if (pass.length() > 3) {
            return true;
        }
        return false;
    }

    public static boolean checkPassStrength(String pass) {
        boolean haveUpperChar = false;
        boolean haveNumber = false;
        boolean haveSpecialChar = false;
        for (int i = 0; i < pass.length(); i++) {
            if (haveUpperChar && haveNumber && haveSpecialChar) {
                break;
            }
            char c = pass.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                haveUpperChar = true;
                continue;
            }
            if (c >= '0' && c <= '9') {
                haveNumber = true;
                continue;
            }
            if (c < '0' || (c > '9' && c < 'A') || (c > 'Z' && c < 'a') || c > 'z') {
                haveSpecialChar = true;
            }
        }
        return (haveUpperChar && haveNumber && haveSpecialChar);
    }

    public static boolean checkPassDifferentUser(String user, String pass) {
        return !user.equals(pass);
    }
}
