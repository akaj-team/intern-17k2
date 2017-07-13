package vn.asiantech.internship.unittest;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/13/2017.
 */
public class SolveQuadraticEquation {
    public double getDelta(double a, double b, double c) {
        return Math.pow(b, 2) - 4 * a * c;
    }

    public double[] solve(double a, double b, double c) {
        if (a == 0 && b == 0 && c == 0) {
            return null;
        }
        double[] result;
        if (a == 0 && b != 0) {
            result = new double[1];
            result[0] = -c / b;
            return result;
        }
        double delta = getDelta(a, b, c);
        if (delta < 0) {
            return null;
        }
        result = new double[2];
        result[0] = (-b - Math.sqrt(delta)) / 2 / a;
        result[1] = (-b + Math.sqrt(delta)) / 2 / a;
        return result;
    }
}
