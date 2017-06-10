package com.dfl.grevesapp.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * class strike
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class Strike {
    private int id;
    private String start_date;
    private String end_date;
    private boolean canceled;
    private boolean all_day;
    private String source_link;
    private Company company;
    private String description;
    private Submitter submitter;
}
