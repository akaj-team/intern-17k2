package vn.asiantech.internship.day19.model;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 01/07/2017.
 */
public class Utils {
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

    public int getProgressPercentage(long currentDuration, long totalDuration) {
        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);
        Double percentage = (((double) currentSeconds) / totalSeconds) * 100;
        return percentage.intValue();
    }

    public int progressToTimer(int progress, int totalDuration) {
        totalDuration = (totalDuration / 1000);
        int currentDuration = (int) ((((double) progress) / 100) * totalDuration);
        return currentDuration * 1000;
    }
}
