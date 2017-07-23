package vn.asiantech.internship.day31;

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 11/07/2017.
 */
public final class QuadraticPerform {
    private static double[] sX = new double[2];

    private QuadraticPerform() {
    }

    public static int checkParameter(double a, double b, double c) {
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    return R.string.quadratic_text_equation_countless_solutions;
                } else {
                    return R.string.quadratic_text_equation_no_solution;
                }
            } else {
                return R.string.quadratic_text_equation_one_solution;
            }
        } else {
            double delta = b * b - 4 * a * c;
            if (delta > 0) {
                return R.string.quadratic_text_equation_two_distinct_solutions;
            } else if (delta == 0) {
                return R.string.quadratic_text_equation_dual_solutions;
            }
            return R.string.quadratic_text_equation_no_solution;
        }
    }

    public static double[] getX1X2(double a, double b, double c) {
        if (a != 0) {
            double delta = b * b - 4 * a * c;
            if (delta > 0) {
                sX[0] = (-b + Math.sqrt(delta)) / (2 * a);
                sX[1] = (-b - Math.sqrt(delta)) / (2 * a);
            } else if (delta == 0) {
                sX[0] = -b / (2 * a);
                sX[1] = -b / (2 * a);
            }
        }
        return sX;
    }

    public static double getX(double a, double b, double c) {
        return (-c / b);
    }
}
