package vn.asiantech.internship.day26;

/**
 * Created by at-dinhvo on 10/07/2017.
 */
public class Login {

    public Login() {
    }

    public boolean checkValidateUserName(String userName) {
        return (checkUserNameLength(userName) && checkTypeUserName(userName));
    }

    public boolean checkUpperCase(String userName, String strCompare) {
        return userName.equalsIgnoreCase(strCompare);
    }

    public boolean checkUserNameLength(String userName) {
        return (userName.length() >= 6 && userName.length() <= 24);
    }

    public boolean checkTypeUserName(String userName) {
        char[] name = userName.trim().toCharArray();
        for (char key : name) {
            if ((key >= 32 && key <= 47) || (key >= 58 && key <= 64) || (key >= 91 && key <= 96) || key >= 123) {
                return false;
            }
        }
        return true;
    }

    public boolean checkValidatePassword(String password) {
        return (checkPasswordLength(password) && checkTypePassword(password));
    }

    public boolean checkPasswordLength(String password) {
        return (password.length() >= 4);
    }

    public boolean checkTypePassword(String password) {
        char[] pass = password.trim().toCharArray();
        boolean isSpecialChar = false;
        boolean isNumber = false;
        boolean isUpperCase = false;
        for (char key : pass) {
            if (key == 32) {
                return false;
            }
            if (checkSpecialCharacter(key)) {
                isUpperCase = true;
                continue;
            }
            if (checkNumber(key)) {
                isNumber = true;
                continue;
            }
            if (checkPasswordUpperCase(key)) {
                isSpecialChar = true;
            }
        }
        return (isSpecialChar && isUpperCase && isNumber);
    }

    private boolean checkSpecialCharacter(char key) {
        return ((key >= 33 && key <= 47) || (key >= 58 && key <= 64)
                || (key >= 91 && key <= 96) || key >= 123);
    }

    private boolean checkNumber(char key) {
        return (key >= 48 && key <= 57);
    }

    private boolean checkPasswordUpperCase(char key) {
        return (key >= 65 && key <= 90);
    }

    public boolean checkSpecialCharacter(String password) {
        char[] pass = password.toCharArray();
        for (char key : pass) {
            if (key == 32) {
                return false;
            }
            if ((key >= 33 && key <= 47) || (key >= 58 && key <= 64)
                    || (key >= 91 && key <= 96) || key >= 123) {
                return true;
            }
        }
        return false;
    }

    public boolean checkNumber(String password) {
        char[] pass = password.toCharArray();
        for (char key : pass) {
            if (key >= 48 && key <= 57) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPasswordUpperCase(String password) {
        char[] pass = password.toCharArray();
        for (char key : pass) {
            if (key >= 65 && key <= 90) {
                return true;
            }
        }
        return false;
    }
}
