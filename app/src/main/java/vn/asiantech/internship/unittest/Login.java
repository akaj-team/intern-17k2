package vn.asiantech.internship.unittest;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/11/2017.
 */
public final class Login {

    private String user;
    private String pass;

    private Login() {
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public static boolean checkUserLength(String user) {
        return user.length() > 5 && user.length() < 25;
    }

    public static boolean checkUserAlphaNumber(String user) {
        for (char c : user.toCharArray()) {
            if (c < '0' || (c > '9' && c < 'a') || c > 'z') {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPassLength(String pass) {
        return pass.length() > 3;
    }

    public static boolean checkPassStrength(String pass) {
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

    public static boolean checkPassDifferentUser(String user, String pass) {
        return !user.equals(pass);
    }
}
