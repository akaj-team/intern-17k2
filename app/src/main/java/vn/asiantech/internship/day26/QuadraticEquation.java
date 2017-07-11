package vn.asiantech.internship.day26;

/**
 * Created by at-dinhvo on 11/07/2017.
 */
public class QuadraticEquation {

    public float getDelta(int a, int b, int c) {
        return b * b - 4 * a * c;
    }

    public String getResult(int a, int b, int c) {
        if (getDelta(a, b, c) < 0) {
            return "Vo nghiem";
        } else if (getDelta(a, b, c) == 0) {
            return "X = " + (-b) / 2 * a;
        } else {
            return "X1 = " + (-b + Math.sqrt(getDelta(a, b, c))) / 2 * a
                    + ", X2 = " + (-b - Math.sqrt(getDelta(a, b, c))) / 2 * a;
        }
    }
}
