package vn.asiantech.internship.unittest;

/**
 * Author AsianTech Inc.
 * Created by sony on 11/07/2017.
 */
public class Equation {
    private String a;
    private String b;
    private String c;
    private int denta;

    public Equation(String a, String b, String c) {
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

    public int getDenta() {
        return denta;
    }

    public void setDenta(int denta) {
        this.denta = denta;
    }
}
