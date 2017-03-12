package com.dfl.grevesapp.utils;

import com.dfl.grevesapp.datamodels.Strike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Diogo Loureiro on 15/01/2017.
 * <p>
 * strikes utils
 */

public class StrikesUtils {

    /**
     * order the strikes list by id
     *
     * @param strikes array list of strikes to order
     */
    public static void sortStrikesById(ArrayList<Strike> strikes) {
        Comparator<Strike> comparator = new Comparator<Strike>() {
            @Override
            public int compare(Strike c1, Strike c2) {
                return c2.getId() - c1.getId();
            }
        };
        Collections.sort(strikes, comparator);
    }

    /**
     * order the strikes list by date
     *
     * @param strikes array list of strikes to order
     */
    public static void sortSrikesByDate(ArrayList<Strike> strikes) {
        Comparator<Strike> comparator = new Comparator<Strike>() {
            @Override
            public int compare(Strike c1, Strike c2) {
                return c2.getStart_date().compareTo(c1.getStart_date());
            }
        };
        Collections.sort(strikes, comparator);
    }

    public static ArrayList<Strike> getOnlyCurrentStrikes(ArrayList<Strike> strikes) {
        Date now = GregorianCalendar.getInstance().getTime();

        ArrayList<Strike> currentStrikes = new ArrayList<>();
        for (Strike strike : strikes) {
            Date strikeEndDate = parseDate(strike.getEnd_date()).getTime();
            if (strikeEndDate.after(now)) {
                currentStrikes.add(strike);
            }
        }
        return currentStrikes;
    }

    public static ArrayList<Strike> getOnlyOldStrikes(ArrayList<Strike> strikes) {
        Date now = GregorianCalendar.getInstance().getTime();

        ArrayList<Strike> oldStrikes = new ArrayList<>();
        for (Strike strike : strikes) {
            Date strikeEndDate = parseDate(strike.getEnd_date()).getTime();
            if (strikeEndDate.before(now)) {
                oldStrikes.add(strike);
            }
        }
        return oldStrikes;
    }

    /**
     * parse string of date to a gregorian calendar variable
     *
     * @param date string of date to parse
     * @return gregorian calendar with all data from date
     */
    public static GregorianCalendar parseDate(String date) {
        String[] mDate = date.split(" ");
        String[] dates = mDate[0].split("-");
        String[] hours = mDate[1].split(":");
        return new GregorianCalendar(Integer.valueOf(dates[0]), Integer.valueOf(dates[1])-1,
                Integer.valueOf(dates[2]), Integer.valueOf(hours[0]), Integer.valueOf(hours[1]),
                Integer.valueOf(hours[2]));
    }
}