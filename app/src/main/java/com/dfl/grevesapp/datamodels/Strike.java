package com.dfl.grevesapp.datamodels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * class strike
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Strike extends RealmObject {
    @PrimaryKey
    private int id;
    private String start_date;
    private boolean canceled;
    private boolean all_day;
    private String source_link;
    private Company company;
    private String description;
    private String end_date;
    private Submitter submitter;

    public Strike() {
    }
}
