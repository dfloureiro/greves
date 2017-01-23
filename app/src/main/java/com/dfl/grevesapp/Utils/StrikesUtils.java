package com.dfl.grevesapp.Utils;

import com.dfl.grevesapp.datamodels.Strike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public static void sortStrikesId(ArrayList<Strike> strikes) {
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
    public static void sortSrikeDate(ArrayList<Strike> strikes) {
        Comparator<Strike> comparator = new Comparator<Strike>() {
            @Override
            public int compare(Strike c1, Strike c2) {
                return c2.getStart_date().compareTo(c1.getStart_date());
            }
        };
        Collections.sort(strikes, comparator);
    }
}