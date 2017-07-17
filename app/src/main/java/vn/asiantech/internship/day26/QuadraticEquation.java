package vn.asiantech.internship.day26;

/**
 * Created by at-dinhvo on 11/07/2017.
 */
public class QuadraticEquation {

    public QuadraticEquation() {
    }

    /*public float getDelta(float a, float b, float c) {
        return (float) (Math.pow(b, 2) - 4 * a * c);
    }

    public String getResult(float a, float b, float c) {
        if (a != 0) {
            if (getDelta(a, b, c) < 0) {
                return "No root";
            } else if (getDelta(a, b, c) == 0) {
                return "X = " + (-b) / 2 * a;
            } else {
                return "X1 = " + (-b + Math.sqrt(getDelta(a, b, c))) / 2 * a
                        + ", X2 = " + (-b - Math.sqrt(getDelta(a, b, c))) / 2 * a;
            }
        } else {
            if (b == 0) {
                if (c == 0) {
                    return "Countless root";
                } else {
                    return "No root";
                }
            } else {
                return "X = " + -c / b;
            }
        }
    }*/

    public String getResult(Parameter parameter) {
        float a = parameter.getA();
        float b = parameter.getB();
        float c = parameter.getC();
        if (a != 0) {
            if (parameter.getDelta() < 0) {
                return "No root";
            } else if (parameter.getDelta() == 0) {
                return "X = " + (-b) / 2 * a;
            } else {
                return "X1 = " + (-b + Math.sqrt(parameter.getDelta())) / 2 * a
                        + ", X2 = " + (-b - Math.sqrt(parameter.getDelta())) / 2 * a;
            }
        } else {
            if (b == 0) {
                if (c == 0) {
                    return "Countless root";
                } else {
                    return "No root";
                }
            } else {
                return "X = " + -c / b;
            }
        }
    }
}
