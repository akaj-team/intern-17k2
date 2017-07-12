package vn.asiantech.internship.ui.unittest;

/**
 *
 * Created by quanghai on 11/07/2017.
 */
public class EquationTest {
    public float getDelta(int a, int b, int c) {
        return b * b - 4 * a * c;
    }

    public String getRoot(int a, int b, int c) {
        String result = null;
        if (getDelta(a, b, c) < 0) {
            result = "No root";
        } else if (getDelta(a, b, c) == 0) {
            result = "x = " + (-b / 2 * a);
        } else if (getDelta(a, b, c) > 0) {
            result = "x1 = " + ((-b + Math.sqrt(getDelta(a, b, c))) / 2 * a)
                    + ", x2 = " + ((-b - Math.sqrt(getDelta(a, b, c))) / 2 * a);
        }
        return result;
    }
}
