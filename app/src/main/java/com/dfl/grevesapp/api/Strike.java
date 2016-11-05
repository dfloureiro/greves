package com.dfl.grevesapp.api;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Diogo Loureiro on 05/11/2016.
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

    @Override
    public String toString() {
        return "start_date: "+start_date+" , "+
                "end_date: "+end_date+" , "+
                "submitter: "+submitter+" , "+
                "id: "+id+" , "+
                "canceled: "+canceled+" , "+
                "all_day: "+all_day+" , "+
                "source_link: "+source_link+" , "+
                "company: "+company+" , "+
                "description: "+description;
    }

}