package vn.asiantech.internship.unittest;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/11/2017.
 */
public final class SolveQuadraticEquation {

    private double a;
    private double b;
    private double c;

    private SolveQuadraticEquation() {
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getDelta() {
        return SolveQuadraticEquation.getDelta(a, b, c);
    }

    public double[] solve() {
        return SolveQuadraticEquation.solve(a, b, c);
    }

    public static double getDelta(double a, double b, double c) {
        return Math.pow(b, 2) - 4 * a * c;
    }

    public static double[] solve(double a, double b, double c) {
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
