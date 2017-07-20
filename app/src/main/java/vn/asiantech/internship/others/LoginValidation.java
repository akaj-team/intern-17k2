package vn.asiantech.internship.others;

import java.util.regex.Pattern;

/**
 * Login Validation.
 * Created by huypham on 16-Jul-17.
 */
public final class LoginValidation {

    private LoginValidation() {
    }

    public static boolean checkUserLength(String user) {
        return user.length() >= 6 && user.length() <= 24;
    }

    public static boolean checkUserCharacter(String user) {
        return user.matches("[A-Za-z0-9]+");
    }

    public static boolean checkPasswordLength(String password) {
        return password.length() >= 4;
    }

    public static boolean checkPasswordCharacter(String password) {
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern specialWord = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Pattern number = Pattern.compile("[0-9]");
        boolean check = true;
        if (!upperCase.matcher(password).find()) {
            check = false;
        }
        if (!specialWord.matcher(password).find()) {
            check = false;
        }
        if (!number.matcher(password).find()) {
            check = false;
        }
        return check;
    }

    public static boolean checkDifference(String password, String user) {
        return !password.equals(user);
    }

    public static boolean checkPasswordNoSpace(String password) {
        return !password.contains(" ");
    }
}
