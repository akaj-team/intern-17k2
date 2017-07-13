package vn.asiantech.internship.unittest;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/11/2017.
 */
public class SolveQuadraticEquation {

    private double a;
    private double b;
    private double c;

    public SolveQuadraticEquation() {
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
        return SolveQuadraticEquationUtil.getDelta(a, b, c);
    }

    public double[] solve() {
        return SolveQuadraticEquationUtil.solve(a, b, c);
    }
}
