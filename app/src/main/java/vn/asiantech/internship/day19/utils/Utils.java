package vn.asiantech.internship.day19.utils;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 01/07/2017.
 */
public final class Utils {
    private static Utils sUtils;

    private Utils() {
    }

    public static Utils getUtils() {
        if (sUtils == null) {
            sUtils= new Utils();
        }
        return sUtils;
    }

    public String showTime(long milliseconds) {
        String timer = "";
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            timer += String.valueOf(hours) + ":";
        } else timer += "0:";
        if (minutes > 0) {
            if (minutes <= 9) timer += "0";
            timer += String.valueOf(minutes) + ":";
        } else timer += "00:";
        if (seconds > 0) {
            if (seconds <= 9) timer += "0";
            timer += String.valueOf(seconds);
        } else timer += "00";

        return timer;
    }
}
