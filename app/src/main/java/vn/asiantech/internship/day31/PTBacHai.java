package vn.asiantech.internship.day31;

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 11/07/2017.
 */
public class PTBacHai {
    private static double[] mX = new double[2];

    private PTBacHai() {
    }

    public static int checkParameter(double a, double b, double c) {
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    return R.string.ptbachai_text_pt_vo_so_nghiem;
                } else {
                    return R.string.ptbachai_text_pt_vo_nghiem;
                }
            } else {
                return R.string.ptbachai_text_pt_1_nghiem;
            }
        } else {
            double delta = b * b - 4 * a * c;
            if (delta > 0) {
                return R.string.ptbachai_text_pt_2_nghiem_phan_biet;
            } else if (delta == 0) {
                return R.string.ptbachai_text_pt_co_nghiem_kep;
            }
            return R.string.ptbachai_text_pt_vo_nghiem;
        }
    }

    public static double[] getX1X2(double a, double b, double c) {
        if (a != 0) {
            double delta = b * b - 4 * a * c;
            if (delta > 0) {
                mX[0] = (-b + Math.sqrt(delta)) / (2 * a);
                mX[1] = (-b - Math.sqrt(delta)) / (2 * a);
            } else if (delta == 0) {
                mX[0] = mX[1] = -b / (2 * a);
            }
        }
        return mX;
    }

    public static double getX(double a, double b, double c) {
        return (-c / b);
    }
}
