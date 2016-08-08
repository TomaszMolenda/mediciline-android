package local.tomo.medi.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import local.tomo.medi.R;

public abstract class Months {
    static String JANUARY = Mediciline.getAppContext().getString(R.string.January);
    static String FEBRUARY = Mediciline.getAppContext().getString(R.string.February);
    static String MARCH = Mediciline.getAppContext().getString(R.string.March);
    static String APRIL = Mediciline.getAppContext().getString(R.string.April);
    static String MAY = Mediciline.getAppContext().getString(R.string.May);
    static String JUNE = Mediciline.getAppContext().getString(R.string.June);
    static String JULY = Mediciline.getAppContext().getString(R.string.July);
    static String AUGUST = Mediciline.getAppContext().getString(R.string.August);
    static String SEPTEMBER = Mediciline.getAppContext().getString(R.string.September);
    static String OCTOBER = Mediciline.getAppContext().getString(R.string.October);
    static String NOVEMBER = Mediciline.getAppContext().getString(R.string.November);
    static String DECEMBER = Mediciline.getAppContext().getString(R.string.December);

    private static List<String> months = new ArrayList<>();

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
