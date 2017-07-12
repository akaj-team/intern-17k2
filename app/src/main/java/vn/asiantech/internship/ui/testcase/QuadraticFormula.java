package vn.asiantech.internship.ui.testcase;

/**
 * Copyright © 2017 AsianTech inc.
 *
 * @author Thanh Thien
 *         Created on 7/12/2017.
 */
public class QuadraticFormula {

    public float findPeakX(float a, float b) {
        return -b / (2 * a);
    }

    public float findPeakY(float a, float b, float c) {
        float x = findPeakX(a, b);
        return (a * x * x) + (b * x) + c;
    }

    public float getDelta(float a, float b, float c) {
        return b * b - (4 * a * c);
    }

    public float getX1(float a, float b, float c) {
        float delta = getDelta(a, b, c);
        return (float) (((-b) - Math.sqrt(delta)) / (2 * a));
    }

    public float getX2(float a, float b, float c) {
        float delta = getDelta(a, b, c);
        return (float) (((-b) + Math.sqrt(delta)) / (2 * a));
    }
}
