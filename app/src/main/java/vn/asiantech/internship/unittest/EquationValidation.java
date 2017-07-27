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

    public static String checkDataInput(int a, int b, int c) {
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    return "UnlessRoots";
                } else {
                    return "NoRoot";
                }
            } else {
                return "OneRoot";
            }
        } else {
            return "CalculateDelta";
        }
    }

    public static int checkDelta(int delta) {
        if (delta < 0) {
            return 0;
        } else if (delta > 0) {
            return 2;
        } else {
            return 1;
        }
    }

    public static String checkOneRoot(int a, int b) {
        return String.valueOf((float) -b / (2 * a));
    }

    public static List<Float> checkTwoRoots(int a, int b, int delta) {
        List<Float> roots = new ArrayList<>();
        roots.add((float) (-b + Math.sqrt(delta) / (2 * a)));
        roots.add((float) (-b - Math.sqrt(delta) / (2 * a)));
        return roots;
    }
}
