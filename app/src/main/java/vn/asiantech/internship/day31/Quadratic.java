package vn.asiantech.internship.day31;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 17/07/2017.
 */
public class Quadratic {
    private double a;
    private double b;
    private double c;
    private double x1;
    private double x2;

    public Quadratic() {
    }

    public Quadratic(double a, double b, double c, double x1, double x2) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.x1 = x1;
        this.x2 = x2;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }
}
