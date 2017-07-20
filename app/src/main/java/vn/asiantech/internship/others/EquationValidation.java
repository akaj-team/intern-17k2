package vn.asiantech.internship.others;

import java.util.ArrayList;
import java.util.List;

/**
 * Equation Validation.
 * Created by huypham on 16-Jul-17.
 */
public class EquationValidation {

    private EquationValidation() {
    }

    public static String checkInput(double a, double b, double c) {
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    return "Surd Root";
                } else {
                    return "No Root";
                }
            } else {
                return "One Root";
            }
        } else {
            return "Double Root";
        }
    }

    public static int checkDelta(double delta) {
        if (delta > 0) {
            return 2;
        } else if (delta == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static String checkOneRoot(double a, double b) {
        return String.valueOf((float) -b / (2 * a));
    }

    public static List<Float> checkDoubleRoot(double a, double b, double delta) {
        List<Float> root = new ArrayList<>();
        root.add((float) ((-b + Math.sqrt(delta)) / (2 * a)));
        root.add((float) ((-b - Math.sqrt(delta)) / (2 * a)));
        return root;
    }

    public static boolean checkInputCharacter(String chars) {
        boolean check = true;
        for (char s : chars.toCharArray()) {
            if (!Character.isDigit(s)) {
                check = false;
            }
        }
        return check;
    }
}
