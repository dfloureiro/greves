package com.dfl.grevesapp.api;

import java.util.Date;
import java.util.GregorianCalendar;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 *
 */

/**
 * class strike
 */
public class Strike {
    @Getter @Setter private String start_date;
    @Getter @Setter private Submitter submitter;
    @Getter @Setter private int id;
    @Getter @Setter private boolean canceled;
    @Getter @Setter private boolean all_day;
    @Getter @Setter private String source_link;
    @Getter @Setter private Company company;
    @Getter @Setter private String description;
    @Getter @Setter private String end_date;
}
