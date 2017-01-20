package com.dfl.grevesapp.datamodels;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 *
 * class strike
 */
@Data public class Strike {
    private String start_date;
    private Submitter submitter;
    private int id;
    private boolean canceled;
    private boolean all_day;
    private String source_link;
    private Company company;
    private String description;
    private String end_date;
}
