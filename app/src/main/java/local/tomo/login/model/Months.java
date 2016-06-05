package local.tomo.login.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomo on 2016-06-05.
 */
public abstract class Months {
    String JANUARY = "Styczeń";
    String FEBRUARY = "Luty";
    String MARCH = "Mrzec";
    String APRIL = "Kwiecień";
    String MAY = "Maj";
    String JUNE = "Czerwiec";
    String JULY = "Lipiec";
    String AUGUST = "Sierpień";
    String SEPTEMBER = "Wrzesień";
    String OCTOBER = "Paździenik";
    String NOVEMBER = "Listopad";
    String DECEMBER = "Grudzień";

    public static final List<String> months = new ArrayList<String>();
    {
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
    }




}
