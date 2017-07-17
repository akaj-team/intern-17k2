package vn.asiantech.internship.ui.unittest;

/**
 *
 * Created by quanghai on 11/07/2017.
 */
public final class Login {
    private Login() {
    }

    public static boolean getMinLengthUserName(String userName) {
        return userName.length() >= 6;
    }

    public static boolean getMaxLengthUserName(String userName) {
        return userName.length() <= 24;
    }

    public static boolean getSpace(String userName) {
        boolean isCheck = false;
        char[] charArray = userName.toCharArray();
        for (char c : charArray) {
            isCheck = c != 32;
        }
        return isCheck;
    }

    public static boolean getAlphaNumberic(String userName) {
        boolean isCheck = false;
        char[] charArray = userName.toCharArray();
        for (char c : charArray) {
            isCheck = (c > 47 && c < 58) || (c > 96 && c < 123);
        }
        return isCheck;
    }

    public static boolean getLengthPassword(String password) {
        return password.length() >= 4;
    }

    public static boolean getPassword(String password, String userName) {
        return !password.equals(userName);
    }

    public static boolean getPasswordContainNumber(String password) {
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

    public static boolean getPasswordContainUpCase(String password) {
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

    public static boolean getPasswordContainSpecialChar(String password) {
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

    public static boolean getPasswordSpace(String password) {
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
