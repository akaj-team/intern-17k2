package vn.asiantech.internship.exday26;

/**
 * Created by datbu on 17-07-2017.
 */

public class Equation {
    private int a;
    private int b;
    private int c;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public double getDelta() {
        return QuadraticEquationTest.getDelta(a, b, c);
    }

    public String getCheckInput() {
        return QuadraticEquationTest.checkInput(a, b, c);
    }
}
