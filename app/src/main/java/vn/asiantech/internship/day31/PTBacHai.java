package vn.asiantech.internship.day31;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 11/07/2017.
 */
public class PTBacHai {

    public static String checkParameter(double a, double b, double c) {
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    return "phuong trinh vo so nghiem";
                } else {
                    return "phuong trinh vo nghiem";
                }
            } else {
                return "phuong trinh co 1 nghiem";
            }
        } else {
            double delta = b * b - 4 * a * c;
            if (delta > 0) {
                return "phuong trinh co 2 nghiem phan biet";
            } else if (delta == 0) {
                return "phuong trinh co nghiem kep";
            }
            return "phuong trinh vo nghiem";
        }
    }

    public static double[] getX1X2(double a, double b, double c) {
        double[] x = new double[2];
        if (a != 0) {
            double delta = b * b - 4 * a * c;
            if (delta > 0) {
                x[0] = (-b + Math.sqrt(delta)) / (2 * a);
                x[1] = (-b - Math.sqrt(delta)) / (2 * a);
            } else if (delta == 0) {
                x[0] = x[1] = -b / (2 * a);
            }
        }
        return x;
    }

    public static double getX(double a, double b, double c) {
        return (-c / b);
    }
}
