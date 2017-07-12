package vn.asiantech.internship.exday26;

/**
 * Created by datbu on 12-07-2017.
 */

public class QuadraticEquationTest {
    public QuadraticEquationTest() {
    }

    public String checkInput(int a, int b, int c) {
        String result = null;
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    result = "Countless solutions";
                } else {
                    result = "Error";
                }
            } else {
                result = "x = " + (-c / b);
            }
        } else {
            if (getDelta(a, b, c) < 0) {
                result = "No root";
            } else if (getDelta(a, b, c) == 0) {
                result = "x = " + (-b / 2 * a);
            } else if (getDelta(a, b, c) > 0) {
                result = "x1 = " + ((-b + Math.sqrt(getDelta(a, b, c))) / 2 * a)
                        + ", x2 = " + ((-b - Math.sqrt(getDelta(a, b, c))) / 2 * a);
            }
        }
        return result;
    }

    public double getDelta(int a, int b, int c) {
        return b * b - 4 * a * c;
    }
}
