package vn.asiantech.internship.models;

/**
 * Quadratic model
 * Created by huypham on 16-Jul-17.
 */
public class Quadratic {
    private String a;
    private String b;
    private String c;
    private double delta;

    public Quadratic(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }
}
