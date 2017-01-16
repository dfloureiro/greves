package com.dfl.grevesapp.Utils;

import com.dfl.grevesapp.datamodels.Company;
import com.dfl.grevesapp.datamodels.Strike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Diogo Loureiro on 15/01/2017.
 *
 */

public class StrikesUtils {

    /**
     * order the strikes list
     * @param strikes array list of strikes to order
     */
    public static void sortStrikes(ArrayList<Strike> strikes){
        Comparator<Strike> comparator = new Comparator<Strike>() {
            @Override
            public int compare(Strike c1, Strike c2) {
                return c2.getId() - c1.getId();
            }
        };
        Collections.sort(strikes, comparator);
    }
}