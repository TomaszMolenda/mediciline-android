package local.tomo.medi.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tomo on 2016-06-05.
 */
public abstract class Months {
    static String JANUARY = "Styczeń";
    static String FEBRUARY = "Luty";
    static String MARCH = "Mrzec";
    static String APRIL = "Kwiecień";
    static String MAY = "Maj";
    static String JUNE = "Czerwiec";
    static String JULY = "Lipiec";
    static String AUGUST = "Sierpień";
    static String SEPTEMBER = "Wrzesień";
    static String OCTOBER = "Paździenik";
    static String NOVEMBER = "Listopad";
    static String DECEMBER = "Grudzień";

    private static List<String> months = new ArrayList<String>();

    public static List<String> getMonths() {
        months.add(JANUARY);
        months.add(FEBRUARY);
        months.add(MARCH);
        months.add(APRIL);
        months.add(MAY);
        months.add(JUNE);
        months.add(JULY);
        months.add(AUGUST);
        months.add(SEPTEMBER);
        months.add(OCTOBER);
        months.add(DECEMBER);
        months.add(NOVEMBER);
        return months;
    }

    public static String createDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int monthId = calendar.get(Calendar.MONTH);
        String month = getMonths().get(monthId);
        int year = calendar.get(Calendar.YEAR);
        return day + " " + month + " " + year;
    }




}
