package vn.asiantech.internship.music.utils;

/**
 * Created by ducle on 10/07/2017.
 * TimeUtil to handle
 */
public final class TimeUtil {
    private TimeUtil() {
    }

    public static String getTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int second = time % 60;
        return minute < 10 ? ("0" + minute + ":" + ((second < 10) ? "0" + second : second))
                : (minute + ":" + ((second < 10) ? "0" + second : second));
    }
}
