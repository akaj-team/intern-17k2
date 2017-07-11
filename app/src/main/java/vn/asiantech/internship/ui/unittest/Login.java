package vn.asiantech.internship.ui.unittest;

/**
 * Created by quanghai on 11/07/2017.
 */

public class Login {
    public boolean getMinLengthUserName(String userName) {
        return userName.length() >= 6;
    }

    public boolean getMaxLengthUserName(String userName) {
        return userName.length() <= 24;
    }

    public boolean getSpace(String userName) {
        boolean isCheck = false;
        char[] charArray = userName.toCharArray();
        for (char c : charArray) {
            isCheck = c != 32;
        }
        return isCheck;
    }

    public boolean getAlphaNumberic(String userName) {
        boolean isCheck = false;
        char[] charArray = userName.toCharArray();
        for (char c : charArray) {
            isCheck = (c > 47 && c < 58) || (c > 96 && c < 123);
        }
        return isCheck;
    }

    public boolean getLengthPassword(String password) {
        return password.length() >= 4;
    }

    public boolean getPassword(String password, String userName) {
        return !password.equals(userName);
    }

    public boolean getPasswordContainNumber(String password) {
        boolean isCheck = false;
        char[] charArray = password.toCharArray();
        for (char c : charArray) {
            if ((c > 47 && c < 58)) {
                isCheck = true;
                break;
            }
        }
        return isCheck;
    }

    public boolean getPasswordContainUpCase(String password) {
        boolean isCheck = false;
        char[] charArray = password.toCharArray();
        for (char c : charArray) {
            if (c > 64 && c > 91) {
                isCheck = true;
                break;
            }
        }
        return isCheck;
    }

    public boolean getPasswordContainSpecialChar(String password) {
        boolean isCheck = false;
        char[] charArray = password.toCharArray();
        for (char c : charArray) {
            if ((c > 20 && c < 48) || (c > 57 && c < 65) || (c > 122 && c < 127)) {
                isCheck = true;
                break;
            }
        }
        return isCheck;
    }

    public boolean getPasswordSpace(String password) {
        boolean isCheck = true;
        char[] charArray = password.toCharArray();
        for (char c : charArray) {
            if (c == 32) {
                isCheck = false;
                break;
            }
        }
        return isCheck;
    }
}
