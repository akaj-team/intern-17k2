package vn.asiantech.internship.unittest;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/13/2017.
 */

public class Login {
    public boolean checkUserLength(String user) {
        return user.length() > 5 && user.length() < 25;
    }

    public boolean checkUserAlphaNumber(String user) {
        for (char c : user.toCharArray()) {
            if (c < '0' || (c > '9' && c < 'a') || c > 'z') {
                return false;
            }
        }
        return true;
    }

    public boolean checkPassLength(String pass) {
        return pass.length() > 3;
    }

    public boolean checkPassStrength(String pass) {
        boolean haveUpperChar = false;
        boolean haveNumber = false;
        boolean haveSpecialChar = false;
        for (char c : pass.toCharArray()) {
            if (haveUpperChar && haveNumber && haveSpecialChar) {
                break;
            }
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

    public boolean checkPassDifferentUser(String user, String pass) {
        return !user.equals(pass);
    }
}
