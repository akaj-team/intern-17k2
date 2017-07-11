package vn.asiantech.internship.unittest;

import java.util.ArrayList;
import java.util.List;

/**
 * Author AsianTech Inc.
 * Created by sony on 11/07/2017.
 */
public final class EquationValidation {

    private EquationValidation() {
    }

    public static boolean checkFactorDigit(String a) {
        boolean flag = true;
        for (char s : a.toCharArray()) {
            if (!Character.isDigit(s)) {
                flag = false;
            }
        }
        return flag;
    }

    public static String checkCondition(int a, int b, int c) {
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    return "VoSoNghiem";
                } else {
                    return "0";
                }
            } else {
                return "1";
            }
        } else {
            return "TinhDenta";
        }
    }

    public static int checkDenta(int denta) {
        if (denta == 0) {
            return 1;
        } else if (denta > 0) {
            return 2;
        } else {
            return 0;
        }
    }

    public static String checkOneRoot(int a, int b) {
        return String.valueOf((float) -b / (2 * a));
    }

    public static List<Float> checkTwoRoot(int a, int b, int denta) {
        List<Float> roots = new ArrayList<>();
        roots.add((float) (-b + Math.sqrt(denta) / (2 * a)));
        roots.add((float) (-b - Math.sqrt(denta) / (2 * a)));
        return roots;
    }
}
