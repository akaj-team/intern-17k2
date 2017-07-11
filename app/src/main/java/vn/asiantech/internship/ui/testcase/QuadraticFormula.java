package vn.asiantech.internship.ui.testcase;

/**
 * Created by Thanh Thien on 7/11/2017.
 * QuadraticFormula
 */
public class QuadraticFormula {

    public static float findPeakX(float a, float b) {
        return -b / (2 * a);
    }

    public static float findPeakY(float a, float b, float c) {
        float x = findPeakX(a, b);
        return (a * x * x) + (b * x) + c;
    }

    public static float getDelta(float a, float b, float c) {
        return b * b - (4 * a * c);
    }

    public static float getX1(float a, float b, float c) {
        float delta = getDelta(a, b, c);
        return (float) (((-b) - Math.sqrt(delta)) / (2 * a));
    }

    public static float getX2(float a, float b, float c) {
        float delta = getDelta(a, b, c);
        return (float) (((-b) + Math.sqrt(delta)) / (2 * a));
    }
}
