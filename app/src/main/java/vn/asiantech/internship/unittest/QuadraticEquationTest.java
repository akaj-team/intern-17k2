package vn.asiantech.internship.unittest;

/**
 * Created by ducle on 13/07/2017.
 */

public class QuadraticEquationTest {
    public double getDelta(double a, double b, double c) {
        return Math.pow(b, 2) - 4 * a * c;
    }


    public double[] getResult(double a, double b, double c) {
        double[] result = new double[2];
        if (a == 0) {
            if (b != 0) {
                return null;
            }
        } else {
            double delta = getDelta(a, b, c);
            if (delta >= 0) {
                if (delta == 0) {
                    result[0] = -b / (2 * a);
                } else {
                    result[0] = (-b - Math.sqrt(delta)) / (2 * a);
                    result[1] = (-b + Math.sqrt(delta)) / (2 * a);
                }
            } else {
                return null;
            }
        }
        return result;
    }
}
